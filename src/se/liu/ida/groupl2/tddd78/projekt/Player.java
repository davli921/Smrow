package se.liu.ida.groupl2.tddd78.projekt;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.awt.Color;

/**
 * The Player class contains information about the objects position and its size. It has a list of listeners which it can notify
 * with the method "notifyStateListener". The methods "moveLeft" and "moveRight" subtracts and adds 1 to the x-position.
 */

public class Player implements Collidable
{

    final static double PLAYER_SIZE = 34;
    final static double MOVE_STEP = 5;
    final static int MAX_HP = 100;
    final static Color COLOR = Color.black;
    private int health;
    private double xPos,yPos,direction;
    private HealthBar healthBar;
    private Weapon weapon;

    public Player(double xPos, double yPos, double direction, String weapon) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        this.health = 100;
        this.healthBar = new HealthBar(this);
        createWeapon(weapon);

    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(final double direction) {
        this.direction = direction;
    }

    public Weapon getWeapon() {
	return weapon;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
        this.healthBar.updateHealthBar();
    }

    public HealthBar getHealthBar() {
        return this.healthBar;
    }

    // These two do the same thing but are seperate so they fit in "Collidable"
    public double getWidth() {
        return PLAYER_SIZE;
    }

    public double getHeight() {
        return PLAYER_SIZE;
    }
    // ------------------------------------------------------------------------//

    public void move(String horizontalDirection) {
        double directionRadians = toRadians(direction);
        if (horizontalDirection == "right") {
            xPos += (MOVE_STEP * cos(directionRadians));
            yPos -= (MOVE_STEP * sin(directionRadians));
            this.healthBar.updateHealthBar();
        } else if (horizontalDirection == "left") {
            xPos -= (MOVE_STEP * cos(directionRadians));
            yPos += (MOVE_STEP * sin(directionRadians));
            this.healthBar.updateHealthBar();
        }
    }

    private void createWeapon(String weapon){
        switch (weapon){
            case "MissileLauncher":
                this.weapon = new MissileLauncher(direction);
                break;
            default:
                break;
        }
    }

}
