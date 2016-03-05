package dez.app;

import dez.quadtree.Object2D;

public class Entity implements Object2D {

    public static final int WIDTH  = 16;
    public static final int HEIGHT = 16;

    public int x;
    public int y;

    public Entity(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(double x)
    {
        this.x = (int) x;
    }

    @Override
    public void setY(double y)
    {
        this.y = (int) y;
    }

    @Override
    public double minX()
    {
        return this.x;
    }

    @Override
    public double minY()
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
        return (this.minX() + this.maxX()) / 2;
    }

    @Override
    public double centreY()
    {
        return (this.minY() + this.maxY()) / 2;
    }
}
