package dezbyte.app.engine;

import dezbyte.app.engine.physics.Bounds2D;
import dezbyte.app.engine.physics.Vector2D;

public class EntityRectangle extends Entity<EntityRectangle> {


    public EntityRectangle(int width, int height, int x, int y, Vector2D vector2D, Bounds2D bounds2D)
    {
        super(width, height, x, y, vector2D, bounds2D);
    }

    public EntityRectangle(int x, int y, Vector2D vector2D, Bounds2D bounds2D)
    {
        super(16, 16, x, y, vector2D, bounds2D);
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
        return entity.maxX() > this.getX() && entity.maxY() > this.getY() && entity.getX() < this.maxX() && entity.getY() < this.maxY();
    }

}
