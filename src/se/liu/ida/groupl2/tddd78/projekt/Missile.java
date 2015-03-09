package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.pow;

public class Missile extends Projectile
{
    final static Color COLOR = Color.BLACK;
    final static int WIDTH = 2;
    final static int HEIGHT = 2;
    final static int RADIUS = 2;
    private long TIME_OF_BIRTH;

    public Missile(int dmg, int speed, double direction) {
        super(dmg, COLOR, WIDTH, HEIGHT, speed, direction);
        this.TIME_OF_BIRTH = System.currentTimeMillis();
    }

    public int getRadius() {
	return RADIUS;
    }

    public void move() {

        double g = 9.82;
        long currentT = System.currentTimeMillis();
        double t = (double)(currentT-TIME_OF_BIRTH)/(double)1000;

        double cosValue = cos(toRadians(direction));
        double sinValue = sin(toRadians(direction));

        double newXPos = xPos + speed*cosValue;
        double newYPos = yPos + speed*sinValue  +(g*(pow(t,2.0)))/2.0;

        this.xPos = newXPos;
        this.yPos = newYPos;

    }


}

