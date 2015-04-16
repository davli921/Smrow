package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * GameFrame contains a "currentComponent" that is either a "menuComponent","gameComponent" or a "highscoreComponent"
 * that it displays depending on the "frameState". Has methods for changing currentComponent "setCurrentComponent",
 * and creating a menu "createMenu". All three available components are stored in their own field.
 */

public class GameFrame extends JFrame
{

    private GameBoard gameBoard;

    private GameComponent gameComponent;
    private HighscoreComponent highscoreComponent;
    private MenuComponent menuComponent;
    private JComponent currentComponent;


    public GameFrame(final String title) throws HeadlessException {
	super(title);
	this.createMenu();
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	this.gameBoard = new GameBoard();

	this.gameComponent = new GameComponent(gameBoard);
	this.highscoreComponent = new HighscoreComponent();
	this.menuComponent = new MenuComponent();
	this.currentComponent = menuComponent;

	this.add(currentComponent);
	// Frame is not resizable
	this.setResizable(false);
	this.pack();
    }

    public GameBoard getGameBoard() {
	return gameBoard;
    }

    public GameComponent getGameComponent() {
	return gameComponent;
    }

    public HighscoreComponent getHighscoreComponent() {
	return highscoreComponent;
    }

    public MenuComponent getMenuComponent() {
	return menuComponent;
    }

    public void setCurrentComponent(final JComponent currentComponent) {
	this.remove(this.currentComponent);
	this.currentComponent = currentComponent;
	this.add(currentComponent);
	this.currentComponent.requestFocus();
	this.pack();
    }

    private void createMenu() {
	final JMenu menu = new JMenu("Menu");
	final JMenuItem restart = new JMenuItem("Restart");
	final JMenuItem exit = new JMenuItem("Exit");
	menu.add(restart);
	menu.addSeparator();
	menu.add(exit);

	//Creates a action listener for the menuitems.
	final ActionListener actionListener = e -> {
	    if (e.getSource().equals(restart)) {
		// Not finished!
		System.out.println("restartsssss");
	    } else if (e.getSource().equals(exit)) {
		System.exit(0);
	    }
	};

	restart.addActionListener(actionListener);
	exit.addActionListener(actionListener);

	final JMenuBar menuBar = new JMenuBar();
	menuBar.add(menu);
	this.setJMenuBar(menuBar);
    }

}
