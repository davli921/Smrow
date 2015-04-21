package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static java.lang.Math.toRadians;
import static java.lang.Math.abs;

/**
 * GameComponent is the visual representation of the playing field. Contains a GameBoard, the players-objects size, and the
 * projectile-objects size. Listens to the "GameBoard"-obj.
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

	Action changeWeapon = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		String weapon = this.getValue(e.getActionCommand()).toString();
		gameBoard.getCurrentPlayer().changeWeapon(weapon);
	    }

	};
	changeWeapon.putValue("1", "MissileLauncher");
	changeWeapon.putValue("2", "RocketLauncher");

	this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Move Left");
	this.getActionMap().put("Move Left", moveLeft);

	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Move Right");
	this.getActionMap().put("Move Right", moveRight);

	this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "Move Weapon Up");
	this.getActionMap().put("Move Weapon Up", moveWeaponUp);

	this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Move Weapon Down");
	this.getActionMap().put("Move Weapon Down", moveWeaponDown);

	// Use to charge the weapon and then fire
	this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"Charge Weapon");
	this.getActionMap().put("Charge Weapon", chargeWeapon);
	this.getInputMap().put(KeyStroke.getKeyStroke("released SPACE"),"Shoot");
	this.getActionMap().put("Shoot", shoot);

	// Use to change between weapons
	this.getInputMap().put(KeyStroke.getKeyStroke("1"), "Weapon1");
	this.getActionMap().put("Weapon1", changeWeapon);
	this.getInputMap().put(KeyStroke.getKeyStroke("2"), "Weapon2");
	this.getActionMap().put("Weapon2", changeWeapon);
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
	g2d.fillRect(0, 0, (int) gameBoard.getWIDTH(), (int) gameBoard.getHEIGHT());

	paintGround(g2d);

	paintPlayer(g2d, player1);
	paintWeapon(g2d, player1);

	paintPlayer(g2d, player2);
	paintWeapon(g2d, player2);

	paintObstacles(g2d);

	paintProjectile(g2d);

	paintStatusField(g2d);
    }

    private void paintPlayer(Graphics2D g2d, Player player) {
	player.draw(g2d, player);
    }

    private void paintWeapon(Graphics2D g2d, Player player) {
	Weapon weapon = player.getWeapon();
	weapon.draw(g2d, player);
    }

    private void paintProjectile(Graphics2D g2d) {
	Projectile projectile = gameBoard.getProjectile();
	if (projectile != null) {projectile.draw(g2d, gameBoard.getCurrentPlayer());}
    }

    private void paintObstacles(Graphics2D g2d) {
	for (int i = 0; i < GameBoard.OBSTACLES.length; i++) {
	    Obstacle obstacle = GameBoard.OBSTACLES[i];
	    obstacle.draw(g2d, gameBoard.getCurrentPlayer());
	}
    }

    private void paintStatusField(Graphics2D g2d) {
	StatusField statusField = new StatusField(player1, player2);
	Player currentPlayer = gameBoard.getCurrentPlayer();

	statusField.draw(g2d, currentPlayer);
    }

    private void paintGround(Graphics2D g2d){
	g2d.setColor(Color.white);
	Polygon polygon = new Polygon(GameBoard.XCOORDS, GameBoard.YCOORDS ,GameBoard.XCOORDS.length);
	g2d.fillPolygon(polygon);
    }

    public void update() {
	this.repaint();
    }

}
