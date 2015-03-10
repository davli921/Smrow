package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.pow;

/**
 * Created by MissileLauncher and represents a projectile on the GameBoard.
 * Has method for calculating its trajectory.
 */

public class Missile extends Projectile
{
    final static Color COLOR = Color.BLACK;
    final static int WIDTH = 2;
    final static int HEIGHT = 2;
    final static int RADIUS = 2;
    private long timeOfBirth;

    public Missile(int dmg, int speed, double direction) {
        super(dmg, COLOR, WIDTH, HEIGHT, speed, direction);
        this.timeOfBirth = System.currentTimeMillis();
    }

    public int getRadius() {
	return RADIUS;
    }

    public void move() {

        long currentT = System.currentTimeMillis();
        double t = (currentT-timeOfBirth) /(double)1000;

        double cosValue = cos(toRadians(direction));
        double sinValue = sin(toRadians(direction));

        double newXPos = xPos + speed*cosValue;
        // Gravity constant
        final double g = 9.82;
        double newYPos = yPos + speed*sinValue  +(g *(pow(t,2.0)))/2.0;

        this.xPos = newXPos;
        this.yPos = newYPos;

    }


}

