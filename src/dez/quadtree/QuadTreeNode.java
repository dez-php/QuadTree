package dez.quadtree;

public class QuadTreeNode<T> {

    private QuadTreeNode<T> NW;
    private QuadTreeNode<T> NE;
    private QuadTreeNode<T> SE;
    private QuadTreeNode<T> SW;

    private boolean hasChildren = false;
    private QuadTreeBound bounds;

    public QuadTreeNode(double minX, double minY, double maxX, double maxY)
    {
        this.bounds = new QuadTreeBound(minX, minY, maxX, maxY);
    }

    public boolean put()
    {
        return false;
    }

    public void divide()
    {
        this.NW = new QuadTreeNode<>(this.bounds.minX, this.bounds.minY, this.bounds.centreX, this.bounds.centreY);
        this.NE = new QuadTreeNode<>(this.bounds.centreX, this.bounds.minY, this.bounds.maxX, this.bounds.centreY);
        this.SE = new QuadTreeNode<>(this.bounds.centreX, this.bounds.centreY, this.bounds.maxX, this.bounds.maxY);
        this.SW = new QuadTreeNode<>(this.bounds.minX, this.bounds.centreY, this.bounds.centreX, this.bounds.maxY);

        this.hasChildren = false;
    }

    public QuadTreeBound getBounds()
    {
        return bounds;
    }

    public boolean hasChildren()
    {
        return this.hasChildren;
    }

    public String toString()
    {
        return String.format("QuadTreeNode{ bounds: %s, NW: %s, NE: %s, SE: %s, SW: %s }", this.bounds, this.NW,
                             this.NE, this.SE, this.SW);
    }
}
