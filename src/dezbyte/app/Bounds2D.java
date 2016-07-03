package dezbyte.app;

public class Bounds2D {

    private int minX = 0;
    private int maxX = 0;
    private int minY = 0;
    private int maxY = 0;

    public Bounds2D(int minX, int maxX, int minY, int maxY)
    {
        this.minX = minX;
        this.maxX = maxX;

        this.minY = minY;
        this.maxY = maxY;
    }

    public int getMinX()
    {
        return minX;
    }

    public int getMaxX()
    {
        return maxX;
    }

    public int getMinY()
    {
        return minY;
    }

    public int getMaxY()
    {
        return maxY;
    }

}
