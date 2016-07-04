package dezbyte.app;

import dezbyte.quadtree.Object2D;
import dezbyte.quadtree.QuadTree;
import dezbyte.quadtree.QuadTreeNode;

import java.awt.*;
import java.util.Random;
import java.util.function.BiConsumer;

public class MainLoop extends Loop implements QuadTree.EachLeaf {

    private Random random;

    private QuadTree<Entity> tree;

    public MainLoop()
    {
        super();

        this.tree = new QuadTree<>(0, 0, MainFrame.WIDTH, MainFrame.HEIGHT);
        this.random = new Random();

        int amount = 32;

        Bounds2D bounds2D = new Bounds2D(0, MainFrame.WIDTH, 0, MainFrame.HEIGHT);

        double rangeMin = 1D;
        double rangeMax = -1D;

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
    protected void initialize()
    {

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void update()
    {
        this.tree.rootNode().leafsAll().forEach(object2D -> ((Entity) object2D).move());
        this.tree.update();

        this.tree.rootNode().eachNode(treeNode -> {
            if(! treeNode.hasChildren() && treeNode.leafs().size() > 0) {

                for (Entity entityA : (Iterable<Entity>) treeNode.leafs()) {
                    for (Entity entityB : (Iterable<Entity>) treeNode.leafs()) {

                        if (entityA.intersect(entityB)) {
                            entityB.getVector().oppositeX();
                            entityA.getVector().oppositeX();
                            entityB.getState().collision = EntityState.Collision.YES;
                            entityA.getState().collision = EntityState.Collision.YES;
                        } else {
                            entityB.getState().collision = EntityState.Collision.NO;
                            entityA.getState().collision = EntityState.Collision.NO;
                        }

                    }
                }

            }
        });
//
//        BiConsumer<BiConsumer, QuadTreeNode> collisionChecker = (consumer, node) -> {
//            if(node.hasChildren()) {
//                node.nodes().forEach((nodeType, innerNode) -> consumer.accept(consumer, innerNode));
//            } else if(node.leafs().size() > 0) {
//                for (Entity leaf : (Iterable<Entity>) node.leafs()) {
//                    for (Entity innerLeaf : (Iterable<Entity>) node.leafs()) {
//                        if (!leaf.equals(innerLeaf) && innerLeaf.intersect(leaf)) {
//                            leaf.getVector().rebound();
//                            leaf.getState().collision = EntityState.Collision.YES;
//                            innerLeaf.getState().collision = EntityState.Collision.YES;
//                        } else {
//                            leaf.getState().collision = EntityState.Collision.NO;
//                            innerLeaf.getState().collision = EntityState.Collision.NO;
//                        }
//                    }
//                }
//            }
//        };

//        collisionChecker.accept(collisionChecker, this.tree.rootNode());

    }

    @Override
    @SuppressWarnings("unchecked")
    protected void render()
    {
        this.mainFrame.clearFrame();

        Graphics2D graphics2D = this.mainFrame.getGraphics2D();

        graphics2D.setFont(new Font("courier", Font.PLAIN, 8));

        graphics2D.setColor(Color.DARK_GRAY);

        BiConsumer<BiConsumer, QuadTreeNode> nodeRecursive = (consumer, node) -> {
            if(node.hasChildren()) {
                node.nodes().forEach((nodeType, innerNode) -> consumer.accept(consumer, innerNode));
            } else {
                int x = (int) node.getBounds().minX;
                int y = (int) node.getBounds().minY;

                int width  = (int) node.getBounds().width;
                int height = (int) node.getBounds().height;

                graphics2D.drawString(String.valueOf(node.leafs().size()), x + 2, y + 10);
                graphics2D.drawRect(x, y, width, height);
            }
        };

        nodeRecursive.accept(nodeRecursive, this.tree.rootNode());

        this.tree.rootNode().leafsAll().forEach(entity -> {

            if(entity.getState().collision == EntityState.Collision.YES) {
                graphics2D.setColor(Color.RED);
            } else {
                graphics2D.setColor(Color.WHITE);
            }

            int x      = (int) entity.getX();
            int y      = (int) entity.getY();
            int width  = (int) entity.width();
            int height = (int) entity.height();

            graphics2D.fillOval(x, y, width, height);

        });

        this.mainFrame.swapBuffer();
    }

    @Override
    public void execute(Object2D object2D)
    {

    }

}
