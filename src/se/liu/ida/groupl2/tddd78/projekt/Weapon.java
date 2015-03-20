package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;

/**
 *
 */

public abstract class Weapon
{

    protected double length, height,power;
    protected double direction;
    protected Color color;
    protected static final int MAX_POWER = 100;
    // Chargetime in milliseconds
    protected static final long CHARGE_TIME = 50;

    protected Weapon(final double length, final double height, final Color color, final double direction) {
        this.length = length;
        this.height = height;
        this.color = color;
        this.direction = direction;
        this.power = 0;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
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

    public double getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    abstract Projectile shoot();

}
