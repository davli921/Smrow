package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.JComponent;
import java.awt.*;

public class HighscoreComponent extends JComponent
{

    final static int WIDTH = 300;
    final static int HEIGHT = 300;
    private float p1ShotsFired, p1Hits, p2ShotsFired, p2Hits;

    public HighscoreComponent() {
	this.p1ShotsFired = 10;
	this.p1Hits = 1;
	this.p2ShotsFired = 20;
	this.p2Hits = 6;
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
