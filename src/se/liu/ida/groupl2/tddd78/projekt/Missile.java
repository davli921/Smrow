package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.lang.Math.pow;

/**
 * Created by an Weapon,used in MissileLauncher, and represents a projectile on the GameBoard.
 * The method move() is used for calculating its trajectory.
 */

public class Missile extends Projectile
{
    final static Color COLOR = Color.BLACK;
    final static double WIDTH = 2.0;
    final static double HEIGHT = 2.0;
    final static double RADIUS = 2.0;

    private long timeOfBirth;

    public Missile(int dmg, double speed, double direction) {
        super(dmg, COLOR, WIDTH, HEIGHT, speed, direction);
        this.timeOfBirth = System.currentTimeMillis();
    }

    public double getRadius() {
        return RADIUS;
    }

    public void move() {

        long currentT = System.currentTimeMillis();
        double t = (currentT-timeOfBirth) /(double)1000;

        double cosValue = cos(toRadians(direction));
        double sinValue = sin(toRadians(direction));

        double deltaX = (speed*cosValue);
        final double g = 9.82;
        double deltaDown = g*( pow(t,2)/2 );
        double deltaY = (speed*sinValue + deltaDown);

        double newXPos = xPos + deltaX;
        double newYPos = yPos + deltaY;

        this.xPos = newXPos;
        this.yPos = newYPos;
    }

}

