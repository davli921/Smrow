package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;

/**
 * RocketLauncher subclass of Weapon, "shoot()" returns a missile.
 */


public class RocketLauncher extends Weapon
{
    final static double LENGTH = 50;
    final static double HEIGHT = 15;
    final static int DMG = 30;
    final static Color COLOR = Color.YELLOW;
    final static int SPEED_CONSTANT = 20;

    public RocketLauncher(double direction) {
	super(LENGTH, HEIGHT, COLOR, direction);
    }

    public Projectile shoot() {
        double speed = power / SPEED_CONSTANT;
        Projectile rocket = new Rocket(DMG, speed, direction);
	return rocket;
    }

}
