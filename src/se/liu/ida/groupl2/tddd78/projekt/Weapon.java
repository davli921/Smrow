package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;

/**
 *
 */

public abstract class Weapon
{

    protected int length;
    protected int height;
    protected double direction;
    protected int power;
    protected Color color;
    protected static final int MAXPOWER = 25;
    // Chargetime in milliseconds
    protected static final long CHARGETIME = 250;

    protected Weapon(final int length, final int height, final Color color, final double direction) {
        this.length = length;
        this.height = height;
        this.color = color;
        this.direction = direction;
        this.power = 0;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(final double direction) {
        this.direction = direction;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    abstract Projectile shoot();


}
