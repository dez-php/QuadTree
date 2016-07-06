package dezbyte.app.engine;

import dezbyte.app.engine.geometry.Bounds2D;
import dezbyte.app.engine.geometry.Vector2D;

public class EntityCircle extends Entity<EntityCircle> {

    public EntityCircle(int radius, Vector2D position, Vector2D velocity, Bounds2D bounds2D)
    {
        super(radius * 2, radius * 2, position, velocity, bounds2D);
    }

    public double radius()
    {
        return this.width() / 2;
    }

    public void move()
    {
        this.setX(this.x() + this.velocity.x());
        this.setY(this.y() + this.velocity.y());

        if (this.maxX() > this.bounds.getMaxX()) {
            this.setX(this.bounds.getMaxX() - this.width());
            this.velocity().setX(-this.velocity().x());
        } else if (0 > this.x()) {
            this.setX(0.00D);
            this.velocity().setX(this.velocity().x() * -1);
        }

        if (this.maxY() > this.bounds.getMaxY()) {
            this.setY(this.bounds.getMaxY() - this.height());
            this.velocity().setY(-this.velocity().y());
        } else if (0 > this.y()) {
            this.setY(0.00D);
            this.velocity().setY(this.velocity().y() * -1);
        }
    }

    @Override
    public boolean colliding(EntityCircle entity)
    {
        return false;
    }

}
