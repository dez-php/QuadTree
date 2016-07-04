package dezbyte.app.engine.physics;

public class Vector2D {

    protected double vectorX = 0.00F;
    protected double vectorY = 0.00F;

    public Vector2D(double vectorX, double vectorY)
    {
        this.vectorX = vectorX; //Math.min(vectorX, 1D);
        this.vectorY = vectorY; //Math.min(vectorY, 1D);
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

    public void reduceX()
    {
        if(this.vectorX > 0D)
            this.vectorX -= 0.0001D;
    }

    public void reduceY()
    {
        if(this.vectorY > 0D)
            this.vectorY -= 0.0001D;
    }

    public void oppositeY()
    {
        this.vectorY *= -1;
    }

    public void oppositeX()
    {
        this.vectorX *= -1;
    }

    public void opposite()
    {
        this.oppositeX();
        this.oppositeY();
    }

    public void rebound()
    {

        if (this.vectorY > this.vectorX) {
            this.oppositeX();
        } else {
            this.oppositeY();
        }
    }

}
