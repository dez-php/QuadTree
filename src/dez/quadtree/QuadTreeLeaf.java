package dez.quadtree;

import java.util.ArrayList;

public class QuadTreeLeaf<T> {

    public double x;
    public double y;
    public ArrayList<T> values;

    public QuadTreeLeaf(double x, double y, T value)
    {
        this.x = x;
        this.y = y;
        this.values = new ArrayList<>();

        this.values.add(value);
    }
}
