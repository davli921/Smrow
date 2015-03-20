package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;

/**
 * MissileLauncher subclass of Weapon, "shoot()" returns a missile.
 */

public class MissileLauncher extends Weapon
{

    final static double LENGTH = 50;
    final static double HEIGHT = 10;
    final static int DMG = 20;
    final static Color COLOR = Color.RED;

    public MissileLauncher(double direction) {
        super(LENGTH, HEIGHT, COLOR, direction);
    }

    public Projectile shoot() {
        // Divide power by 10 for better calculation and movement in the projectiles move func.
        double speed = power/10;
        Projectile missile = new Missile(DMG, speed, direction);
        return missile;
    }


}
