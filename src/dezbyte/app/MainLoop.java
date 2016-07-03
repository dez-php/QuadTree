package dezbyte.app;

import dezbyte.quadtree.Object2D;
import dezbyte.quadtree.QuadTree;

import java.awt.*;
import java.util.Random;

public class MainLoop extends Loop implements QuadTree.Executor {

    private Random random;

    private QuadTree<Entity> tree;

    public MainLoop()
    {
        super();

        this.tree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
        this.random = new Random();

        int amount = 32;

        Bounds2D bounds2D = new Bounds2D(0, MainFrame.WIDTH, 0, MainFrame.HEIGHT);

        double rangeMin = -1D;
        double rangeMax = 1D;

        for (int i = 0; i < amount; i++) {

            int x = this.random.nextInt(MainFrame.WIDTH);
            int y = this.random.nextInt(MainFrame.HEIGHT);

            double vectorX = (rangeMax - rangeMin) + rangeMin * this.random.nextDouble();
            double vectorY = (rangeMax - rangeMin) + rangeMin * this.random.nextDouble();

            Vector2D vector2D = new Vector2D(vectorX, vectorY);

            this.tree.add(new Entity(x, y, vector2D, bounds2D));
        }

    }

    @Override
    protected void update()
    {
        this.tree.values().forEach(object2D -> ((Entity) object2D).move());
    }

    @Override
    protected void render()
    {
        this.mainFrame.clearFrame();

        Graphics2D graphics2D = this.mainFrame.getGraphics2D();

        graphics2D.setFont(new Font("courier", Font.PLAIN, 11));
        graphics2D.setColor(Color.GREEN);

        this.tree.rootNode().nodes().forEach((nodeType, node) -> {

            int x = (int) node.getBounds().minX;
            int y = (int) node.getBounds().minY;

            int width  = (int) node.getBounds().width;
            int height = (int) node.getBounds().height;

            graphics2D.drawRect(x, y, width, height);
            graphics2D.drawString(node.values().size() + " leafs", x + 5, y + 16);

        });

        graphics2D.setColor(Color.WHITE);

        this.tree.values().forEach(object2D -> {

            int x      = (int) object2D.getX();
            int y      = (int) object2D.getY();
            int width  = (int) object2D.width();
            int height = (int) object2D.height();

            graphics2D.fillOval(x, y, width, height);

        });
        this.mainFrame.swapBuffer();
    }

    @Override
    public void execute(Object2D object2D)
    {

    }

}
