package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

/**
 * The GameFrame contains a GameComponent, is also has a method "createMenu"that creates a menu with the options "Restart" and
 * "Exit" which does what you might imagine. The GameFrame is also non-resizable by default.
 */

public class GameFrame extends JFrame
{

    public GameFrame(final String title, GameComponent gameComponent) throws HeadlessException {
	super(title);
	this.setLayout(new BorderLayout());
	this.createMenu();
	this.add(gameComponent);
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	// Frame is not resizable
	this.setResizable(false);
	this.pack();
    }

    private void createMenu() {
	final JMenu alpha = new JMenu("Alpha");
	final JMenuItem restart = new JMenuItem("Restart");
	final JMenuItem exit = new JMenuItem("Exit");
	alpha.add(restart);
	alpha.addSeparator();
	alpha.add(exit);

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
	menuBar.add(alpha);
	this.setJMenuBar(menuBar);
    }

}
