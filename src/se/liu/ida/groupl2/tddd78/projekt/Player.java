package se.liu.ida.groupl2.tddd78.projekt;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.awt.Color;

/**
 * "Player" contains information about a "Player"-obj health, position and direction, and also color and size.
 * It also contains a "Weapon", "HealthBar", and a "name" (in the form of a string).
 * "Player" has a method for moving "move" that takes a direction as input, also has a method
 * "createWeapon" that is used in the constructor to create a specific weapon.
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
    private String name =  "JohnDoe";

    public Player(double xPos, double yPos, double direction, String weapon) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        this.health = 100;
        this.healthBar = new HealthBar(this);
        this.weapon = null;
        // Creates a weapon with method instead of taking it as param and having to construct a weapon in "GameBoard".
        createWeapon(weapon);

    }

    // GETTERS & SETTERS------------------------------------------------//

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (horizontalDirection.equals("right")) {
            xPos += (MOVE_STEP * cos(directionRadians));
            yPos += (MOVE_STEP * sin(directionRadians));
            this.healthBar.updateHealthBar();
        } else if (horizontalDirection.equals("left")) {
            xPos += (MOVE_STEP * cos(directionRadians+Math.PI));
            yPos += (MOVE_STEP * sin(directionRadians+Math.PI));
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
