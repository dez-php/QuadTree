package dez.quadtree;


public class QuadTreeBound {

    public final double minX;
    public final double minY;
    public final double maxX;
    public final double maxY;

    public final double centreX;
    public final double centreY;

    public QuadTreeBound(double minX, double minY, double maxX, double maxY)
    {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        this.centreX = (minX + maxX) / 2;
        this.centreY = (minY + maxY) / 2;
    }

    public boolean contains(double x, double y)
    {
        return (x >= this.minX && y >= this.minY && x <= this.maxX && y <= this.maxY);
    }

    public boolean intersects(QuadTreeBound bound)
    {
        return (bound.maxX > this.minX && bound.maxY > this.maxY && bound.minX < this.maxY && bound.minY < this.maxY);
    }

    public QuadTreeBound union(QuadTreeBound bound)
    {
        return new QuadTreeBound(Math.min(bound.minX, this.minX), Math.min(bound.minY, this.minY),
                                 Math.max(bound.maxX, this.maxX), Math.max(bound.maxY, this.maxY));
    }

    @Override
    public String toString()
    {
        return String.format("QuadTreeBound{ x:%s y:%s, x:%s y:%s, x:%s y:%s }", this.minX, this.minY, this.maxX,
                             this.maxY, this.centreX, this.centreY);
    }
}
