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
    private final static int GROUNDLEVEL = 400;

    // Specific xcoords.
    private final static int X_COORD_3 = 200;
    private final static int X_COORD_4 = WIDTH / 2 -150;
    private final static int X_COORDS_5 = WIDTH / 2 +150;
    private final static int X_COORD_6 = WIDTH-200;

    // List of xcoords for the ground.
    private final static int[] XCOORDS = {0, 0, X_COORD_3, X_COORD_4, X_COORDS_5, X_COORD_6, WIDTH, WIDTH};

    // Specific ycoords
    private final static int Y_COORD_4 = GROUNDLEVEL - 50;
    private final static int Y_COORD_5 = GROUNDLEVEL - 50;

    // List of ycoords for the ground.
    private final static int[] YCOORDS = {HEIGHT, GROUNDLEVEL, GROUNDLEVEL, Y_COORD_4, Y_COORD_5, GROUNDLEVEL, GROUNDLEVEL, HEIGHT};

    // Colors for the GameBoard background and foreground.
    private final static int BLUE = 189;
    private final static int ALPHA = 89;
    private final static Color SKYCOLOR = new Color(0, 0, BLUE, ALPHA);
    private final static Color GROUNDCOLOR = Color.WHITE;

    // One projectile at the time since it is turn-based
    private Projectile projectile;

    private StatusField statusField;

    private boolean isBetweenTurns, isChargingWeapon, isGameOver;

    private long startTime;

    private List<Listener> listeners;

    private List<Player> players;
    private List<Obstacle> obstacles;

    public GameBoard() {
        this.projectile = null;

        this.isBetweenTurns = false;
        this.isChargingWeapon = false;
        this.isGameOver = false;

        this.startTime = System.currentTimeMillis();

        this.listeners = new ArrayList<>();

        this.obstacles = new ArrayList<>();
    /*
        final int boxXPos1 = WIDTH/2 -150;
        addBox(boxXPos1,getYCoord(boxXPos1)-Box.HEIGHT);
        final int boxXPos2 = WIDTH/2 -150 + (int)Box.WIDTH;
        addBox(boxXPos2,getYCoord(boxXPos2)-Box.HEIGHT);
        final int boxXPos3 = WIDTH/2 -150 + (int)Box.WIDTH/2;
        addBox(boxXPos3,getYCoord(boxXPos3)-Box.HEIGHT*2);
        final int boxXPos4 = WIDTH/2 +100;
        addBox(boxXPos4, getYCoord(boxXPos4)-Box.HEIGHT);
        addBox(boxXPos4, getYCoord(boxXPos4)-Box.HEIGHT*2);
    */

        // Player construction ------------------------------//
        this.players = new ArrayList<>();

        // Start values for player1
        final double player1StartXPos = WIDTH / 10.0 - Player.WIDTH;
        final double player1StartAngle = Math.toDegrees(atan(getGradient(player1StartXPos)));
        final double player1StartYPos = GROUNDLEVEL -Player.HEIGHT + (player1StartXPos +Player.WIDTH/2)*Math.tan(
                toRadians(player1StartAngle));

        Player player1 = new Player(player1StartXPos, player1StartYPos, player1StartAngle,Direction.RIGHT);

        // Start values for player2
        final double player2StartXPos = WIDTH - WIDTH / 10.0;
        final double player2StartAngle = Math.toDegrees(atan(getGradient(player2StartXPos)));
        final double player2StartYPos = GROUNDLEVEL -Player.HEIGHT -(WIDTH-(player2StartXPos +Player.WIDTH/2))*Math.tan(
                toRadians(player2StartAngle));

        Player player2 = new Player(player2StartXPos, player2StartYPos, player2StartAngle, Direction.LEFT);

        // Set angle so that the players face eachother from the start
        player2.getCurrentWeapon().setAngle(180 + player2StartAngle);

        this.players.add(player1);
        this.players.add(player2);

        players.get(0).setActive(true);

        this.statusField = new StatusField(players);
        // -------------------------------------------------//
    }

    // GETTERS & SETTERS ----------------------------------//

    public Player getCurrentPlayer() {
        Player currentPlayer = null;
        for (Player player : players) {
            if (player.isActive()) {
                currentPlayer = player;
            }
        }
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setIsChargingWeapon(final boolean isChargingWeapon) {
            this.isChargingWeapon = isChargingWeapon;
        }

    //---------------------------------------------------//

    // Uses the static coord lists to calculate and return the gradient in "xPos".
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

    //Increases current players weapons power up to the weapons "MAX_POWER".
    public void chargeWeapon(){
        if(!isBetweenTurns) {
            Weapon currentWeapon = getCurrentPlayer().getCurrentWeapon();
            int currentPower = (int) currentWeapon.getPower();
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - startTime;

            if (!isBetweenTurns) {
                if (deltaTime > Weapon.CHARGE_TIME && currentPower < Weapon.MAX_POWER) {
                    currentPower++;
                    currentWeapon.setPower(currentPower);
                    startTime = System.currentTimeMillis();
                }
            }
        }
    }

    // Changes the weapon angle.
    public void rotateWeapon(Direction direction) {
            Weapon weapon = getCurrentPlayer().getCurrentWeapon();
            Double angle = weapon.getAngle();

            if (!isBetweenTurns && !isChargingWeapon) {

                if (direction.equals(Direction.UP)) {
                    if (getCurrentPlayer().getDirection().equals(Direction.RIGHT)) {
                        weapon.setAngle(angle - 1);
                    } else if (getCurrentPlayer().getDirection().equals(Direction.LEFT)) {
                        weapon.setAngle(angle + 1);
                    }

                } else if (direction.equals(Direction.DOWN)) {
                    if (getCurrentPlayer().getDirection().equals(Direction.RIGHT)) {
                        weapon.setAngle(angle + 1);
                    } else if (getCurrentPlayer().getDirection().equals(Direction.LEFT)) {
                        weapon.setAngle(angle - 1);
                    }
                }
                notifyListener();
            }
        }

    // Creates a projectile using Weapon.shoot() then writes it as the
    // current projectile. Sets X and Y positions to a coordinate near the
    // firing player and sets "isBetweenTurns" to true
    public void shotsFired() {
        if (!isBetweenTurns) {
            Player currentPlayer = getCurrentPlayer();

            Weapon weapon = currentPlayer.getCurrentWeapon();
            double angle = weapon.getAngle();

            Projectile projectile = weapon.shoot();

            double x = currentPlayer.getWeaponJointX() + Weapon.LENGTH * cos(toRadians(angle));
            double y = currentPlayer.getWeaponJointY() + Weapon.LENGTH * sin(toRadians(angle));

            // Create it where the player is
            projectile.setXPos(x);
            projectile.setYPos(y);
            this.projectile = projectile;
            this.isChargingWeapon = false;
            this.isBetweenTurns = true;

            StateList stateList = StateList.getInstance();
            HighscoreComponent highscoreComponent = stateList.getFrame().getHighscoreComponent();

            highscoreComponent.addShotsFired(currentPlayer, 1);
        }
    }

    public void moveCurrentPlayer(Direction direction) {
        double xPos = getCurrentPlayer().getXPos();
        double middleXPos = getCurrentPlayer().getXPos()+Player.WIDTH /2;
        double gradient = getGradient(middleXPos);

        double angleInRadians = atan(gradient);
        double angleInDegrees = Math.toDegrees(angleInRadians);

        if (!isBetweenTurns && !isChargingWeapon) {

            getCurrentPlayer().setAngle(angleInDegrees);
            getCurrentPlayer().setDirection(direction);

            // Enables the player to turn on the spot.
            getCurrentPlayer().updateImg(direction);
            if (direction.equals(Direction.RIGHT)) {
                getCurrentPlayer().getCurrentWeapon().setAngle(angleInDegrees);
            } else if (direction.equals(Direction.LEFT)) {
                getCurrentPlayer().getCurrentWeapon().setAngle(180 + angleInDegrees);
            }

            Player currentPlayer = getCurrentPlayer();

            boolean freeToMove = true;

            // Check collision with obstacles
            for (Obstacle obstacle : obstacles) {
                if (willCollide(currentPlayer,direction,obstacle)) {
                    freeToMove = false;
                    break;
                }
            }

            // Check collision with the other players
            // (Compares pointers to make sure it is the same object)
            for (int i=0;i<players.size();i++) {
                if (players.get(i)!=currentPlayer && willCollide(currentPlayer,direction,players.get(i))) {
                    freeToMove = false;
                }
            }

            // The actual movement
            if (freeToMove) {
                if (direction.equals(Direction.RIGHT)) {
                    xPos += (Player.MOVE_STEP * cos(angleInRadians));
                } else if (direction.equals(Direction.LEFT)) {
                    xPos += (Player.MOVE_STEP * cos(angleInRadians+PI));
                }

                // New fields so the y-coords is calculated correctly with the current xPos.
                double newMiddleXPos = xPos + Player.WIDTH /2;
                double yPos = getYCoord(newMiddleXPos);
                currentPlayer.setXPos(xPos);
                currentPlayer.setYPos(yPos - Player.HEIGHT);

                currentPlayer.getHealthBar().updateHealthBar(
                        currentPlayer.getHealth(), currentPlayer.getXPos(), currentPlayer.getYPos());
            }

        }
        notifyListener();
    }

    // FIXA MED SKOTTLAGRING
    public void moveProjectile() {
        if (projectile != null) {

            projectile.move();

            StateList stateList = StateList.getInstance();
            HighscoreComponent highscoreComponent = stateList.getFrame().getHighscoreComponent();

            boolean collision = false;

            for (Obstacle obstacle : obstacles) {
                if (checkCollision(projectile, obstacle)) {
                    collision = true;
                    break;
                }
            }

            // Projectile out of bounds
            if (outOfBounds(projectile)) {
                collision = true;
            }
            /*
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
            */

            for (Player player : players) {
                if (checkCollision(projectile,player)) {
                    int currentHp = player.getHealth();
                    int dmg = projectile.getDmg();
                    player.setHealth(currentHp-dmg);
                    collision = true;
                    highscoreComponent.addHits(this.getCurrentPlayer(), 1);
                }
            }

            if (collision) {
                resetProjectile();
                nextTurn();
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

    private boolean willCollide(Collidable moving, Direction direction, Collidable obstruction) {

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
        if ((Objects.equals(direction, Direction.LEFT) && mXPos - moveStep < 0) ||
            (Objects.equals(direction, Direction.RIGHT) && mXPos + mWidth + moveStep > WIDTH)) {
            willCollide = true;
        }

        // Is moving bottom below obstruction top && moving top above obstruction bottom
        // (Different statements with identical branches because of increased readability)
        else if ((mYPos + mHeight >= obstYPos && mYPos <= obstYPos + obstHeight)) {
            // Collision if moving takes a step to the right
            if (Objects.equals(direction, Direction.RIGHT) && mXPos + mWidth <= obstXPos && mXPos + mWidth + moveStep > obstXPos) {
                willCollide = true;
            }
            // Collision if moving takes a step to the left
            else if (Objects.equals(direction, Direction.LEFT) && mXPos >= obstXPos + obstWidth && mXPos - moveStep < obstXPos + obstWidth) {
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

    // Reset projectile, isBetweenTurns, and change turns. Also resets the power on the weapon
    private void resetProjectile() {
        this.projectile = null;
        getCurrentPlayer().getCurrentWeapon().setPower(0);
        this.isBetweenTurns = false;
    }

    private void addBox(double xPos, double yPos) {
        Box box = new Box(xPos,yPos);
        this.obstacles.add(box);
    }

    public void nextTurn() {
        for (int i=0; i<players.size(); i++) {
            if (players.get(i).isActive()) {
                if (i==players.size()-1) {
                    players.get(i).setActive(false);
                    players.get(0).setActive(true);
                } else {
                    players.get(i).setActive(false);
                    players.get(i+1).setActive(true);
                }
                break;
            }
        }
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void notifyListener() {
        listeners.forEach(Listener::update);
    }

    // FIXA MED VEM SOM ÄR VINNARE
    public void checkGameOver() {
        StateList stateList = StateList.getInstance();
        HighscoreComponent highscoreComponent = stateList.getFrame().getHighscoreComponent();

        for (Player player : players) {
            if (player.getHealth()==0) {
                this.isGameOver = true;
            }
        }

        Player winner = players.get(0);
        int winnerHP = 0;
        for (Player player : players) {
            if (player.getHealth()>winnerHP) {
                winnerHP = player.getHealth();
                winner = player;
            }
        }
        highscoreComponent.setWinner(winner);

        if (isGameOver) {
            StateList.getInstance().setFrameState(FrameState.HIGHSCORE);
        }
    }

    public void draw(Graphics2D g2d) {
        // Paint the sky
        g2d.setColor(SKYCOLOR);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Paint ground
        g2d.setColor(GROUNDCOLOR);
        Polygon polygon = new Polygon(XCOORDS, YCOORDS, XCOORDS.length);
        g2d.fillPolygon(polygon);

        for (Player player : players) {
            player.draw(g2d);
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2d);
        }

        if (projectile != null) {projectile.draw(g2d);}

        statusField.draw(g2d);
    }

    public void tick() {
        checkGameOver();
        moveProjectile();
        if(isChargingWeapon){
            chargeWeapon();
        }
        notifyListener();
    }

}
