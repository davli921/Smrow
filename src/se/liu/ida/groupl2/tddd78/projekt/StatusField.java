package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * An class that creates and draws the StatusField, it holds information about the players. For example how  much health the
 * players have, which angle their weapon is in, who the currentplayer is etc.
 */

public class StatusField implements Drawable
{
    private String player1Name, player2Name;
    private Player player1, player2;

    public StatusField(Player player1, Player player2) {
	this.player1Name = player1.getName();
	this.player2Name = player2.getName();
	this.player1 = player1;
	this.player2 = player2;
    }

    //-------- WORK IN PROGRESS -------------//

    public void draw(Graphics2D g2d, Player player) {
	g2d.setColor(Color.black);
	/*
	int player1FieldXPos = 40;
	int player2FieldXPos = 900;
	*/

	int currentPlayerFieldYPos = 40;
	int currentPlayerFieldXPos = 450;
	int angleFieldYPos = 60;
	int powerFieldYPos = 80;
	int healthFieldYPos = 100;

	// Player 1 stats.
	int anglePlayer1 = (int) abs(player1.getWeapon().getDirection()) % 360;
	String weaponAnglePlayer1 = "Angle: " + anglePlayer1;
	String powerPlayer1 = "Power: " + player1.getWeapon().getPower();
	String currentHealthPlayer1 = "Hp: " + player1.getHealth();

	// Player 2 stats.
	int anglePlayer2 = (int) abs((player2.getWeapon().getDirection()) % 360 - 180);
	String weaponAnglePlayer2 = "Angle: " + anglePlayer2;
	String powerPlayer2 = "Power: " + player2.getWeapon().getPower();
	String currentHealthPlayer2 = "Hp: " + player2.getHealth();

	int fieldX;
	int angleY = 60;
	int powerY = 80;
	int healthY = 100;

	int angle;

	if (player == player1) {
	    fieldX = 40;
	    angle = (int) abs(player.getWeapon().getDirection() % 360);
	} else if (player == player2) {
	    fieldX = 900;
	    angle = (int) abs(player.getWeapon().getDirection() % 360 - 180);
	}

	String weaponAngle = "Angle: " + angle;
	String power = "Power: " + player.getWeapon().getPower();
	String currentHP = "HP: " + player.getHealth();

	g2d.drawString(player1Name, player1FieldXPos, currentPlayerFieldYPos);
	g2d.drawString(weaponAnglePlayer1, player1FieldXPos, angleFieldYPos);
	g2d.drawString(powerPlayer1, player1FieldXPos, powerFieldYPos);
	g2d.drawString(currentHealthPlayer1, player1FieldXPos, healthFieldYPos);

	/*
	// Draw current players name.
	// (Intentional comparison of pointers)
	if (player == player1) {
	    String currentPlayerName = "Current player: " + player1Name;
	    g2d.drawString(currentPlayerName, currentPlayerFieldXPos, currentPlayerFieldYPos);
	} else if (player == player2) {
	    String currentPlayerName = "Current player: " + player2Name;
	    g2d.drawString(currentPlayerName, currentPlayerFieldXPos, currentPlayerFieldYPos);
	}


	// Draw player1 stats.
	g2d.drawString(player1Name, player1FieldXPos, currentPlayerFieldYPos);
	g2d.drawString(weaponAnglePlayer1, player1FieldXPos, angleFieldYPos);
	g2d.drawString(powerPlayer1, player1FieldXPos, powerFieldYPos);
	g2d.drawString(currentHealthPlayer1, player1FieldXPos, healthFieldYPos);

	// Draw player2 stats.
	g2d.drawString(player2Name, player2FieldXPos, currentPlayerFieldYPos);
	g2d.drawString(weaponAnglePlayer2, player2FieldXPos, angleFieldYPos);
	g2d.drawString(powerPlayer2, player2FieldXPos, powerFieldYPos);
	g2d.drawString(currentHealthPlayer2, player2FieldXPos, healthFieldYPos);
	*/

    }
}
