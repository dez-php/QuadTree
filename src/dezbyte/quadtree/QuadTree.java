package dezbyte.quadtree;

import java.util.ArrayList;

public class QuadTree<T extends Object2D> {

    private QuadTreeNode<T> root;
    private ArrayList<T>    items;

    public QuadTree(double minX, double minY, double maxX, double maxY)
    {
        this.root = new QuadTreeNode<>(minX, minY, maxX, maxY, 0);
    }

    public void add(T treeLeaf)
    {
        this.root.insert(treeLeaf);
    }

    public void clear()
    {
        this.root.clear();
    }

    public void execute(QuadTree.Executor executor)
    {
        this.root.execute(executor);
    }

    public ArrayList<T> values()
    {
        if(this.items == null) {
            this.items = new ArrayList<>();
            this.root.values(this.items);
        }

        return this.items;
    }

    public QuadTreeNode<T> rootNode()
    {
        return this.root;
    }

    public String toString()
    {
        return String.format("QuadTree{ rootNode: %s }", root);
    }

    @FunctionalInterface
    public interface Executor {
        void execute(Object2D object2D);
    }

}
