package se.liu.ida.groupl2.tddd78.projekt;

/**
 * An Obstacle is an object that is part of the map, but that can't be crossed by players or projectiles.
 */

public abstract class Obstacle implements Collidable, Drawable
{

    private double xPos,yPos,width,height;

    protected Obstacle(double xPos, double yPos, double width, double height) {
	this.xPos = xPos;
	this.yPos = yPos;
	this.width = width;
	this.height = height;
    }


    public double getXPos() {
	return this.xPos;
    }

    public double getYPos() {
	return this.yPos;
    }

    public double getWidth() {
	return this.width;
    }

    public double getHeight() {
	return this.height;
    }

}
