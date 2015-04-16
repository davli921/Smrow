package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HealthBar-objects represents the Player-objects current health. Has fields for its postition,
 * measurements, and current health-info. Have method for updating the healthbar "updateHealthBar".
 */

public class HealthBar
{
    final static Color HEALTH_LEFT_COLOR = Color.green;
    final static Color HEALTH_LOST_COLOR = Color.red;
    final static int LENGHT = 20;
    final static int DIST_ABOVE_PLAYER = 10;
    private List<Rectangle> healthBar;
    private Player player;
    private int playerSize = (int)Player.PLAYER_SIZE;
    private int healthLostLength;
    private int xPos;
    private int yPos;

    public HealthBar(Player player) {
	this.player = player;
	this.xPos = (int) player.getXPos();
	this.yPos = (int) player.getYPos();
	// Length -1 so that it doesnt get painted.
	this.healthLostLength = -1;

	this.healthBar = new ArrayList<>();
	Rectangle fullHp = new Rectangle(xPos - LENGHT / 2 + playerSize / 2, yPos - DIST_ABOVE_PLAYER, LENGHT, 5);
	Rectangle healthLost = new Rectangle(xPos - LENGHT / 2 + playerSize / 2, yPos - DIST_ABOVE_PLAYER, healthLostLength, 5);
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
	int maxHp = Player.MAX_HP;
	int health = player.getHealth();
	this.xPos = (int) player.getXPos();
	this.yPos = (int) player.getYPos();

	double healthChange = (double) health / maxHp;

	if (health < maxHp) {
	    this.healthLostLength = (int) ((1 - healthChange) * LENGHT);
	}

	Rectangle fullHp = new Rectangle(xPos - LENGHT / 2 + playerSize / 2, yPos - DIST_ABOVE_PLAYER, LENGHT, 5);
	Rectangle healthLost =
		new Rectangle(xPos - LENGHT / 2 + playerSize / 2, yPos - DIST_ABOVE_PLAYER, healthLostLength, 5);

	healthBar.clear();
	healthBar.add(fullHp);
	healthBar.add(healthLost);
    }
}
