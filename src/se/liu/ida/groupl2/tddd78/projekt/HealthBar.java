package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.Rectangle;

import java.util.ArrayList;

public class HealthBar
{
    private ArrayList<Rectangle> healthBar;
    private Player player;
    private int playerSize = Player.PLAYERSIZE;
    private int length;
    private int distanceAbovePlayer;
    private int xPos;
    private int yPos;

    public HealthBar(Player player) {
	this.player = player;
	this.length = 20;
	this.distanceAbovePlayer = 10;
	this.xPos = player.getXPos();
	this.yPos = player.getYPos();

	this.healthBar = new ArrayList<>();
	Rectangle fullHp = new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, length, 5);
	Rectangle healthLost = new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, -1, 5);
	healthBar.add(fullHp);
	healthBar.add(healthLost);
    }

    public Rectangle getHealthLeft() {
	return healthBar.get(0);
    }

    public Rectangle getHealthLost() {
	return healthBar.get(1);
    }


    public void updateHealthBar() {
	int maxHp = Player.MAXHP;
	int health = player.getHealth();
	this.xPos = player.getXPos();
	this.yPos = player.getYPos();

	double healthChange = (double) health / maxHp;

	int healthLostLength = (int) ((1 - healthChange) * length);

	Rectangle fullHp = new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, length, 5);
	Rectangle healthLost =
		new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, healthLostLength, 5);

	healthBar.clear();
	healthBar.add(fullHp);
	healthBar.add(healthLost);
    }
}
