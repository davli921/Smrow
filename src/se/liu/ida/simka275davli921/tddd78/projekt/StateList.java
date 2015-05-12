package se.liu.ida.simka275davli921.tddd78.projekt;

/**
 * Singleton containing a "FrameState", and a "GameFrame".
 * Have setters and getters for all fields that can be used to access and change the
 * components used in the game. Using "setFrameState" also changes the current component in the "frame"-obj.
 */

public final class StateList
{
    private GameFrame frame;

    private final static StateList INSTANCE = new StateList();

    private StateList(){
        this.frame = new GameFrame("Smrow");
    }

    public static StateList getInstance(){
	return INSTANCE;
    }

    public GameFrame getFrame() {
	return frame;
    }

}
