package se.liu.ida.groupl2.tddd78.projekt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.AffineTransform;

import static java.lang.Math.toRadians;

/**
 * "Player" contains information about a "Player"-obj health, position and direction, and also color and size.
 * It also contains a "Weapon", "HealthBar", and a "name" (in the form of a string).
 * "Player" has a method for moving "move" that takes a direction as input, also has a method
 * "createWeapon" that is used in the constructor to create a specific weapon.
 */

public class Player implements Collidable, Drawable
{

    final static double WIDTH = 75;
    final static double HEIGHT = 46;
    final static double MOVE_STEP = 5;
    final static int MAX_HP = 100;

    private int health;

    // Direction in degrees
    private double xPos,yPos,direction,weaponJointX, weaponJointY;

    private HealthBar healthBar;

    private Weapon weapon;

    private String name =  "JohnDoe";

    private BufferedImage imgRight,imgLeft,currentImg;

    public Player(double xPos, double yPos, double direction, String weapon, Direction horizontalDirection) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        this.health = MAX_HP;
        this.healthBar = new HealthBar(this);
        this.weapon = null;
        // Creates a weapon with method instead of taking it as param and having to construct a weapon in "GameBoard".
        createWeapon(weapon);


        try {
            this.imgRight = ImageIO.read(this.getClass().getResourceAsStream("resources/tankRight.png"));
            this.imgLeft = ImageIO.read(this.getClass().getResourceAsStream("resources/tankLeft.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assigns correct image to "currentImg".
        updateImg(horizontalDirection);

    }

    // GETTERS & SETTERS------------------------------------------------//

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
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

    public double getWeaponJointX() {
        return weaponJointX;
    }

    public double getWeaponJointY() {
        return weaponJointY;
    }

    // These two do the same thing but are seperate so they fit in "Collidable"
    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }

    // ------------------------------------------------------------------------//

    // Updates the image and set the joint of the weapon at appropriate coords.
        public void updateImg(Direction horizontalDirection) {
            if (horizontalDirection.equals(Direction.RIGHT)) {
                currentImg = imgRight;
                weaponJointX = xPos + 45;
                weaponJointY = yPos + 8;
            } else if (horizontalDirection.equals(Direction.LEFT)) {
                currentImg = imgLeft;
                weaponJointX = xPos + 29;
                weaponJointY = yPos + 10;
            }
        }

    public void changeWeapon(String weapon) {
            double weaponDirection = this.weapon.getDirection();

            switch (weapon) {
                case "MissileLauncher":
                    this.weapon = new MissileLauncher(weaponDirection);
                    break;
                case "RocketLauncher":
                    this.weapon = new RocketLauncher(weaponDirection);
                    break;
                default:
                    break;
            }
        }

    private void createWeapon(String weapon){
        switch (weapon){
            case "MissileLauncher":
                this.weapon = new MissileLauncher(direction);
                break;
            case "RocketLauncher":
                this.weapon = new RocketLauncher(direction);
                break;
            default:
                break;
        }
    }

    private void drawWeapon(Graphics2D g2d) {
            AffineTransform oldTransformer = g2d.getTransform();
            AffineTransform transformer = new AffineTransform();

            double weaponAngle = (int) weapon.getDirection();

            int weaponPosX;
            int weaponPosY;

            if (weaponAngle<90) {
                weaponPosX = (int) (xPos +45);
                weaponPosY = (int) (yPos +8);
            } else {
                weaponPosX = (int) (xPos +28);
                weaponPosY = (int) (yPos +13);
            }

            // Rotates the weapon around two anchor points.
            transformer.setToRotation(toRadians(weaponAngle), weaponPosX, weaponPosY);
            g2d.setTransform(transformer);

            g2d.drawImage(weapon.getCurrentImg(),weaponPosX,weaponPosY,null);

            g2d.setTransform(oldTransformer);
        }

    public void draw(Graphics2D g2d) {

        AffineTransform oldTransformer = g2d.getTransform();
        AffineTransform transformer = new AffineTransform();

        double direction = toRadians(this.direction);

        double playerAnchorPointX = this.xPos + WIDTH / 2.0;
        double playerAnchorPointY = this.yPos + HEIGHT / 2.0;

        transformer.setToRotation(direction, playerAnchorPointX, playerAnchorPointY);
        g2d.setTransform(transformer);

        HealthBar healthBar = this.healthBar;
        healthBar.draw(g2d);

        g2d.drawImage(currentImg,(int)xPos,(int)yPos,null);

        g2d.setTransform(oldTransformer);

        drawWeapon(g2d);

    }

}
