package se.liu.ida.groupl2.tddd78.projekt;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * GameBoard contains information about the playing field such as "width", "height", and "groundlevel". It also has two
 * Player-object and one Projectile-object. GameBoard has method to change and retrive the current player.
 */

public class GameBoard
{

    private int width;
    private int height;
    // Player & Projectile -objects only moveable above groundlevel
    private int groundlevel;

    // One projectile at the time since it is turn-based
    private Projectile projectile;

    private Player player1;
    private Player player2;
    private boolean player1Turn;
    // calm true between to turns
    private boolean calm;
    private boolean gameOver = false;

    private List<Listener> listeners;

    public GameBoard(final int width, final int height) {
	this.width = width;
	this.height = height;
	// Two thirds of the board will be above ground
	this.groundlevel = (height / 3) * 2;
	// Creates Player objects that start at groundlevel
	// and 1/10 in from the sides. Also assigns a Weapon to each player.
        this.player1 = new Player((width / 10 - Player.PLAYERSIZE), groundlevel - Player.PLAYERSIZE, new MissileLauncher(0));
        this.player2 = new Player((width - (width / 10)), groundlevel - Player.PLAYERSIZE, new MissileLauncher(180));
        this.player1Turn = true;
        this.listeners = new ArrayList<>();
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getGroundlevel() {
	return groundlevel;
    }

    public Player getPlayer1() {
	return player1;
    }

    public Player getPlayer2() {
	return player2;
    }

    public Player getCurrentPlayer() {
	if (player1Turn) {return player1;} else {return player2;}
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void shotsFired() {
        if (!calm) {
            Player currentPlayer = getCurrentPlayer();
            currentPlayer.getWeapon().setPower(1);
            int playerSize = Player.PLAYERSIZE;

            Weapon weapon = currentPlayer.getWeapon();
            double direction = weapon.getDirection();
            int weaponLenght = weapon.getLength();

            Projectile projectile = weapon.shoot();

            double spawnPointX = currentPlayer.getXPos() + playerSize / 2.0 + weaponLenght * cos(toRadians(direction));
            double spawnPointY = currentPlayer.getYPos() + playerSize / 2.0 + weaponLenght * sin(toRadians(direction));
            int x = (int) spawnPointX;
            int y = (int) spawnPointY;

            // Create it where the player is
            projectile.setXPos(x);
            projectile.setYPos(y);
            this.projectile = projectile;
            this.calm = true;
        }
    }

    // If String is "right" move right, if "left" ...
    // If invalid string do nothing
    public void moveCurrentPlayer(String horizontalDirection) {
        if (!calm) {

            Player currentPlayer = getCurrentPlayer();

            // Compares pointers to make sure it is the same object
            if (currentPlayer == player1) {
                if (!willCollide(currentPlayer, horizontalDirection, player2)) {
                    currentPlayer.move(horizontalDirection);
                }
            } else if (currentPlayer == player2) {
                if (!willCollide(currentPlayer, horizontalDirection, player1)) {
                    currentPlayer.move(horizontalDirection);
                }
            }
        }
        notifyListener();
    }

    public void rotateWeapon(String direction) {
        Player player = this.getCurrentPlayer();
        Weapon weapon = player.getWeapon();
        Double currentDirection = weapon.getDirection();

        if (!calm) {
            int up = -1;
            int down = 1;
            if (player == player1) {
                if (direction == "Up") {
                    weapon.setDirection(currentDirection + up);

                } else if (direction == "Down") {
                    weapon.setDirection(currentDirection + down);
                }
            } else if (player == player2) {
                if (direction == "Up") {
                    weapon.setDirection(currentDirection + down);

                } else if (direction == "Down") {
                    weapon.setDirection(currentDirection + up);
                }
            }
            notifyListener();
        }
    }
    
    public void moveProjectile() {
        if (projectile != null) {

            projectile.move();

            // Projectile out of bounds
            if (outOfBounds(projectile)) {
                resetProjectile();
            }
            // If collision with player, deal damage.
            else if (checkCollision(projectile, player1)) {
                int currentHp = player1.getHealth();
                int dmg = projectile.getDmg();
                player1.setHealth(currentHp - dmg);
                resetProjectile();
            } else if (checkCollision(projectile, player2)) {
                int currentHp = player2.getHealth();
                int dmg = projectile.getDmg();
                player2.setHealth(currentHp - dmg);
                resetProjectile();
            }
        }
    }

    public boolean hasWon() {
        int currentHpPlayer1 = player1.getHealth();
        int currentHpPlayer2 = player2.getHealth();

        if (currentHpPlayer1 <= 0) {
            System.out.println("Player 2 has won the game!");
            this.gameOver = true;
            return true;
        } else if (currentHpPlayer2 <= 0) {
            System.out.println("Player 1 has won the game!");
            this.gameOver = true;
            return true;
        } else { return false; }
    }

    public void checkGameOver() {
        this.hasWon();
        if (gameOver) {
            System.exit(0);
        }
    }


    // Returns true if there is a collision
    public boolean checkCollision(Collidable moving, Collidable obstruction){

        int mXPos = moving.getXPos();
        int mYPos = moving.getYPos();
        int mWidth = moving.getWidth();
        int mHeight = moving.getHeight();

        int obstXPos = obstruction.getXPos();
        int obstYPos = obstruction.getYPos();
        int obstWidth = obstruction.getWidth();
        int obstHeight = obstruction.getHeight();

        // Check if the bottom of moving is below the top of obst &&
        // check if the top of moving is above the bottom of obst &&
        //check if the left side of moving is to the left of the obst's right side &&
        // check if the right side of moving is to the right of the obst's left side
        // if all this is true a collision has occured
        if (mYPos + mHeight > obstYPos && mYPos < obstYPos + obstHeight &&
            mXPos < obstXPos + obstWidth && mXPos + mWidth > obstXPos) {
            return true;
        } else {
            return false;
        }
    }

    private boolean willCollide(Player moving, String direction, Collidable obstruction) {

        boolean willCollide = false;

        int mXPos = moving.getXPos();
        int mYPos = moving.getYPos();
        int mWidth = moving.getWidth();
        int mHeight = moving.getHeight();
        int moveStep = Player.MOVESTEP;

        int obstXPos = obstruction.getXPos();
        int obstYPos = obstruction.getYPos();
        int obstWidth = obstruction.getWidth();
        int obstHeight = obstruction.getHeight();

        // Collision with outer walls
        if ((direction == "left" && mXPos - moveStep < 0) || (direction == "right" && mXPos + mWidth + moveStep > width)) {
            willCollide = true;
        }

        // Is moving bottom below obstruction top && moving top above obstruction bottom
        else if ((mYPos + mHeight >= obstYPos && mYPos <= obstYPos + obstHeight)) {
            // Collision if moving takes a step to the right
            if (direction == "right" && mXPos + mWidth <= obstXPos && mXPos + mWidth + moveStep > obstXPos) {
                willCollide = true;
            }
            // Collision if moving takes a step to the left
            else if (direction == "left" && mXPos >= obstXPos + obstWidth && mXPos - moveStep < obstXPos + obstWidth) {
                willCollide = true;
            }
        }
        return willCollide;
    }

    private boolean outOfBounds(Collidable moving) {
        int mXPos = moving.getXPos();
        int mYPos = moving.getYPos();
        int mWidth = moving.getWidth();
        int mHeight = moving.getHeight();

        if (mXPos < 0 || mYPos < 0 || mXPos + mWidth > width || mYPos + mHeight > height) {
            return true;
        } else {
            return false;
        }
    }

    // reset projectile, calm, and change turns
    private void resetProjectile() {
        this.projectile = null;
        this.calm = false;
        nextTurn();
    }

    public void nextTurn() {
	if (player1Turn) {
	    player1Turn = false;
	} else {
	    player1Turn = true;
	}
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void notifyListener() {
        listeners.forEach(Listener::update);
    }

    public void tick() {
        checkGameOver();
        moveProjectile();
        notifyListener();
    }

}
