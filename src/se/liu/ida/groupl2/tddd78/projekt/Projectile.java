package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;

/**
 * An abstract class which specifies how the projectiles should be implemented.
 * It contains fields about how it should look, which damage it should give to a Player.
 * Has methods to set and get positions of the projectile, get the damage and the representation of the projectile.
 */

public abstract class Projectile implements Collidable, Drawable
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

    abstract double getRadius();

    public void draw(Graphics2D g2d, Player player) {
        int radius = (int) this.getRadius();
        int xPos = (int) this.xPos;
        int yPos = (int) this.yPos;

        g2d.setColor(color);
        g2d.fillOval(xPos, yPos, radius * 2, radius * 2);
    }
    
    
}
