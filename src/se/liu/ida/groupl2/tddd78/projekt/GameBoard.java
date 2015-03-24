package se.liu.ida.groupl2.tddd78.projekt;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.atan;
import static java.lang.Math.toRadians;

/**
 * GameBoard contains information about the playing field such as "width", "height", and "groundlevel". It also has two
 * Player-object and one Projectile-object. GameBoard has method to change and retrive the current player.
 */

public class GameBoard
{

    private double width,height,groundlevel;

    // One projectile at the time since it is turn-based
    private Projectile projectile;

    private Player player1;
    private Player player2;
    private boolean player1Turn, betweenTurns,chargingWeapon,gameOver;

    private long startTime;

    private List<Listener> listeners;
    private List<Integer> xCoords;
    private List<Integer> yCoords;

    public GameBoard(double width, double height) {
	this.width = width;
	this.height = height;
	// Two thirds of the board will be above ground
	this.groundlevel = (height / 3) * 2;
        double playerSize = Player.PLAYER_SIZE;

        this.player1Turn = true;
        this.betweenTurns = false;
        this.chargingWeapon = false;
        this.gameOver = false;

        this.startTime = System.currentTimeMillis();

        this.listeners = new ArrayList<>();

        // List over cords where the ground changes
        this.xCoords = new ArrayList<>();
        this.yCoords = new ArrayList<>();
        addGroundPoint(0,height);
        addGroundPoint(0,groundlevel);
        addGroundPoint(width/2,groundlevel-50);
        addGroundPoint(width,groundlevel);
        addGroundPoint(width,height);

        // Start values for player1
        double player1StartXPos = width / 10 - playerSize;
        double player1StartDir = Math.toDegrees(Math.atan(getGradient(player1StartXPos)));
        System.out.println(player1StartDir);
        double player1StartYPos = groundlevel - playerSize + (player1StartXPos+playerSize/2)*Math.tan(Math.toRadians(player1StartDir));
        this.player1 = new Player(player1StartXPos, player1StartYPos, player1StartDir, "MissileLauncher");

        // Start values for player2
        double player2StartXPos = width - width / 10;
        double player2StartDir = Math.toDegrees(Math.atan(getGradient(player2StartXPos)));
        System.out.println("2 DIR " + player2StartDir);
        double player2StartYPos = groundlevel - playerSize - (width-(player2StartXPos+playerSize/2))*Math.tan(Math.toRadians(player2StartDir));
        this.player2 = new Player(player2StartXPos, player2StartYPos, player2StartDir, "MissileLauncher");

    }

    // GETTERS & SETTERS ----------------------------------//

    public double getWidth() {
	return width;
    }

    public double getHeight() {
	return height;
    }

    public double getGroundlevel() {
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

    //---------------------------------------------------//

    public void setChargingWeapon(final boolean chargingWeapon) {
        this.chargingWeapon = chargingWeapon;
    }

    public void chargeWeapon(){
        Weapon currentWeapon = getCurrentPlayer().getWeapon();
        int currentPower = (int) currentWeapon.getPower();
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime- startTime;
        long chargeTime = Weapon.CHARGE_TIME;
        int maxPower = Weapon.MAX_POWER;

        if(!betweenTurns){
            if(deltaTime>chargeTime && currentPower < maxPower){
                currentPower++;
                currentWeapon.setPower(currentPower);
                startTime = System.currentTimeMillis();
            }
        }
    }

    // Creates a projectile using Weapon.shoot() then writes it as the
    // current projectile. Sets X and Y positions to a coordinate near the
    // firing player and sets "betweenTurns" to true
    public void shotsFired() {
        if (!betweenTurns) {
            Player currentPlayer = getCurrentPlayer();
            double playerSize = Player.PLAYER_SIZE;
            double middleXOfPlayer = currentPlayer.getXPos() + playerSize/2.0;
            double middleYOfPlayer = currentPlayer.getYPos() + playerSize/2.0;

            Weapon weapon = currentPlayer.getWeapon();
            double direction = weapon.getDirection();
            double weaponLenght = weapon.getLength();

            Projectile projectile = weapon.shoot();

            double spawnPointX = middleXOfPlayer + weaponLenght * cos(toRadians(direction));
            double spawnPointY = middleYOfPlayer + weaponLenght * sin(toRadians(direction));
            double x = spawnPointX;
            double y = spawnPointY;

            // Create it where the player is
            projectile.setXPos(x);
            projectile.setYPos(y);
            this.projectile = projectile;
            this.chargingWeapon = false;
            this.betweenTurns = true;
        }
    }

    // If String is "right" move right, if "left" ...
    // If invalid string do nothing
    public void moveCurrentPlayer(String horizontalDirection) {
        double xPos = getCurrentPlayer().getXPos()+Player.PLAYER_SIZE/2;
        double gradient = getGradient(xPos);
        double directionRadians = atan(gradient);
        double directionDegrees = Math.toDegrees(directionRadians);
        getCurrentPlayer().setDirection(directionDegrees);

        if (!betweenTurns && !chargingWeapon) {

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

    private double getGradient(double xPos){
        int i = 0;
        while(xPos >= xCoords.get(i)){
            i++;
        }

        double y2 = yCoords.get(i);
        double y1 = yCoords.get(i-1);
        double x2 = xCoords.get(i);
        double x1 = xCoords.get(i-1);

        // (-1) to compensate for y-axis pointing south.
        double gradient = (y2-y1)/(x2-x1);
        return gradient;
    }

    private void addGroundPoint(double xPos, double yPos){
        int x = (int) xPos;
        int y = (int) yPos;
        xCoords.add(x);
        yCoords.add(y);
    }

    public void rotateWeapon(String direction) {
        Player player = this.getCurrentPlayer();
        Weapon weapon = player.getWeapon();
        Double currentDirection = weapon.getDirection();

        if (!betweenTurns && !chargingWeapon) {
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

        double mXPos = moving.getXPos();
        double mYPos = moving.getYPos();
        double mWidth = moving.getWidth();
        double mHeight = moving.getHeight();

        double obstXPos = obstruction.getXPos();
        double obstYPos = obstruction.getYPos();
        double obstWidth = obstruction.getWidth();
        double obstHeight = obstruction.getHeight();

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

        double mXPos = moving.getXPos();
        double mYPos = moving.getYPos();
        double mWidth = moving.getWidth();
        double mHeight = moving.getHeight();
        double moveStep = Player.MOVE_STEP;

        double obstXPos = obstruction.getXPos();
        double obstYPos = obstruction.getYPos();
        double obstWidth = obstruction.getWidth();
        double obstHeight = obstruction.getHeight();

        // Collision with outer walls
        if ((direction == "left" && mXPos - moveStep < 0) || (direction == "right" && mXPos + mWidth + moveStep > width)) {
            willCollide = true;
        }

        // Is moving bottom below obstruction top && moving top above obstruction bottom
        // (Different statements with identical branches because of increased readability)
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
        double mXPos = moving.getXPos();
        double mYPos = moving.getYPos();
        double mWidth = moving.getWidth();
        double mHeight = moving.getHeight();

        // No roof outOfBound so it can still fall down even though it is not visible
        if (mXPos < 0 || mXPos + mWidth > width || mYPos + mHeight > groundlevel) {
            return true;
        } else {
            return false;
        }
    }

    // reset projectile, betweenTurns, and change turns. Also resets the power on the weapon
    private void resetProjectile() {
        this.projectile = null;
        getCurrentPlayer().getWeapon().setPower(0);
        this.betweenTurns = false;
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
        if(chargingWeapon){
            chargeWeapon();
        }
        notifyListener();
    }

}
