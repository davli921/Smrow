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

    public final static int WIDTH = 1000;
    public final static int HEIGHT = 750;
    private static final int REFRESH_TIMER_MS = 5;

    // TEst meny
    static String gameState = "menu";
    static String lastState = "menu";

    private TestClass() {}

    public static void main(String[] args) {
	final GameBoard gameBoard = new GameBoard(WIDTH, HEIGHT);
	GameComponent gameComponent = new GameComponent(gameBoard);

	MenuComponent menuComponent = new MenuComponent();

	GameFrame frame = new GameFrame("Spel", menuComponent);
	// Adds listener
	gameBoard.addListener(gameComponent);
	frame.setVisible(true);

	//TEST

	final Action updateGameState = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if(gameState.equals("game") && lastState.equals("game")){
		    gameBoard.tick();
		}
		else if(gameState.equals("game") && lastState.equals("menu")){
		    frame.setComponent(gameComponent);
		    lastState = "game";
		}

	    }
	};

	final Timer clockTimer = new Timer(REFRESH_TIMER_MS, updateGameState);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

}
