package dezbyte.app.engine.geometry;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class Triangle {

    protected Vector2D position;
    protected int width;
    protected int height;
    protected Map<Vertex, Vector2D> points;

    public Triangle(Vector2D position, int width, int height)
    {
        this.position = position;
        this.width = width;
        this.height = height;

        this.points = new EnumMap<>(Vertex.class);
        this.points.put(Vertex.A, new Vector2D(0D, 0D));
        this.points.put(Vertex.B, new Vector2D(0D, 0D));
        this.points.put(Vertex.C, new Vector2D(0D, 0D));
    }

    public Triangle()
    {
        this(new Vector2D(0D, 0D), 10, 10);
    }

    public Vector2D position()
    {
        return this.position;
    }

    public double x()
    {
        return this.position.x();
    }

    public double y()
    {
        return this.position.y();
    }

    public int width()
    {
        return this.width;
    }

    public int height()
    {
        return this.height;
    }

    public Vector2D center()
    {
        return new Vector2D(this.position.x() + (this.width() / 2), this.position.y() + (this.height() / 2));
    }

    public int[][] points()
    {
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        xPoints[0] = (int) this.position.x();
        yPoints[0] = (int) this.position.y();

        xPoints[1] = (int) this.position.x() + this.width() / 2;
        xPoints[2] = (int) this.position.x() - this.width() / 2;

        yPoints[1] = (int) this.position.y() + this.height();
        yPoints[2] = (int) this.position.y() + this.height();

        return new int[][] {xPoints, yPoints};
    }

    public Polygon polygon()
    {
        int[][] points = this.points();
        return new Polygon(points[0], points[1], 3);
    }

    public enum Vertex{
        A, B, C
    }

}
