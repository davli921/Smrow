package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The GameFrame contains a GameComponent, is also has a method "createMenu"that creates a menu with the options "Restart" and
 * "Exit" which does what you might imagine. The GameFrame is also non-resizable by default.
 */

public class GameFrame extends JFrame
{

    private JComponent component;

    // Gameframe ska ha component fält och setter till detta. Menucomponent i constructor,
    // den kan skappa ett gameboard och ändra till gamecomponent. Gameboard kollar gameover
    // och om sann så skapas highscoreComponent osv...
    public GameFrame(final String title, JComponent component) throws HeadlessException {
	super(title);
	//this.setLayout(new BorderLayout());
	this.createMenu();
	this.component = component;
	this.add(component);
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
	final JMenu menu = new JMenu("Alpha");
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
