package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HealthBar-objects visually represents the Player-objects current health
 */

public class HealthBar
{
    final static Color HEALTH_LEFT_COLOR = Color.green;
    final static Color HEALTH_LOST_COLOR = Color.red;
    private List<Rectangle> healthBar;
    private Player player;
    private int playerSize = (int)Player.PLAYER_SIZE;
    private int length;
    private int healthLostLength;
    private int distanceAbovePlayer;
    private int xPos;
    private int yPos;

    public HealthBar(Player player) {
	this.player = player;
	this.length = 20;
	this.distanceAbovePlayer = 10;
	this.xPos = (int) player.getXPos();
	this.yPos = (int) player.getYPos();
	// Length -1 so that it doesnt get painted.
	this.healthLostLength = -1;

	this.healthBar = new ArrayList<>();
	Rectangle fullHp = new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, length, 5);
	Rectangle healthLost = new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, healthLostLength, 5);
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
	int maxHp = (int) Player.MAX_HP;
	int health = player.getHealth();
	this.xPos = (int) player.getXPos();
	this.yPos = (int) player.getYPos();

	double healthChange = (double) health / maxHp;

	if (health < maxHp) {
	    this.healthLostLength = (int) ((1 - healthChange) * length);
	}

	Rectangle fullHp = new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, length, 5);
	Rectangle healthLost =
		new Rectangle(xPos - length / 2 + playerSize / 2, yPos - distanceAbovePlayer, healthLostLength, 5);

	healthBar.clear();
	healthBar.add(fullHp);
	healthBar.add(healthLost);
    }
}
