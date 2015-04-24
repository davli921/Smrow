package se.liu.ida.groupl2.tddd78.projekt;

/**
 * RocketLauncher subclass of Weapon, "shoot()" returns a missile.
 */


public class RocketLauncher extends Weapon
{
    final static int DMG = 30;
    final static int SPEED_CONSTANT = 20;


    public RocketLauncher(double direction) {
        super(direction, "RocketLauncher");
    }

    public Projectile shoot() {
        double speed = power / SPEED_CONSTANT;
        Projectile rocket = new Rocket(DMG, speed, angle);
	return rocket;
    }

}
