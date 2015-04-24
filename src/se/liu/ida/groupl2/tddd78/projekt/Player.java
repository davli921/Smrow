package se.liu.ida.groupl2.tddd78.projekt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.AffineTransform;

import static java.lang.Math.toRadians;

/**
 * "Player" contains information about a "Player"-obj health, position and angle, and also color and size.
 * It also contains a "Weapon", "HealthBar", and a "name" (in the form of a string).
 * "Player" has a method for moving "move" that takes a angle as input, also has a method
 * "createArsenal" that is used in the constructor to create a specific currentWeapon.
 */

public class Player implements Collidable, Drawable
{

    final static double WIDTH = 75;
    final static double HEIGHT = 46;
    final static double MOVE_STEP = 5;
    final static int MAX_HP = 100;

    private final static int WEAPONJOINTX_R = 45;
    private final static int WEAPONJOINTY_R = 8;

    private final static int WEAPONJOINTX_L =28;
    private final static int WEAPONJOINTY_L =13;

    // Direction in degrees
    private double xPos, yPos, angle, weaponJointX, weaponJointY;

    private int health;

    private boolean isActive;

    private Direction direction;

    private HealthBar healthBar;

    private MissileLauncher missileLauncher;
    private RocketLauncher rocketLauncher;
    private Weapon currentWeapon;

    private String name =  "JohnDoe";

    private BufferedImage imgRight,imgLeft,currentImg;

    public Player(double xPos, double yPos, double angle, Direction direction) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;

        this.health = MAX_HP;

        this.isActive = false;

        this.direction = direction;

        this.healthBar = new HealthBar(this.xPos, this.yPos);

        this.missileLauncher = new MissileLauncher(0);
        this.rocketLauncher = new RocketLauncher(0);
        this.currentWeapon = missileLauncher;

        try {
            this.imgRight = ImageIO.read(this.getClass().getResourceAsStream("resources/tankRight.png"));
            this.imgLeft = ImageIO.read(this.getClass().getResourceAsStream("resources/tankLeft.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assigns correct image to "currentImg" and values to the weaponjoints..
        updateImg(direction);

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

    public void setAngle(final double angle) {
        this.angle = angle;
    }

    public Weapon getCurrentWeapon() {
	return currentWeapon;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
        this.healthBar.updateHealthBar(this.health, this.xPos, this.yPos);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }

    // ------------------------------------------------------------------------//

    // Updates the image and set the joint of the currentWeapon at appropriate coords.
    public void updateImg(Direction direction) {
        if (direction.equals(Direction.RIGHT)) {
            currentImg = imgRight;
            weaponJointX = xPos + WEAPONJOINTX_R;
            weaponJointY = yPos + WEAPONJOINTY_R;
        } else if (direction.equals(Direction.LEFT)) {
            currentImg = imgLeft;
            weaponJointX = xPos + WEAPONJOINTX_L;
            weaponJointY = yPos + WEAPONJOINTY_L;
        }
    }

    public void changeWeapon(WeaponType weapon) {
        switch (weapon) {
            case MISSILE_LAUNCHER:
                currentWeapon = missileLauncher;
                break;
            case ROCKET_LAUNCHER:
                currentWeapon = rocketLauncher;
                break;
            default:
                break;
        }
    }

    private void drawWeapon(Graphics2D g2d) {
        AffineTransform oldTransformer = g2d.getTransform();
        AffineTransform transformer = new AffineTransform();

        double weaponAngle = (int) currentWeapon.getAngle();

        int weaponPosX;
        int weaponPosY;

        if (weaponAngle<90) {
            weaponPosX = (int) (xPos +WEAPONJOINTX_R);
            weaponPosY = (int) (yPos +WEAPONJOINTY_R);
        } else {
            weaponPosX = (int) (xPos +WEAPONJOINTX_L);
            weaponPosY = (int) (yPos +WEAPONJOINTY_L);
        }

        // Rotates the currentWeapon around two anchor points.
        transformer.setToRotation(toRadians(weaponAngle), weaponPosX, weaponPosY);
        g2d.setTransform(transformer);

        g2d.drawImage(currentWeapon.getCurrentImg(), weaponPosX, weaponPosY, null);

        g2d.setTransform(oldTransformer);
    }

    public void draw(Graphics2D g2d) {

        AffineTransform oldTransformer = g2d.getTransform();
        AffineTransform transformer = new AffineTransform();

        double angle = toRadians(this.angle);

        double playerAnchorPointX = this.xPos + WIDTH / 2.0;
        double playerAnchorPointY = this.yPos + HEIGHT / 2.0;

        transformer.setToRotation(angle, playerAnchorPointX, playerAnchorPointY);
        g2d.setTransform(transformer);

        HealthBar healthBar = this.healthBar;
        healthBar.draw(g2d);

        g2d.drawImage(currentImg,(int)xPos,(int)yPos,null);

        g2d.setTransform(oldTransformer);

        drawWeapon(g2d);
    }

}
