package dezbyte.app;

public class Vector2D {

    protected double vectorX = 0.00F;
    protected double vectorY = 0.00F;

    public Vector2D(double vectorX, double vectorY)
    {
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    public double getVectorX()
    {
        return vectorX;
    }

    public double getVectorY()
    {
        return vectorY;
    }

    public void setVectorX(double vectorX) {
        this.vectorX = vectorX;
    }

    public void setVectorY(double vectorY) {
        this.vectorY = vectorY;
    }

    public void oppositeY()
    {
        this.vectorY *= -1;
    }

    public void oppositeX()
    {
        this.vectorX *= -1;
    }

}
