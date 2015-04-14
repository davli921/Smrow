package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import static java.lang.Math.toRadians;
import static java.lang.Math.abs;

/**
 * GameComponent is the visual representation of the playing field. Contains a GameBoard, the players-objects size, and the
 * projectile-objects size.
 */

public class GameComponent extends JComponent implements Listener
{

    private GameBoard gameBoard;
    private Player player1,player2;

    public GameComponent(final GameBoard gameBoard) {
	this.gameBoard = gameBoard;
	this.player1 = gameBoard.getPlayer1();
	this.player2 = gameBoard.getPlayer2();

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

	Action chargeWeapon = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.setChargingWeapon(true);
	    }
	};

	Action shoot = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.shotsFired();
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Move Left");
	this.getActionMap().put("Move Left", moveLeft);

	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Move Right");
	this.getActionMap().put("Move Right", moveRight);

	this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "Move Weapon Up");
	this.getActionMap().put("Move Weapon Up", moveWeaponUp);

	this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Move Weapon Down");
	this.getActionMap().put("Move Weapon Down", moveWeaponDown);

	/* Fire when pressed
	this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "Shoot");
	this.getActionMap().put("Shoot", shoot);
	*/

	// Use to charge the weapon and then fire
	this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"Charge Weapon");
	this.getActionMap().put("Charge Weapon", chargeWeapon);
	this.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"),"Shoot");
	this.getActionMap().put("Shoot", shoot);
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension((int)gameBoard.getWIDTH(), (int) gameBoard.getHEIGHT());
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	paintBoard(g2d);
    }

    private void paintBoard(Graphics2D g2d) {
	// Colors
	Color lightblue = new Color(0, 0, 182, 89);

	GameBoard gameBoard = this.gameBoard;
	// Paint the sky
	g2d.setColor(lightblue);
	g2d.fillRect(0, 0, (int)gameBoard.getWIDTH(), (int)gameBoard.getHEIGHT());

	paintGround(g2d);

	paintPlayer(g2d, player1);
	paintWeapon(g2d, player1);

	paintPlayer(g2d, player2);
	paintWeapon(g2d, player2);

	paintProjectile(g2d);

	paintStatusField(g2d);
    }

    private void paintPlayer(Graphics2D g2d, Player player) {

	AffineTransform oldTransformer = g2d.getTransform();
	AffineTransform transformer = new AffineTransform();

	int x = (int) player.getXPos();
	int y = (int) player.getYPos();
	int playerSize = (int)Player.PLAYER_SIZE;
	Color playerColor = Player.COLOR;
	Color healthLeftColor = HealthBar.HEALTH_LEFT_COLOR;
	Color healthLostColor = HealthBar.HEALTH_LOST_COLOR;
	double direction = toRadians(player.getDirection());

	int playerAnchorPointX = (int) player.getXPos() + playerSize / 2;
	int playerAnchorPointY = (int) player.getYPos() + playerSize / 2;


	transformer.setToRotation(direction, playerAnchorPointX, playerAnchorPointY);
	g2d.setTransform(transformer);

	HealthBar healthBar = player.getHealthBar();
	Rectangle healthLeft = healthBar.getHealthLeft();
	Rectangle healthLost = healthBar.getHealthLost();

	g2d.setColor(healthLeftColor);
	g2d.fill(healthLeft);
	g2d.draw(healthLeft);

	g2d.setColor(healthLostColor);
	g2d.fill(healthLost);
	g2d.draw(healthLost);

	g2d.setColor(playerColor);
	g2d.fillRect(x, y, playerSize, playerSize);

	g2d.setTransform(oldTransformer);
    }

    private void paintWeapon(Graphics2D g2d, Player player) {

	AffineTransform oldTransformer = g2d.getTransform();
	AffineTransform transformer = new AffineTransform();

	int playerSize = (int) Player.PLAYER_SIZE;
	int playerXPos = (int) player.getXPos();
	int playerYPos = (int) player.getYPos();
	Weapon weapon = player.getWeapon();
	double weaponAngle = weapon.getDirection();
	int weaponLength = (int) weapon.getLength();
	int weaponHeight = (int) weapon.getHeight();
	int weaponPosX = playerXPos + playerSize / 2;
	int weaponPosY = playerYPos + playerSize / 2 - weaponHeight / 2;
	int anchorPointX = weaponPosX + 2;
	int anchorPointY = weaponPosY + weaponHeight / 2;
	Color color = weapon.getColor();
	g2d.setColor(color);

	// Rotates the weapon around two anchor points.
	transformer.setToRotation(toRadians(weaponAngle), anchorPointX, anchorPointY);
	g2d.setTransform(transformer);

	Shape weaponShape = new Rectangle(weaponPosX, weaponPosY, weaponLength, weaponHeight);
	g2d.draw(weaponShape);
	g2d.fill(weaponShape);

	g2d.setTransform(oldTransformer);
    }

    private void paintProjectile(Graphics g2d) {
	Projectile projectile = gameBoard.getProjectile();
	if (projectile != null) {
	    Color color = projectile.getColor();
	    int radius = (int) projectile.getRadius();
	    int xPos = (int) projectile.getXPos();
	    int yPos = (int) projectile.getYPos();

	    g2d.setColor(color);
	    g2d.fillOval(xPos, yPos, radius * 2, radius * 2);
	}
    }

    private void paintStatusField(Graphics g2d) {
	// Shows information about the players.
	Player currentPlayer = gameBoard.getCurrentPlayer();

	Player player1 = gameBoard.getPlayer1();
	Player player2 = gameBoard.getPlayer2();

	String player1Name = player1.getName();
	String player2Name = player2.getName();

	int fieldPlayer1XPos = 40;
	int fieldPlayer2XPos = 900;

	int playerFieldYPos = 40;
	int angleFieldYPos = 60;
	int powerFieldYPos = 80;
	int healthFieldYPos = 100;

	if (currentPlayer == player1) {
	    String currentPlayerName = "Current player: " + player1Name;
	    g2d.drawString(currentPlayerName, 450, playerFieldYPos);
   	 } else if (currentPlayer == player2) {
	    String currentPlayerName = "Current player: " + player2Name;
	    g2d.drawString(currentPlayerName, 450, playerFieldYPos);
	}

	// Problems if the player is rotated!!
	int anglePlayer1 = (int) abs(player1.getWeapon().getDirection()) % 360;
	String powerPlayer1 = "Power: " + player1.getWeapon().getPower();
	String currentHealthPlayer1 = "Hp: " + player1.getHealth();
	String weaponAnglePlayer1 = "Angle: " + anglePlayer1;
	g2d.drawString(currentHealthPlayer1, fieldPlayer1XPos, healthFieldYPos);
	g2d.drawString(powerPlayer1, fieldPlayer1XPos, powerFieldYPos);
	g2d.drawString(player1Name, fieldPlayer1XPos, playerFieldYPos);
	g2d.drawString(weaponAnglePlayer1, fieldPlayer1XPos, angleFieldYPos);


	int anglePlayer2 = (int) abs((player2.getWeapon().getDirection()) % 360 - 180);
	String powerPlayer2 = "Power: " + player2.getWeapon().getPower();
	String currentHealthPlayer2 = "Hp: " + player2.getHealth();
	String weaponAnglePlayer2 = "Angle: " + anglePlayer2;
	g2d.drawString(currentHealthPlayer2, fieldPlayer2XPos, healthFieldYPos);
	g2d.drawString(powerPlayer2, fieldPlayer2XPos, powerFieldYPos);
	g2d.drawString(player2Name, fieldPlayer2XPos, playerFieldYPos);
	g2d.drawString(weaponAnglePlayer2, fieldPlayer2XPos, angleFieldYPos);
    }

    private void paintGround(Graphics2D g2d){
	int[] XCOORDS = gameBoard.getXCoords();
	int[] YCOORDS = gameBoard.getYCoords();

	g2d.setColor(Color.white);
	Polygon polygon = new Polygon(XCOORDS, YCOORDS,5);
	g2d.fillPolygon(polygon);
    }

    public void update() {
	this.repaint();
    }

}
