package dezbyte.app;

import dezbyte.app.engine.EntityCircle;
import dezbyte.app.engine.Loop;
import dezbyte.app.engine.geometry.Bounds2D;
import dezbyte.app.engine.geometry.Triangle;
import dezbyte.app.engine.geometry.Vector2D;
import dezbyte.app.engine.graphics.Colors;
import dezbyte.app.gui.MainFrame;
import dezbyte.quadtree.QuadTree;

import java.awt.*;
import java.util.ArrayList;

public class TestVectorLoop extends Loop {

    private QuadTree<EntityCircle> quadTree;
    private static EntityCircle circleV;
    private static EntityCircle circleA;
    private static EntityCircle circleB;
    private static Vector2D vectorAB;

    public TestVectorLoop()
    {
        super("vector_test_loop");

        this.quadTree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);

        Bounds2D bounds2D = new Bounds2D(0, MainFrame.WIDTH, 0, MainFrame.HEIGHT);

        circleA = new EntityCircle(5, new Vector2D(0D, 250D), new Vector2D(1.2D, 1D), bounds2D);
        circleB = new EntityCircle(5, new Vector2D(10D, 250D), new Vector2D(0.4D, 0.7D), bounds2D);

        circleV = new EntityCircle(5, new Vector2D(1D, 1D), new Vector2D(1.5D, 1.5D), bounds2D);
        vectorAB = new Vector2D(0D, 0D);

        this.quadTree.add(circleA);
        this.quadTree.add(circleB);
    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void update(float elapsedTime)
    {
        circleA.move();
        circleB.move();

        vectorAB = Vector2D.subtract(circleA.position(), circleB.position());
        vectorAB.normalize().add(circleA.position());
    }

    @Override
    protected void render()
    {
        this.mainFrame.clearFrame();

        Graphics2D graphics2D = this.mainFrame.getGraphics2D();

        graphics2D.setColor(Colors.DEEP_PINK.color());
        graphics2D.drawLine(
                (int) vectorAB.x() + (int) circleB.radius(),
                (int) vectorAB.y() + (int) circleB.radius(),
                (int) circleB.x() + (int) circleB.radius(),
                (int) circleB.y() + (int) circleB.radius()
        );

        graphics2D.setColor(Colors.BLUE.color());
        graphics2D.fillOval((int) circleA.x(), (int) circleA.y(), (int) circleA.width(), (int) circleA.height());
        graphics2D.drawLine(0, 0, (int) circleA.x() + (int) circleA.radius(), (int) circleA.y() + (int) circleA.radius());

        graphics2D.setColor(Colors.SPRING.color());
        graphics2D.fillOval((int) circleB.x(), (int) circleB.y(), (int) circleB.width(), (int) circleB.height());
        graphics2D.drawLine(0, 0, (int) circleB.x() + (int) circleB.radius(), (int) circleB.y() + (int) circleB.radius());

        this.mainFrame.swapBuffer();
    }

}
