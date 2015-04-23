package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HealthBar-objects represents the Player-objects current health. Has fields for its postition,
 * measurements, and current health-info. Have method for updating the healthbar "updateHealthBar".
 *
 * The draw function is used by the player to draw the healthbar.
 */

public class HealthBar implements Drawable
{
    final static Color HEALTH_LEFT_COLOR = Color.green;
    final static Color HEALTH_LOST_COLOR = Color.red;
    final static int LENGHT = 30;
    final static int DIST_ABOVE_PLAYER = 10;

    private int xPos,yPos,healthLostLength,playerWidth;

    private Player player;

    private List<Rectangle> healthBar;


    public HealthBar(Player player) {
	this.player = player;
	this.xPos = (int) player.getXPos();
	this.yPos = (int) player.getYPos();
	// Length -1 so that it doesnt get painted.
	this.healthLostLength = -1;
	this.playerWidth = (int) Player.WIDTH;

	this.healthBar = new ArrayList<>();
	Rectangle fullHp = new Rectangle(xPos - LENGHT / 2 + playerWidth / 2, yPos - DIST_ABOVE_PLAYER, LENGHT, 5);
	Rectangle healthLost = new Rectangle(xPos - LENGHT / 2 + playerWidth / 2, yPos - DIST_ABOVE_PLAYER, healthLostLength, 5);
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

	Rectangle fullHp = new Rectangle(xPos - LENGHT / 2 + playerWidth / 2, yPos - DIST_ABOVE_PLAYER, LENGHT, 5);
	Rectangle healthLost =
		new Rectangle(xPos - LENGHT / 2 + playerWidth / 2, yPos - DIST_ABOVE_PLAYER, healthLostLength, 5);

	healthBar.clear();
	healthBar.add(fullHp);
	healthBar.add(healthLost);
    }

    public void draw(Graphics2D g2d) {
	Color healthLeftColor = HealthBar.HEALTH_LEFT_COLOR;
	Color healthLostColor = HealthBar.HEALTH_LOST_COLOR;

	Rectangle healthLeft = this.getHealthLeft();
	Rectangle healthLost = this.getHealthLost();

	g2d.setColor(healthLeftColor);
	g2d.fill(healthLeft);
	g2d.draw(healthLeft);

	g2d.setColor(healthLostColor);
	g2d.fill(healthLost);
	g2d.draw(healthLost);
    }
}
