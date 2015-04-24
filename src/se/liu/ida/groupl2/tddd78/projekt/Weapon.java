package se.liu.ida.groupl2.tddd78.projekt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * An abstract class that describes how the weapon should work.  For now it's implemented in MissileLauncher.
 * The abstract class contains fields about how the meassurements of the weapon and the maximum power, which is used in GameBoard
 * when the weapon is charged.
 *
 * The shoot function is realised in the subclass and is where the weapon creates an projectile and gives the properties to the projectile.
 */

public abstract class Weapon //implements Drawable
{

    protected static final int MAX_POWER = 100;
    // Chargetime in milliseconds
    protected static final long CHARGE_TIME = 50;
    final static double LENGTH = 40;

    // angle in degrees
    protected double power, angle;


    protected String weaponName;

    private BufferedImage imgRight,imgLeft,currentImg;

    protected Weapon(double angle, String weaponName) {
        this.power = 0;
        this.angle = angle;
        this.weaponName = weaponName;

        try {
            this.imgRight  = ImageIO.read(this.getClass().getResourceAsStream("resources/weaponLeft.png"));
            this.imgLeft = ImageIO.read(this.getClass().getResourceAsStream("resources/weaponLeft.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assign the correct img to "currentImg":
        updateImg();

    }

    public String getWeaponName() {
        return weaponName;
    }

    public double getAngle() {
        return angle;
    }

    // Updates image aswell.
    public void setAngle(final double angle) {
        this.angle = angle;
        updateImg();
    }

    public double getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public BufferedImage getCurrentImg() {
        return currentImg;
    }

    public void updateImg() {
        if (angle < 90) {
            this.currentImg = imgRight;
        } else if (angle > 90) {
            this.currentImg = imgLeft;
        }
    }

    abstract Projectile shoot();

}
