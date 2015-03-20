package se.liu.ida.groupl2.tddd78.projekt;


/**
 * The Player class contains information about the objects position and its size. It has a list of listeners which it can notify
 * with the method "notifyStateListener". The methods "moveLeft" and "moveRight" subtracts and adds 1 to the x-position.
 */

public class Player implements Collidable
{

    final static int PLAYERSIZE = 35;
    final static int MOVESTEP = 5;
    final static int MAXHP = 100;
    private int xPos;
    private int yPos;
    private double direction;
    private int health;
    private HealthBar healthBar;
    private Weapon weapon;

    public Player(final int xPos, final int yPos, double direction, String weapon) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        this.health = 100;
        this.healthBar = new HealthBar(this);
        createWeapon(weapon);

    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public Weapon getWeapon() {
	return weapon;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
        this.healthBar.updateHealthBar();
    }

    public HealthBar getHealthBar() {
        return this.healthBar;
    }

    // These two do the same thing but are seperate so they fit in "Collidable"
    public int getWidth() {
        return PLAYERSIZE;
    }

    public int getHeight() {
        return PLAYERSIZE;
    }
    // ------------------------------------------------------------------------//

    public void move(String horizontalDirection) {
        if (horizontalDirection == "right") {
            xPos += MOVESTEP;
            this.healthBar.updateHealthBar();
        } else if (horizontalDirection == "left") {
            xPos -= MOVESTEP;
            this.healthBar.updateHealthBar();
        }
    }

    private void createWeapon(String weapon){
        switch (weapon){
            case "MissileLauncher":
                this.weapon = new MissileLauncher(direction);
                break;
            default:
                break;
        }
    }

}
