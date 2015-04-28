package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import static java.lang.Math.abs;

/**
 * An class that creates and draws the StatusField, it holds information about the players. For example how  much health the
 * players have, which angle their weapon is in, who the currentplayer is etc.
 */

public class StatusField implements Drawable
{

    final static Color TEXTCOLOR = Color.BLACK;

    private List<Player> players;

    public StatusField(List<Player> players) {
	this.players = players;
    }

    //-------- WORK IN PROGRESS -------------/

    private void drawPlayerStats(Graphics2D g2d, Player player) {
	int fieldX = GameBoard.WIDTH/2 -100;
	int angle = (int) abs(player.getCurrentWeapon().getAngle() % 180 );

	final int nameY = 40;
	final int angleY = 60;
	final int powerY = 80;
	final int healthY = 100;
	final int weaponY = 120;

	String weaponAngle = "Angle: " + angle;
	String power = "Power: " + player.getCurrentWeapon().getPower();
	String currentHP = "HP: " + player.getHealth();
	String weapon = "Weapon: " + player.getCurrentWeapon().getWeaponName();

	g2d.drawString(player.getName(), fieldX, nameY);
	g2d.drawString(weaponAngle, fieldX, angleY);
	g2d.drawString(power, fieldX, powerY);
	g2d.drawString(currentHP, fieldX, healthY);
	g2d.drawString(weapon, fieldX, weaponY);

    }

    public void draw(Graphics2D g2d) {
	g2d.setColor(TEXTCOLOR);


	for (Player player : players) {
	    if (player.isActive()) {
		drawPlayerStats(g2d, player);
	    }
	}
    }
}
