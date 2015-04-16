package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * GameFrame contains a "Jcomponent"-obj that is either a "menuComponent","gameComponent" or a "highscoreComponent"
 * that it displays depending on the "frameState". Has methods for changing component "setComponent",
 * and creating a menu "createMenu".
 */

public class GameFrame extends JFrame
{

    private JComponent component;

    // Gameframe ska ha component fält och setter till detta. Menucomponent i constructor,
    // den kan skappa ett gameboard och ändra till gamecomponent. Gameboard kollar gameover
    // och om sann så skapas highscoreComponent osv...
    public GameFrame(final String title) throws HeadlessException {
	super(title);
	this.createMenu();
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	// Add the correct component from StateList
	StateList sList = StateList.getInstance();
	if(sList.getFrameState().equals(FrameState.HIGHSCORE)){
	    this.component = sList.getHighscoreComponent();
	} else if(sList.getFrameState().equals(FrameState.GAME)){
	    this.component = sList.getGameComponent();
	} else if(sList.getFrameState().equals(FrameState.MENU)){
	    this.component = sList.getMenuComponent();
	}
	this.add(component);
	// Frame is not resizable
	this.setResizable(false);
	this.pack();
    }

    public void setComponent(final JComponent component) {
	this.remove(this.component);
	this.component = component;
	this.add(component);
	this.component.requestFocus();
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
