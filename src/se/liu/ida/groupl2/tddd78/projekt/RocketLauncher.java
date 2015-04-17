package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;

public class RocketLauncher extends Weapon
{
    final static double LENGTH = 50;
    final static double HEIGHT = 15;
    final static int DMG = 30;
    final static Color COLOR = Color.YELLOW;

    public RocketLauncher(double direction) {
	super(LENGTH, HEIGHT, COLOR, direction);
    }

    public Projectile shoot() {
	// Divide power by 10 for better calculation and movement in the projectiles move func.
	double speed = power / 20;
	Projectile rocket = new Rocket(DMG, speed, direction);
	return rocket;
    }

}
