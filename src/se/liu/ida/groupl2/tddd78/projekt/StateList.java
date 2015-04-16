package se.liu.ida.groupl2.tddd78.projekt;

/**
 * Singleton containing a "FrameState", and a "GameFrame".
 * Have setters and getters for all fields that can be used to access and change the
 * components used in the game. Using "setFrameState" also changes the current component in the "frame"-obj.
 */

public final class StateList
{

    private FrameState frameState;

    private GameFrame frame;

    private final static StateList INSTANCE = new StateList();

    private StateList(){
        this.frameState = FrameState.MENU;

        this.frame = new GameFrame("Smrow");
    }

    public static StateList getInstance(){
	return INSTANCE;
    }

    public FrameState getFrameState() {
	return frameState;
    }

    public void setFrameState(final FrameState frameState) {
	this.frameState = frameState;
        if (frameState.equals(FrameState.MENU)) {
            frame.setCurrentComponent(frame.getMenuComponent());
        }
        else if (frameState.equals(FrameState.GAME)) {
            frame.setCurrentComponent(frame.getGameComponent());
        }
        else if (frameState.equals(FrameState.HIGHSCORE)) {
            frame.setCurrentComponent(frame.getHighscoreComponent());
        }
    }

    public GameFrame getFrame() {
	return frame;
    }

}
