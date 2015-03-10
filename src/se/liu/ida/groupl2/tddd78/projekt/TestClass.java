package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

/**
 * TestClass...
 */

public final class TestClass
{

    private final static int WIDTH = 1000;
    private final static int HEIGHT = 750;
    private static final int REFRESH_TIMER_MS = 16;

    private TestClass() {}

    public static void main(String[] args) {
	final GameBoard gameBoard = new GameBoard(WIDTH, HEIGHT);
	GameComponent gameComponent = new GameComponent(gameBoard);
	GameFrame frame = new GameFrame("Spel", gameComponent);
	// Adds listener
	gameBoard.addListener(gameComponent);

	frame.setVisible(true);

	final Action updateGameState = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		gameBoard.tick();
	    }
	};

	final Timer clockTimer = new Timer(REFRESH_TIMER_MS, updateGameState);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

}
