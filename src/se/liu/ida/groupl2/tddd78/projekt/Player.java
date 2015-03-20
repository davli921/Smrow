package se.liu.ida.groupl2.tddd78.projekt;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * The Player class contains information about the objects position and its size. It has a list of listeners which it can notify
 * with the method "notifyStateListener". The methods "moveLeft" and "moveRight" subtracts and adds 1 to the x-position.
 */

public class Player implements Collidable
{

    final static int PLAYERSIZE = 35;
    final static int MOVESTEP = 5;
    final static int MAXHP = 100;
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

    public int getXPos() {
        return (int) xPos;
    }

    public int getYPos() {
        return (int) yPos;
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
    public int getWidth() {
        return PLAYERSIZE;
    }

    public int getHeight() {
        return PLAYERSIZE;
    }
    // ------------------------------------------------------------------------//

    public void move(String horizontalDirection) {
        double directionRadians = toRadians(direction);
        if (horizontalDirection == "right") {
            xPos += (MOVESTEP*cos(directionRadians));
            yPos -= (MOVESTEP*sin(directionRadians));
            this.healthBar.updateHealthBar();
        } else if (horizontalDirection == "left") {
            xPos -=  (MOVESTEP * cos(directionRadians));
            yPos += (MOVESTEP*sin(directionRadians));
            this.healthBar.updateHealthBar();
        }
    }

    private void createWeapon(String weapon){
        switch (weapon){
            case "MissileLauncher":
                this.weapon = new MissileLauncher(0);
                break;
            default:
                break;
        }
    }

}
