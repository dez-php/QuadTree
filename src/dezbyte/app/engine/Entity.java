package dezbyte.app.engine;

import dezbyte.app.engine.geometry.Bounds2D;
import dezbyte.app.engine.geometry.Vector2D;
import dezbyte.quadtree.Object2D;

abstract public class Entity<T extends Entity> implements Object2D {

    protected int width  = 0;
    protected int height = 0;
    protected Vector2D    position;
    protected Vector2D    velocity;
    protected Bounds2D    bounds;
    protected EntityState state;

    public Entity(int width, int height, Vector2D position, Vector2D velocity, Bounds2D bounds2D)
    {
        this.width = width;
        this.height = height;
        this.bounds = bounds2D;
        this.position = position;
        this.velocity = velocity;
        this.state = new EntityState();
    }

    @Override
    public double x()
    {
        return this.position.x();
    }

    @Override
    public void setX(double x)
    {
        this.position.setX(x);
    }

    @Override
    public double y()
    {
        return this.position.y();
    }

    @Override
    public void setY(double y)
    {
        this.position.setY(y);
    }

    @Override
    public double maxX()
    {
        return this.position.x() + this.width;
    }

    @Override
    public double maxY()
    {
        return this.position.y() + this.height;
    }

    @Override
    public double width()
    {
        return this.width;
    }

    @Override
    public double height()
    {
        return this.height;
    }

    @Override
    public double centreX()
    {
        return (this.x() + this.maxX()) / 2;
    }

    @Override
    public double centreY()
    {
        return (this.y() + this.maxY()) / 2;
    }

    public Vector2D position()
    {
        return this.position;
    }

    public Vector2D velocity()
    {
        return this.velocity;
    }

    public Bounds2D bounds()
    {
        return bounds;
    }

    public EntityState state()
    {
        return this.state;
    }

    abstract public void move();

    abstract public boolean colliding(T entity);

}
