package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Missile extends Projectile
{
    final static Color COLOR = Color.BLACK;
    final static int WIDTH = 2;
    final static int HEIGHT = 2;
    final static int RADIUS = 2;

    public Missile(int dmg, int speed, double direction) {
        super(dmg, COLOR, WIDTH, HEIGHT, speed, direction);
    }

    public int getRadius() {
	return RADIUS;
    }

    public void move() {

        double cosValue = cos(direction);
        double sinValue = sin(direction);

        double newXPos = xPos + speed * cosValue;
        double newYPos = yPos + speed * sinValue;

        this.xPos = newXPos;
        this.yPos = newYPos;

    }


}

