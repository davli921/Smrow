package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;
import java.awt.event.ActionEvent;

/**
 * Main
 */

public final class Smrow
{

    public final static int WIDTH = 1000;
    public final static int HEIGHT = 750;
    private static final int REFRESH_TIMER_MS = 5;

    // Test meny
    static FrameState frameState = FrameState.MENU;
    static FrameState frameLastState = FrameState.MENU;

    static GameBoard gameBoard = new GameBoard(WIDTH, HEIGHT);

    static GameComponent gameComponent = new GameComponent(gameBoard);
    static MenuComponent menuComponent = new MenuComponent();
    static HighscoreComponent highscoreComponent = new HighscoreComponent();

    static GameFrame frame = new GameFrame("Spel", menuComponent);

    private Smrow() {}

    public static void main(String[] args) {
	// Adds listener
	gameBoard.addListener(gameComponent);
	frame.setVisible(true);

	// TEST ---//
	frameState = FrameState.HIGHSCORE;
	// ------//

	final Action updateGameState = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if(frameState.equals(FrameState.GAME) && frameLastState.equals(FrameState.GAME)){
		    gameBoard.tick();
		}
		else if(frameState.equals(FrameState.GAME) && frameLastState.equals(FrameState.MENU)){
		    frame.setComponent(gameComponent);
		    frameLastState = FrameState.GAME;
		}
		else if(frameState.equals(FrameState.HIGHSCORE)){
		    frame.setComponent(highscoreComponent);
		}
	    }
	};

	final Timer clockTimer = new Timer(REFRESH_TIMER_MS, updateGameState);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

}
