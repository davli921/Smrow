package se.liu.ida.groupl2.tddd78.projekt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Box extends Obstacle
{

    final static double WIDTH = 45;
    final static double HEIGHT = 45;

    private BufferedImage img;

    public Box(double xPos, double yPos) {
	super(xPos, yPos, WIDTH, HEIGHT);

	try {
	    this.img = ImageIO.read(this.getClass().getResourceAsStream("resources/box.png"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override public void draw(final Graphics2D g2d) {
	g2d.drawImage(img,(int)getXPos(),(int)getYPos(),null);
    }
}
