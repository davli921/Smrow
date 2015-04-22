package se.liu.ida.groupl2.tddd78.projekt;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.atan;
import static java.lang.Math.toRadians;
import static java.lang.Math.PI;

/**
 * Represents the actual game. Contains fields for 2 "Player"-objects, a "Projectile"-obj.
 * Also contiains information about the layout in the form of "WIDTH", "HEIGHT", "GROUNDLEVEL" and
 * 2 list of y-coords and x-coords where change in the gradient of the ground occurs.
 * GameBoard controls all actions of players and projectiles (i.e movement and firing).
 */

public class GameBoard implements Drawable
{

    final static int WIDTH = 1000;
    final static int HEIGHT = 750;
    final static int GROUNDLEVEL = 400;

    final static int[] XCOORDS = {0, 0, 200, WIDTH / 2 -150, WIDTH / 2 +150, WIDTH-200, WIDTH, WIDTH};
    final static int[] YCOORDS = {HEIGHT, GROUNDLEVEL, GROUNDLEVEL, GROUNDLEVEL - 50, GROUNDLEVEL - 50, GROUNDLEVEL, GROUNDLEVEL, HEIGHT};
    final static Obstacle[] OBSTACLES = {new Obstacle(WIDTH / 2 -150,GROUNDLEVEL-100,50,50)};

    // One projectile at the time since it is turn-based
    private Projectile projectile;

    private Player player1;
    private Player player2;
    private boolean player1Turn, betweenTurns,chargingWeapon,gameOver;

    private long startTime;

    private List<Listener> listeners;


    public GameBoard() {
        this.projectile = null;

        this.player1Turn = true;
        this.betweenTurns = false;
        this.chargingWeapon = false;
        this.gameOver = false;

        this.startTime = System.currentTimeMillis();

        this.listeners = new ArrayList<>();

        // Player construction ------------------------------//
        double playerWidth = Player.PLAYER_WIDTH;
        double playerHeight = Player.PLAYER_HEIGHT;
        // Start values for player1
        double player1StartXPos = WIDTH / 10.0 - playerWidth;
        double player1StartDir = Math.toDegrees(atan(getGradient(player1StartXPos)));
        double player1StartYPos = GROUNDLEVEL - playerHeight + (player1StartXPos+playerWidth/2)*Math.tan(
                toRadians(player1StartDir));
        this.player1 = new Player(player1StartXPos, player1StartYPos, player1StartDir, "MissileLauncher","right");

        // Start values for player2
        double player2StartXPos = WIDTH - WIDTH / 10.0;
        double player2StartDir = Math.toDegrees(atan(getGradient(player2StartXPos)));
        double player2StartYPos = GROUNDLEVEL - playerHeight - (WIDTH-(player2StartXPos+playerWidth/2))*Math.tan(
                toRadians(player2StartDir));
        this.player2 = new Player(player2StartXPos, player2StartYPos, player2StartDir, "MissileLauncher","left");
        // Set direction so that the players face eachother from the start
        player2.getWeapon().setDirection(180 + player2StartDir);
        // -------------------------------------------------//
    }

    // GETTERS & SETTERS ----------------------------------//

    public double getWIDTH() {
	return WIDTH;
    }

    public double getHEIGHT() {
	return HEIGHT;
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

    private double getGradient(double xPos){
        int i = 0;
        while(xPos >= XCOORDS[i]){
            i++;
        }

        double y2 =YCOORDS[i];
        double y1 = YCOORDS[i-1];
        double x2 = XCOORDS[i];
        double x1 = XCOORDS[i-1];

        double gradient = (y2-y1)/(x2-x1);
        return gradient;
        }

    // Returns the groundlevel y-coords for the x-coord "xPos"
    // *Only works for linear groundlevels*
    private double getYCoord(double xPos) {
        int i = 0;
        while(xPos >= XCOORDS[i]){
            i++;
        }

        double y2 =YCOORDS[i];
        double y1 = YCOORDS[i-1];
        double x2 = XCOORDS[i];
        double x1 = XCOORDS[i-1];

        double gradient = (y2-y1)/(x2-x1);
        double constant = y2 - gradient*x2;

        double y = gradient*xPos + constant;
        return y;
    }

    public void setChargingWeapon(final boolean chargingWeapon) {
        this.chargingWeapon = chargingWeapon;
    }

    public void chargeWeapon(){
        if(!betweenTurns) {
            Weapon currentWeapon = getCurrentPlayer().getWeapon();
            int currentPower = (int) currentWeapon.getPower();
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - startTime;

            if (!betweenTurns) {
                if (deltaTime > Weapon.CHARGE_TIME && currentPower < Weapon.MAX_POWER) {
                    currentPower++;
                    currentWeapon.setPower(currentPower);
                    startTime = System.currentTimeMillis();
                }
            }
        }
    }

    public void rotateWeapon(String direction) {
            Player player = this.getCurrentPlayer();
            Weapon weapon = player.getWeapon();
            Double currentDirection = weapon.getDirection();

            if (!betweenTurns && !chargingWeapon) {
                int up = -1;
                int down = 1;
                // Intentional comparison of pointers.
                if (player == player1) {
                    if (direction.equals("Up")) {
                        weapon.setDirection(currentDirection + up);

                    } else if (direction.equals("Down")) {
                        weapon.setDirection(currentDirection + down);
                    }
                } else if (player == player2) {
                    if (Objects.equals(direction, "Up")) {
                        weapon.setDirection(currentDirection + down);

                    } else if (Objects.equals(direction, "Down")) {
                        weapon.setDirection(currentDirection + up);
                    }
                }
                notifyListener();
            }
        }

    // Creates a projectile using Weapon.shoot() then writes it as the
    // current projectile. Sets X and Y positions to a coordinate near the
    // firing player and sets "betweenTurns" to true
    public void shotsFired() {
        if (!betweenTurns) {
            Player currentPlayer = getCurrentPlayer();

            Weapon weapon = currentPlayer.getWeapon();
            double direction = weapon.getDirection();

            Projectile projectile = weapon.shoot();

            double x = currentPlayer.getWeaponJointX() + Weapon.LENGTH * cos(toRadians(direction));
            double y = currentPlayer.getWeaponJointY() + Weapon.LENGTH * sin(toRadians(direction));

            // Create it where the player is
            projectile.setXPos(x);
            projectile.setYPos(y);
            this.projectile = projectile;
            this.chargingWeapon = false;
            this.betweenTurns = true;

            StateList stateList = StateList.getInstance();
            HighscoreComponent highscoreComponent = stateList.getFrame().getHighscoreComponent();

            // Intentional use of pointer comparison
            if (currentPlayer == player1) {
                highscoreComponent.addP1ShotsFired(1);
            } else {
                highscoreComponent.addP2ShotsFired(1);
            }
        }
    }

    // If String is "right" move right, if "left" ...
    // If invalid string do nothing
    public void moveCurrentPlayer(String horizontalDirection) {
        double xPos = getCurrentPlayer().getXPos();
        double middleXPos = getCurrentPlayer().getXPos()+Player.PLAYER_WIDTH/2;
        double gradient = getGradient(middleXPos);
        double directionRadians = atan(gradient);
        double directionDegrees = Math.toDegrees(directionRadians);
        getCurrentPlayer().setDirection(directionDegrees);

        // Enables the player to turn on the spot.
        getCurrentPlayer().updateImg(horizontalDirection);
        if (horizontalDirection.equals("right")) {
            getCurrentPlayer().getWeapon().setDirection(directionDegrees);
        } else if (horizontalDirection.equals("left")) {
            getCurrentPlayer().getWeapon().setDirection(180+directionDegrees);
        }


        if (!betweenTurns && !chargingWeapon) {

            Player currentPlayer = getCurrentPlayer();

            boolean freeToMove = true;

            // Check collision with obstacles
            for (Obstacle obstacle : OBSTACLES) {
                if (willCollide(currentPlayer,horizontalDirection,obstacle)) {
                    freeToMove = false;
                    break;
                }
            }

            // Check collision with the other player
            // (Compares pointers to make sure it is the same object)
            if (currentPlayer == player1) {
                if (willCollide(currentPlayer, horizontalDirection, player2)) {
                    freeToMove = false;
                }
            } else if (currentPlayer == player2) {
                if (willCollide(currentPlayer, horizontalDirection, player1)) {
                    freeToMove = false;
                }
            }

            // The actual movement
            if (freeToMove) {
                if (horizontalDirection.equals("right")) {
                    xPos += (Player.MOVE_STEP * cos(directionRadians));
                } else if (horizontalDirection.equals("left")) {
                    xPos += (Player.MOVE_STEP * cos(directionRadians+PI));
                }

                // New fields so the y-coords is calculated correctly with the current xPos.
                double newMiddleXPos = xPos + Player.PLAYER_WIDTH/2;
                double yPos = getYCoord(newMiddleXPos);
                currentPlayer.setXPos(xPos);
                currentPlayer.setYPos(yPos - Player.PLAYER_HEIGHT);
                
                currentPlayer.getHealthBar().updateHealthBar();
            }

        }
        notifyListener();
    }
    
    public void moveProjectile() {
        if (projectile != null) {

            projectile.move();

            StateList stateList = StateList.getInstance();
            HighscoreComponent highscoreComponent = stateList.getFrame().getHighscoreComponent();

            boolean collision = false;

            for (Obstacle obstacle : OBSTACLES) {
                if (checkCollision(projectile, obstacle)) {
                    collision = true;
                    break;
                }
            }

            // Projectile out of bounds
            if (outOfBounds(projectile)) {
                collision = true;
            }
            // If collision with player, deal damage.
            else if (checkCollision(projectile, player1)) {
                int currentHp = player1.getHealth();
                int dmg = projectile.getDmg();
                player1.setHealth(currentHp - dmg);
                collision = true;
                highscoreComponent.addP2Hits(1);
            } else if (checkCollision(projectile, player2)) {
                int currentHp = player2.getHealth();
                int dmg = projectile.getDmg();
                player2.setHealth(currentHp - dmg);
                collision = true;
                highscoreComponent.addP1Hits(1);
            }

            if (collision) {
                resetProjectile();
            }

        }
    }

    // Collision controllers -------------------- //

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

    private boolean willCollide(Collidable moving, String direction, Collidable obstruction) {

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
        if ((Objects.equals(direction, "left") && mXPos - moveStep < 0) || (Objects.equals(direction, "right") && mXPos + mWidth + moveStep >
                                                                                                                  WIDTH)) {
            willCollide = true;
        }

        // Is moving bottom below obstruction top && moving top above obstruction bottom
        // (Different statements with identical branches because of increased readability)
        else if ((mYPos + mHeight >= obstYPos && mYPos <= obstYPos + obstHeight)) {
            // Collision if moving takes a step to the right
            if (Objects.equals(direction, "right") && mXPos + mWidth <= obstXPos && mXPos + mWidth + moveStep > obstXPos) {
                willCollide = true;
            }
            // Collision if moving takes a step to the left
            else if (Objects.equals(direction, "left") && mXPos >= obstXPos + obstWidth && mXPos - moveStep < obstXPos + obstWidth) {
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
        // Second statement seperate branch because "getYCoords" will not work with x-values
        // outside the "GameBoard".
        if (mXPos < 0 || mXPos + mWidth > WIDTH || mYPos + mHeight > getYCoord(mXPos) ) {
            return true;
        }
        else {
            return false;
        }
    }
    // ------------------------------------------- //

    // Reset projectile, betweenTurns, and change turns. Also resets the power on the weapon
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

    public void checkGameOver() {
        StateList stateList = StateList.getInstance();
        HighscoreComponent highscoreComponent = stateList.getFrame().getHighscoreComponent();

        int currentHpPlayer1 = player1.getHealth();
        int currentHpPlayer2 = player2.getHealth();

        if (currentHpPlayer1 <= 0) {
            highscoreComponent.setWinner(player2);
            this.gameOver = true;
        } else if (currentHpPlayer2 <= 0) {
            highscoreComponent.setWinner(player1);
            this.gameOver = true;
        }
        if (gameOver) {
            StateList.getInstance().setFrameState(FrameState.HIGHSCORE);
        }
    }

    public void draw(Graphics2D g2d, Player player) {
        // Colors
        Color lightblue = new Color(0, 0, 182, 89);

        GameBoard gameBoard = this;

        // Paint the sky
        g2d.setColor(lightblue);
        g2d.fillRect(0, 0, (int) gameBoard.getWIDTH(), (int) gameBoard.getHEIGHT());

        g2d.setColor(Color.white);
        Polygon polygon = new Polygon(GameBoard.XCOORDS, GameBoard.YCOORDS, GameBoard.XCOORDS.length);
        g2d.fillPolygon(polygon);

        player1.draw(g2d, player1);
        player2.draw(g2d, player2);

        Weapon weapon = player1.getWeapon();
        weapon.draw(g2d, player1);

        Weapon weapon2 = player2.getWeapon();
        weapon2.draw(g2d, player2);

        for (int i = 0; i < OBSTACLES.length; i++) {
            Obstacle obstacle = OBSTACLES[i];
            obstacle.draw(g2d, this.getCurrentPlayer());
        }

        if (projectile != null) {projectile.draw(g2d, gameBoard.getCurrentPlayer());}

        StatusField statusField = new StatusField(player1, player2);
        Player currentPlayer = this.getCurrentPlayer();

        statusField.draw(g2d, currentPlayer);
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
