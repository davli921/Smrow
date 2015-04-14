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

    private static final int REFRESH_TIMER_MS = 5;

    private Smrow() {}

    public static void main(String[] args) {
	StateList sList = StateList.getInstance();

	// Framestate
	sList.setFrameState(FrameState.GAME);

	// Gameboard
	GameBoard gameBoard = new GameBoard();
	sList.setGameBoard(gameBoard);

	// Components
	GameComponent gameComponent = new GameComponent(gameBoard);
	HighscoreComponent highscoreComponent = new HighscoreComponent();
	MenuComponent menuComponent = new MenuComponent();
	sList.setGameComponent(gameComponent);
	sList.setHighscoreComponent(highscoreComponent);
	sList.setMenuComponent(menuComponent);

	// Frame
	GameFrame frame = new GameFrame("Smrow");
	sList.setFrame(frame);

	// Adds listener
	sList.getGameBoard().addListener(gameComponent);
	sList.getFrame().setVisible(true);

	// Timer
	final Action updateGameState = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if(sList.getFrameState().equals(FrameState.GAME)){
		    sList.getGameBoard().tick();
		}
	    }
	};
	final Timer clockTimer = new Timer(REFRESH_TIMER_MS, updateGameState);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

}
