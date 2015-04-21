package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.Math.toRadians;

/**
 * An abstract class that describes how the weapon should work.  For now it's implemented in MissileLauncher.
 * The abstract class contains fields about how the meassurements of the weapon and the maximum power, which is used in GameBoard
 * when the weapon is charged.
 *
 * The shoot function is realised in the subclass and is where the weapon creates an projectile and gives the properties to the projectile.
 */

public abstract class Weapon implements Drawable
{

    protected double length, height,power;
    protected double direction;
    protected Color color;
    protected static final int MAX_POWER = 100;
    // Chargetime in milliseconds
    protected static final long CHARGE_TIME = 50;

    private BufferedImage imgRight,imgLeft,currentImg;

    protected Weapon(final double length, final double height, final Color color, final double direction) {
        this.length = length;
        this.height = height;
        this.color = color;
        this.direction = direction;
        this.power = 0;

        try {
            this.imgRight  = ImageIO.read(this.getClass().getResourceAsStream("resources/weaponLeft.png"));
            this.imgLeft = ImageIO.read(this.getClass().getResourceAsStream("resources/weaponLeft.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.currentImg = imgRight;

    }

    public double getLength() {
        return length;
    }


    public Color getColor() {
        return color;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(final double direction) {
        this.direction = direction;
    }

    public double getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    abstract Projectile shoot();

    public void draw(Graphics2D g2d, Player player) {
        AffineTransform oldTransformer = g2d.getTransform();
        AffineTransform transformer = new AffineTransform();

        int playerXPos = (int) player.getXPos();
        int playerYPos = (int) player.getYPos();
        double weaponAngle = (int) direction;

        int weaponPosX;
        int weaponPosY;

        if (direction<90) {
            weaponPosX = playerXPos + 45;
            weaponPosY = playerYPos + 8;
        } else {
            weaponPosX = playerXPos + 29;
            weaponPosY = playerYPos + 10;
        }
        g2d.setColor(color);

        // Rotates the weapon around two anchor points.
        transformer.setToRotation(toRadians(weaponAngle), weaponPosX, weaponPosY);
        g2d.setTransform(transformer);

        g2d.drawImage(currentImg,weaponPosX,weaponPosY,null);

        g2d.setTransform(oldTransformer);
    }
}
