package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuComponent extends JComponent
{

    public MenuComponent() {
	InputMap inputMap = new InputMap();
	ActionMap actionMap = new ActionMap();

	Action startGame = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		TestClass.gameState = "game";
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "Start Game");
	this.getActionMap().put("Start Game", startGame);

    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(TestClass.WIDTH, TestClass.HEIGHT);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setColor(Color.BLUE);
	g2d.fillRect(0,0,TestClass.WIDTH,TestClass.HEIGHT);
    }
}
