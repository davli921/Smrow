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
    protected double xPos, yPos;
    protected int dmg, width, height;
    protected double speed, direction;
    protected Color color;

    protected Projectile(int dmg, Color color, int width, int height, double speed, double direction) {
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

    public int getXPos() {
        return (int) xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return (int) yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public Color getColor() {
        return color;
    }

    @Override public int getWidth() {
        return width;
    }

    @Override public int getHeight() {
        return height;
    }

    abstract void move();

    //temporal, maybe different shape later
    abstract int getRadius();
    
    
}
