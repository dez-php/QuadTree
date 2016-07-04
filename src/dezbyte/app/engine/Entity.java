package dezbyte.app.engine;

import dezbyte.app.engine.physics.Bounds2D;
import dezbyte.app.engine.physics.Vector2D;
import dezbyte.quadtree.Object2D;

abstract public class Entity<T extends Entity> implements Object2D {

    public double x;
    public double y;
    protected int width  = 0;
    protected int height = 0;
    protected Vector2D    vector;
    protected Bounds2D    bounds;
    protected EntityState state;

    public Entity(int width, int height, int x, int y, Vector2D vector2D, Bounds2D bounds2D)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.vector = vector2D;
        this.bounds = bounds2D;
        this.state = new EntityState();
    }

    @Override
    public double getX()
    {
        return this.x;
    }

    @Override
    public void setX(double x)
    {
        this.x = x;
    }

    @Override
    public double getY()
    {
        return this.y;
    }

    @Override
    public void setY(double y)
    {
        this.y = y;
    }

    @Override
    public double maxX()
    {
        return this.x + this.width;
    }

    @Override
    public double maxY()
    {
        return this.y + this.height;
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
        return (this.getX() + this.maxX()) / 2;
    }

    @Override
    public double centreY()
    {
        return (this.getY() + this.maxY()) / 2;
    }

    public Vector2D getVector()
    {
        return vector;
    }

    public Bounds2D getBounds()
    {
        return bounds;
    }

    public EntityState getState()
    {
        return this.state;
    }

    public double getDestination(Entity entity)
    {
        return Math.hypot(this.centreX() - entity.centreX(), this.centreY() - entity.centreY());
    }

    abstract public void move();

    abstract public boolean colliding(T entity);

}
