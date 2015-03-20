package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;

/**
 *
 */

public abstract class Projectile implements Collidable
{
    // We chose to have the positions in double to be able to move the projectile less than one pixel in the move() function
    // because when we calculate the new positions with trigonometric functions they in some cases return less than +1 in change.
    // The getters however is still returning int, so that projectile fullfills the contract of Collidable.
    protected double xPos, yPos,speed, direction;
    protected int dmg;
    protected double width, height;
    protected Color color;

    protected Projectile(int dmg, Color color, double width, double height, double speed, double direction) {
        this.dmg = dmg;
        this.color = color;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.direction = direction;
    }

    public int getDmg() {
        return dmg;
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public Color getColor() {
        return color;
    }

    @Override public double getWidth() {
        return width;
    }

    @Override public double getHeight() {
        return height;
    }

    abstract void move();

    //temporal, maybe different shape later
    abstract double getRadius();
    
    
}
