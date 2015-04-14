package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.JComponent;
import java.awt.*;

public class HighscoreComponent extends JComponent
{

    final static int WIDTH = 300;
    final static int HEIGHT = 300;
    private float p1ShotsFired, p1Hits, p2ShotsFired, p2Hits;

    public void addP1ShotsFired(final float p1ShotsFired) {
	this.p1ShotsFired += p1ShotsFired;
    }

    public void addP1Hits(final float p1Hits) {
	this.p1Hits += p1Hits;
    }

    public void addP2ShotsFired(final float p2ShotsFired) {
	this.p2ShotsFired += p2ShotsFired;
    }

    public void addP2Hits(final float p2Hits) {
	this.p2Hits += p2Hits;
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
	float p1Accuracy = p1Hits/p1ShotsFired *100;
	float p2Accuracy = p2Hits/p2ShotsFired *100;
	int xPosAccStat = 30;
	int p1yPosAccStat = 30;
	int p2yPosAccStat = 60;
	g2d.setColor(Color.black);
	g2d.drawString("Player1 Accuracy: " + p1Accuracy + "%",xPosAccStat,p1yPosAccStat);
	g2d.drawString("Player2 Accuracy; " + p2Accuracy + "%", xPosAccStat,p2yPosAccStat);
    }
}
