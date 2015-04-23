package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * GameComponent is the visual representation of the playing field. Contains a GameBoard, the players-objects size, and the
 * projectile-objects size. Listens to the "GameBoard"-obj.
 */

public class GameComponent extends JComponent implements Listener
{

    private GameBoard gameBoard;

    public GameComponent(final GameBoard gameBoard) {
	this.gameBoard = gameBoard;

	// Event Handling
	Action moveLeft = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.moveCurrentPlayer(Direction.LEFT);
	    }
	};

	Action moveRight = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.moveCurrentPlayer(Direction.RIGHT);
	    }
	};

	Action moveWeaponUp = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.rotateWeapon(Direction.UP);
	    }
	};

	Action moveWeaponDown = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.rotateWeapon(Direction.DOWN);
	    }
	};

	Action chargeWeapon = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.setIsChargingWeapon(true);
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
	return new Dimension(GameBoard.WIDTH, GameBoard.HEIGHT);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	paintBoard(g2d);
    }

    private void paintBoard(Graphics2D g2d) {
	gameBoard.draw(g2d);
    }

    public void update() {
	this.repaint();
    }

}
