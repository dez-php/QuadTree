package dez.app;

import java.awt.*;

public class Entity {

    public int x;
    public int y;
    public int[] vector;

    public Entity(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.vector = new int[]{-2, -2};
    }

    public void move() {
        this.x += this.vector[0];
        this.y += this.vector[1];

        this.vector[0] = this.vector[0] * -1;
        this.vector[1] = this.vector[1] * -1;
    }

    public void draw(Graphics2D gfx) {
        gfx.fillOval(this.x - 1, this.y - 1, 2, 2);
    }


}
