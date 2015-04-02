package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.JComponent;
import java.awt.*;

public class HighscoreComponent extends JComponent
{

    private float p1ShotsFired, p1Hits, p2ShotsFired, p2Hits;

    public HighscoreComponent() {
	this.p1ShotsFired = 10;
	this.p1Hits = 1;
	this.p2ShotsFired = 20;
	this.p2Hits = 6;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(300,300);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setColor(Color.white);
	g2d.fillRect(0,0,300,300);
	paintStats(g2d);
    }

    private void paintStats(Graphics2D g2d){
	float p1Accuracy = p1Hits/p1ShotsFired *100;
	float p2Accuracy = p2Hits/p2ShotsFired *100;
	g2d.setColor(Color.black);
	g2d.drawString("Player1 Accuracy: " + p1Accuracy + "%",30,30);
	g2d.drawString("Player2 Accuracy; " + p2Accuracy + "%", 30,60);
    }
}
