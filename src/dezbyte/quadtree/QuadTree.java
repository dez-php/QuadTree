package dezbyte.quadtree;

import java.util.Set;

@SuppressWarnings("unused")
public class QuadTree<T extends Object2D> {

    private QuadTreeNode<T> root;
    private Set<T>          items;

    public QuadTree(double minX, double minY, double maxX, double maxY)
    {
        root = new QuadTreeNode<>(minX, minY, maxX, maxY, 0);
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

    public Set<T> leafsAll()
    {
        if(this.items == null) {
            this.items = this.root.leafsAll();
        }

        return this.items;
    }

    public QuadTreeNode<T> rootNode()
    {
        return this.root;
    }

    public static QuadTreeNode root()
    {
        return null;
    }

    public void update()
    {
        this.root.updateBelongs();
        this.root.updateNodes();
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
