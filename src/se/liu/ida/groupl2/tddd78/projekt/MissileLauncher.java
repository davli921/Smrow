package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;

/**
 * MissileLauncher subclass of Weapon, "shoot()" returns a missile.
 */

public class MissileLauncher extends Weapon
{

    final static int LENGTH = 50;
    final static int HEIGHT = 10;
    final static int DMG = 20;
    final static Color COLOR = Color.RED;

    public MissileLauncher(double direction) {
        super(LENGTH, HEIGHT, COLOR, direction);
    }

    public Projectile shoot() {
        Projectile missile = new Missile(DMG, power, Math.toRadians(direction));
        return missile;
    }


}
