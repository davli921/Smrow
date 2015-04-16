package se.liu.ida.groupl2.tddd78.projekt;

/**
 * Singleton containing a "FrameState", a "GameBoard", and a version of all three components, and also
 * a "GameFrame". Have setters and getters for all fields that can be used to access and change the
 * components used in the game.
 */

public final class StateList
{

    private FrameState frameState;

    private GameBoard gameBoard;

    private GameComponent gameComponent;
    private HighscoreComponent highscoreComponent;
    private MenuComponent menuComponent;

    private GameFrame frame;

    private final static StateList INSTANCE = new StateList();

    private StateList(){}

    public static StateList getInstance(){
	return INSTANCE;
    }

    public FrameState getFrameState() {
	return frameState;
    }

    public void setFrameState(final FrameState frameState) {
	this.frameState = frameState;
    }

    public GameBoard getGameBoard() {
	return gameBoard;
    }

    public void setGameBoard(final GameBoard gameBoard) {
	this.gameBoard = gameBoard;
    }

    public GameComponent getGameComponent() {
	return gameComponent;
    }

    public void setGameComponent(final GameComponent gameComponent) {
	this.gameComponent = gameComponent;
    }

    public HighscoreComponent getHighscoreComponent() {
	return highscoreComponent;
    }

    public void setHighscoreComponent(final HighscoreComponent highscoreComponent) {
	this.highscoreComponent = highscoreComponent;
    }

    public MenuComponent getMenuComponent() {
	return menuComponent;
    }

    public void setMenuComponent(final MenuComponent menuComponent) {
	this.menuComponent = menuComponent;
    }

    public GameFrame getFrame() {
	return frame;
    }

    public void setFrame(final GameFrame frame) {
	this.frame = frame;
    }
}
