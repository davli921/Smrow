package se.liu.ida.simka275davli921.tddd78.projekt;

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

	// Adds listener
	sList.getFrame().getGameBoard().addListener(sList.getFrame().getGameComponent());
	sList.getFrame().setVisible(true);

	// Timer
	final Action updateGameState = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		if(sList.getFrame().getFrameState().equals(FrameState.GAME)){
		    sList.getFrame().getGameBoard().tick();
		}
	    }
	};
	final Timer clockTimer = new Timer(REFRESH_TIMER_MS, updateGameState);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

}
