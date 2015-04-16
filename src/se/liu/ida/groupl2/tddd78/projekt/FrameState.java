package se.liu.ida.groupl2.tddd78.projekt;

/**
 * Represents what state the game is in. "MENU" notifies the frame that the "menuComponent" should be displayed
 * "GAME" and "HIGHSCORE" works in the same way.
 */

public enum FrameState
{
    /**
     * "MENU" -> Frame displays the "MenuComponent".
     */
    MENU,

    /**
    * "GAME" -> Frame displays the "GameComponent":
    */
    GAME,

    /**
     * "HIGHSCORE" -> Frame displays the "HighscoreComponent".
     */
    HIGHSCORE
}
