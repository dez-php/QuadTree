package dezbyte.app.engine;

import dezbyte.app.engine.physics.Bounds2D;
import dezbyte.app.engine.physics.Vector2D;

public class EntityCircle extends Entity<EntityCircle> {

    protected Vector2D velocity;

    public EntityCircle(int x, int y, Vector2D vector2D, Bounds2D bounds2D)
    {
        this(5, x, y, vector2D, bounds2D);
    }

    public EntityCircle(int radius, int x, int y, Vector2D vector2D, Bounds2D bounds2D)
    {
        super(radius * 2, radius * 2, x, y, vector2D, bounds2D);

        this.velocity = new Vector2D(0D, 0D);
    }

    public double getRadius()
    {
        return this.width() / 2;
    }

    public double getVelocityX()
    {
        return 0;
    }

    public double getVelocityY()
    {
        return 0;
    }

    public void move()
    {
        this.setX(this.getX() + this.getVelocityX());
        this.setY(this.getY() + this.getVelocityY());

        if(this.maxX() > this.bounds.getMaxX()) {
            this.setX(this.bounds.getMaxX() - this.width());
//            this.vector.oppositeX();
        }

        if(this.maxY() > this.bounds.getMaxY()) {
            this.setY(this.bounds.getMaxY() - this.height());
//            this.vector.oppositeY();
        }

        if(0 >= this.getX()) {
            this.setX(0.00D);
//            this.vector.oppositeX();
        }

        if(0 >= this.getY()) {
            this.setY(0.00D);
//            this.vector.oppositeY();
        }
    }

    @Override
    public boolean colliding(EntityCircle entity)
    {
        double radiusSum = this.getRadius() + entity.getRadius();
        boolean collided = this.getDestination(entity) <= Math.hypot(radiusSum, radiusSum);
//        this.velocity.setVectorX(3D);
        return collided;
    }

}
