package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

/**
 * GameComponent is the visual representation of the playing field. Contains a GameBoard, the players-objects size, and the
 * projectile-objects size.
 */

public class GameComponent extends JComponent implements Listener
{

    private GameBoard gameBoard;
    private Player player1;
    private Player player2;

    public GameComponent(final GameBoard gameBoard) {
	this.gameBoard = gameBoard;
	this.player1 = gameBoard.getPlayer1();
	this.player2 = gameBoard.getPlayer2();
	final InputMap inputMap = new InputMap();
	final ActionMap actionMap = new ActionMap();

	// Event Handling
	Action moveLeft = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.moveCurrentPlayer("left");
	    }
	};

	Action moveRight = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.moveCurrentPlayer("right");
	    }
	};

	Action moveWeaponUp = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.rotateWeapon("Up");
	    }
	};

	Action moveWeaponDown = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.rotateWeapon("Down");
	    }
	};

	Action shoot = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		// Creates projectile and adds is to board.
		gameBoard.getCurrentPlayer().getWeapon().setPower(10);
		gameBoard.shotsFired();
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Move Left");
	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Move Right");
	this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "Move Weapon Up");
	this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Move Weapon Down");
	this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "Shoot");
	this.getActionMap().put("Move Left", moveLeft);
	this.getActionMap().put("Move Right", moveRight);
	this.getActionMap().put("Move Weapon Up", moveWeaponUp);
	this.getActionMap().put("Move Weapon Down", moveWeaponDown);
	this.getActionMap().put("Shoot", shoot);


    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(gameBoard.getWidth(), gameBoard.getHeight());
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	// Paint the board
	paintBoard(g2d);

	// Paint the player
	paintPlayer(g2d, player1);
	paintPlayer(g2d, player2);

	// Paint the weapons on the players
	paintWeapon(g2d, player1);
	paintWeapon(g2d, player2);

	// Paint the projectile
	paintProjectile(g2d);

	// Paint the statusField
	paintStatusField(g2d);
    }

    private void paintBoard(Graphics2D g2d) {
	// Colors
	Color lightblue = new Color(0, 0, 182, 89);
	Color brown = new Color(139, 69, 19);

	GameBoard gameBoard = this.gameBoard;
	// Paint the sky
	g2d.setColor(lightblue);
	g2d.fillRect(0, 0, gameBoard.getWidth(), gameBoard.getHeight());
	// Paint the ground
	g2d.setColor(brown);
	g2d.fillRect(0, gameBoard.getGroundlevel(), gameBoard.getWidth(), gameBoard.getHeight());
    }

    private void paintPlayer(Graphics2D g2d, Player player) {
	int x = player.getXPos();
	int y = player.getYPos();
	int playerSize = Player.PLAYERSIZE;
	g2d.setColor(Color.black);
	g2d.fillRect(x, y, playerSize, playerSize);

	HealthBar healthBar = player.getHealthBar();
	Rectangle healthLeft = healthBar.getHealthLeft();
	Rectangle healthLost = healthBar.getHealthLost();

	g2d.setColor(Color.green);
	g2d.fill(healthLeft);
	g2d.draw(healthLeft);

	g2d.setColor(Color.red);
	g2d.fill(healthLost);
	g2d.draw(healthLost);

    }

    private void paintWeapon(Graphics2D g2d, Player player) {

	AffineTransform oldTransformer = g2d.getTransform();
	AffineTransform transformer = new AffineTransform();

	int playerSize = Player.PLAYERSIZE;
	int playerXPos = player.getXPos();
	int playerYPos = player.getYPos();
	Weapon weapon = player.getWeapon();
	double weaponAngle = Math.toRadians(weapon.getDirection());
	int weaponLength = weapon.getLength();
	int weaponHeight = weapon.getHeight();
	int weaponPosX = playerXPos + playerSize / 2;
	int weaponPosY = playerYPos + playerSize / 2 - weaponHeight / 2;
	int anchorPointX = weaponPosX + 2;
	int anchorPointY = weaponPosY + weaponHeight / 2;
	Color color = weapon.getColor();
	g2d.setColor(color);

	// Rotates the weapon around two anchor points.
	transformer.setToRotation(weaponAngle, anchorPointX, anchorPointY);
	g2d.setTransform(transformer);

	Rectangle weaponShape = new Rectangle(weaponPosX, weaponPosY, weaponLength, weaponHeight);
	g2d.draw(weaponShape);
	g2d.fill(weaponShape);

	g2d.setTransform(oldTransformer);
    }

    private void paintProjectile(Graphics g2d) {
	Projectile projectile = gameBoard.getProjectile();
	if (projectile != null) {
	    Color color = projectile.getColor();
	    int radius = projectile.getRadius();
	    int xPos = (int) projectile.getXPos();
	    int yPos = (int) projectile.getYPos();

	    g2d.setColor(color);
	    g2d.fillOval(xPos, yPos, radius * 2, radius * 2);
	}
    }

    private void paintStatusField(Graphics g2d) {
	Player currentPlayer = gameBoard.getCurrentPlayer();
	if (currentPlayer == player1) {
	    String player = "Player 1";
	    g2d.drawString(player, 40, 40);
	    int angle = (int) Math.abs(currentPlayer.getWeapon().getDirection()) % 360;
	    String weaponAngle = "angle " + angle;
	    g2d.drawString(weaponAngle, 40, 60);
	} else if (currentPlayer == player2) {
	    String player = "Player 2";
	    g2d.drawString(player, 40, 40);
	    int angle = (int) (Math.abs(currentPlayer.getWeapon().getDirection()) % 360 - 180);
	    String weaponAngle = "angle " + angle;
	    g2d.drawString(weaponAngle, 40, 60);
	}

	String power = "power " + currentPlayer.getWeapon().getPower();
	g2d.drawString(power, 40, 80);


    }

    public void update() {
	this.repaint();
    }

}
