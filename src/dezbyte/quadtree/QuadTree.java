package dezbyte.quadtree;

import java.util.Set;

public class QuadTree<T extends Object2D> {

    private QuadTreeNode<T> root;
    private Set<T>          items;
    public static QuadTreeNode rootNode;

    public QuadTree(double minX, double minY, double maxX, double maxY)
    {
        this.root = new QuadTreeNode<>(minX, minY, maxX, maxY, 0);
        rootNode = this.root;
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

    public Set<T> values()
    {
        if(this.items == null) {
            this.items = this.root.values();
        }

        return this.items;
    }

    public QuadTreeNode<T> rootNode()
    {
        return this.root;
    }

    public void restructure()
    {
        this.root.restructure();
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
