package dezbyte.app.engine;

import dezbyte.app.engine.geometry.Bounds2D;
import dezbyte.app.engine.geometry.Vector2D;

public class EntityRectangle extends Entity<EntityRectangle> {


    public EntityRectangle(int width, int height, Vector2D position, Vector2D velocity, Bounds2D bounds2D)
    {
        super(width, height, position, velocity, bounds2D);
    }

    @Override
    public void move()
    {

    }

    @Override
    public boolean colliding(EntityRectangle entity)
    {
        return false;
    }

    public boolean intersect(EntityRectangle entity)
    {
        return entity.maxX() > this.x() && entity.maxY() > this.y() && entity.x() < this.maxX() && entity.y() < this.maxY();
    }

}
