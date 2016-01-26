package dez.quadtree;


public class QuadTree {

    private QuadTreeNode root;

    public QuadTree(double minX, double minY, double maxX, double maxY) {
        this.root = new QuadTreeNode(minX, minY, maxX, maxY);
    }

    public String toString()
    {
        return String.format("QuadTree{ rootNode: %s }", root);
    }

}
