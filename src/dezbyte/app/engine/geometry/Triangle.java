package dezbyte.app.engine.geometry;

public class Triangle {

    protected Vector2D position;
    protected Vector2D vector;

    public Triangle(Vector2D position)
    {
        this.position = position;
    }

    public Triangle()
    {
        this(new Vector2D(0D, 0D));
    }

    public void polygon()
    {

    }

}
