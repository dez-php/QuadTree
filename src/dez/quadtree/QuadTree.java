package dez.quadtree;


import java.awt.*;

public class QuadTree<T> {

    private QuadTreeNode<T> root;

    public QuadTree(double minX, double minY, double maxX, double maxY)
    {
        this.root = new QuadTreeNode<>(minX, minY, maxX, maxY, 0);
    }

    public void add(QuadTreeLeaf<T> treeLeaf)
    {
        this.root.put(treeLeaf);
    }

    public void clear()
    {
        this.root.clear();
    }

    public void draw(Graphics2D gfx)
    {
        this.root.draw(gfx);
    }

    public String toString()
    {
        return String.format("QuadTree{ rootNode: %s }", root);
    }

}
