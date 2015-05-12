package se.liu.ida.simka275davli921.tddd78.projekt;

/**
 * MissileLauncher subclass of Weapon, "shoot()" returns a missile.
 */

public class MissileLauncher extends Weapon
{

    final static int DMG = 20;
    final static int SPEED_CONSTANT = 10;

    public MissileLauncher(double direction) {
        super(direction, "MissileLauncher");
    }

    public Projectile shoot() {
        double speed = power / SPEED_CONSTANT;
        Projectile missile = new Missile(DMG, speed, angle);
        return missile;
    }
}
