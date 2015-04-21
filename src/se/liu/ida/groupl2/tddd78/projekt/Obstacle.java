package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;

/**
 * An Obstacle is an object that is part of the map, but that can't be crossed by players or projectiles.
 */

public class Obstacle implements Collidable, Drawable
{

    private double xPos,yPos,width,height;

    public Obstacle(double xPos, double yPos, double width, double height) {
	this.xPos = xPos;
	this.yPos = yPos;
	this.width = width;
	this.height = height;
    }


    @Override public double getXPos() {
	return this.xPos;
    }

    @Override public double getYPos() {
	return this.yPos;
    }

    @Override public double getWidth() {
	return this.width;
    }

    @Override public double getHeight() {
	return this.height;
    }

    public void draw(Graphics2D g2d, Player player) {
        int x = (int) this.xPos;
        int y = (int) this.yPos;
        int width = (int) this.width;
        int height = (int) this.height;
        g2d.setColor(Color.CYAN);
        g2d.fillRect(x, y, width, height);
    }
}
