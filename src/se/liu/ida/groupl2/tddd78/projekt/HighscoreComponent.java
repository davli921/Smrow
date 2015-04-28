package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.JComponent;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Displays information about the game after it is finished.
 */

public class HighscoreComponent extends JComponent
{

    final static int WIDTH = 300;
    final static int HEIGHT = 300;
    private String winner;
    private List<Player> players;

    private HashMap<Player, Float> shotsFired = new HashMap<>();
    private HashMap<Player, Float> hits = new HashMap<>();


    public HighscoreComponent(List<Player> players) {
	this.winner = null;
	this.players = players;
	for (Player player : players) {
	    shotsFired.put(player, 0f);
	    hits.put(player, 0f);
	}

    }

    public void addShotsFired(Player player, float shotsFired) {
	float sumShotsFired = this.shotsFired.get(player) + shotsFired;
	this.shotsFired.put(player, sumShotsFired);
    }

    public void addHits(Player player, float hits) {
	float sumHits = this.hits.get(player) + hits;
	this.hits.put(player, sumHits);
    }

    public void setWinner(Player player) {
	this.winner = player.getName();
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(WIDTH,HEIGHT);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setColor(Color.white);
	g2d.fillRect(0,0,WIDTH,HEIGHT);
	paintStats(g2d);
    }

    private void paintStats(Graphics2D g2d){
	final int xPosStat = 30;
	final int winneryPos = 30;

	g2d.setColor(Color.black);
	g2d.drawString("Player: " + this.winner + " has won the game!", xPosStat, winneryPos);

	for (int i = 0; i < players.size(); i++) {
	    Player player = players.get(i);
	    int yPosAccStat = 60 + 30 * i;
	    String playerName = player.getName();
	    float accuracy = 100 * (hits.get(player) / shotsFired.get(player));


	    g2d.drawString("Player: " + playerName + " " + accuracy + "%", xPosStat, yPosAccStat);
	}
    }
}
