package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * An class that creates and draws the StatusField, it holds information about the players. For example how  much health the
 * players have, which angle their weapon is in, who the currentplayer is etc.
 */

public class StatusField implements Drawable
{

    final static Color TEXTCOLOR = Color.BLACK;

    private Player player1, player2;

    public StatusField(Player player1, Player player2) {
	this.player1 = player1;
	this.player2 = player2;
    }

    //-------- WORK IN PROGRESS -------------/

    private void drawPlayerStats(Graphics2D g2d, Player player) {
	int fieldX;
	int angle;

	final int nameY = 40;
	final int angleY = 60;
	final int powerY = 80;
	final int healthY = 100;

	// Places player1 stats to the far left and player2 stats to the far right.
	// (Uses pointer comparison to make sure you've got the right object)
	if (player == player1) {
	    fieldX = 40;
	    angle = (int) abs(player.getWeapon().getDirection() % 360);
	} else {
	    fieldX = 880;
	    angle = (int) abs(player.getWeapon().getDirection() % 360 - 180);
	}

	String weaponAngle = "Angle: " + angle;
	String power = "Power: " + player.getWeapon().getPower();
	String currentHP = "HP: " + player.getHealth();

	g2d.drawString(player.getName(), fieldX, nameY);
	g2d.drawString(weaponAngle, fieldX, angleY);
	g2d.drawString(power, fieldX, powerY);
	g2d.drawString(currentHP, fieldX, healthY);
    }

    public void draw(Graphics2D g2d) {
	g2d.setColor(TEXTCOLOR);

	drawPlayerStats(g2d,player1);
	drawPlayerStats(g2d,player2);
    }
}
