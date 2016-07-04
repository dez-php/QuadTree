package dezbyte.app;

import dezbyte.quadtree.Object2D;

public class Entity implements Object2D {

    public static final int WIDTH  = 8;
    public static final int HEIGHT = 8;

    public double x;
    public double y;

    private Vector2D vector;
    private Bounds2D bounds;

    public Entity(int x, int y, Vector2D vector2D, Bounds2D bounds2D)
    {
        this.x = x;
        this.y = y;
        this.vector = vector2D;
        this.bounds = bounds2D;
    }

    @Override
    public void setX(double x)
    {
        this.x = x;
    }

    @Override
    public void setY(double y)
    {
        this.y = y;
    }

    @Override
    public double getX()
    {
        return this.x;
    }

    @Override
    public double getY()
    {
        return this.y;
    }

    @Override
    public double maxX()
    {
        return this.x + WIDTH;
    }

    @Override
    public double maxY()
    {
        return this.y + HEIGHT;
    }

    @Override
    public double width()
    {
        return WIDTH;
    }

    @Override
    public double height()
    {
        return HEIGHT;
    }

    @Override
    public double centreX()
    {
        return (this.getX() + this.maxX()) / 2;
    }

    @Override
    public double centreY()
    {
        return (this.getY() + this.maxY()) / 2;
    }

    public void move()
    {
        if(this.maxX() > this.bounds.getMaxX()) {
            this.setX(this.bounds.getMaxX() - this.width());
            this.vector.oppositeX();
        }

        if(this.maxY() > this.bounds.getMaxY()) {
            this.setY(this.bounds.getMaxY() - this.height());
            this.vector.oppositeY();
        }

        if(0 >= this.getX()) {
            this.setX(0.00D);
            this.vector.oppositeX();
        }

        if(0 >= this.getY()) {
            this.setY(0.00D);
            this.vector.oppositeY();
        }

        this.setX(this.getX() + this.vector.getVectorX());
        this.setY(this.getY() + this.vector.getVectorY());
    }

}
