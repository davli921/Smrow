package se.liu.ida.simka275davli921.tddd78.projekt;

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

    private FrameState frameState;


    public GameFrame(final String title) throws HeadlessException {
	super(title);
	this.createMenu();
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	this.gameBoard = new GameBoard();

	this.gameComponent = new GameComponent(gameBoard);
	this.highscoreComponent = new HighscoreComponent(gameBoard.getPlayers());
	this.menuComponent = new MenuComponent();

	// Assigns values to "frameState" and "currentComponent"
	setFrameState(FrameState.MENU);

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

    public void setCurrentComponent(final JComponent currentComponent) {
	if (this.currentComponent!=null) {
	    this.remove(this.currentComponent);
	}
	//this.remove(this.currentComponent);
	this.currentComponent = currentComponent;
	this.add(currentComponent);
	this.currentComponent.requestFocus();
	this.pack();
    }

    public FrameState getFrameState() {
	return frameState;
    }

    public void setFrameState(final FrameState frameState) {
    	this.frameState = frameState;
            if (frameState.equals(FrameState.MENU)) {
                setCurrentComponent(menuComponent);
            }
            else if (frameState.equals(FrameState.GAME)) {
		setCurrentComponent(gameComponent);
            }
	    else if (frameState.equals(FrameState.HIGHSCORE)) {
		setCurrentComponent(highscoreComponent);
	    }
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

		String p1Name = gameBoard.getPlayers().get(0).getName();
		String p2Name = gameBoard.getPlayers().get(1).getName();

		GameBoard gameBoard = new GameBoard();
		this.gameBoard = gameBoard;

		this.gameBoard.getPlayers().get(0).setName(p1Name);
		this.gameBoard.getPlayers().get(1).setName(p2Name);

		this.gameComponent = new GameComponent(gameBoard);

		this.highscoreComponent = new HighscoreComponent(gameBoard.getPlayers());

		this.gameBoard.addListener(gameComponent);

		//sList.setFrameState(FrameState.GAME);
		setFrameState(FrameState.GAME);

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
