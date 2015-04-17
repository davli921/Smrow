package se.liu.ida.groupl2.tddd78.projekt;

/**
 * An Obstacle is an object that is part of the map, but that can't be crossed by players or projectiles.
 */

public class Obstacle implements Collidable
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
}
