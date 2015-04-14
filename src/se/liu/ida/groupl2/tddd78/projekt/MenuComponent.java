package se.liu.ida.groupl2.tddd78.projekt;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The MenuComponent contains options in the form of buttons which you
 * can use to start,quit, modify player names or access the highscore list.
 */

public class MenuComponent extends JComponent
{

    public MenuComponent() {

	StateList sList = StateList.getInstance();

	this.setLayout(new MigLayout());

	JButton start = new JButton("Start");
	JButton highscore = new JButton("Highscore");
	JButton quit = new JButton("Quit");

	JLabel textLabel = new JLabel("Player1");
	JTextField textField = new JTextField();
	Dimension textDim = new Dimension(105, 10);
	textField.setPreferredSize(textDim);
	JButton enter = new JButton("Enter");

	ActionListener al = e -> {
	    if (e.getSource().equals(start)) {
		StateList.getInstance().setFrameState(FrameState.GAME);
		sList.getFrame().setComponent(sList.getGameComponent());
		StateList.getInstance().getGameBoard().getPlayer1().setName("Player1");
		StateList.getInstance().getGameBoard().getPlayer2().setName("Player2");
	    } else if (e.getSource().equals(quit)) {
		System.exit(0);
	    }
	    // The following branches sets player#:s name to
	    // the text "in textField"
	    else if (e.getSource().equals(enter) && textLabel.getText().equals("Player1")) {
		String player1Name = textField.getText();
		textLabel.setText("Player2");
		textField.setText("");
		textField.requestFocus();

		if (player1Name.isEmpty()) {
		    StateList.getInstance().getGameBoard().getPlayer1().setName("Player1");
		} else {
		StateList.getInstance().getGameBoard().getPlayer1().setName(player1Name);
		}
	    }

	    else if (e.getSource().equals(enter) && textLabel.getText().equals("Player2")) {
		String player2Name = textField.getText();

		if (player2Name.isEmpty()) {
		    StateList.getInstance().getGameBoard().getPlayer2().setName("Player2");
		} else {
		    StateList.getInstance().getGameBoard().getPlayer2().setName(player2Name);
		}

		sList.setFrameState(FrameState.GAME);
		sList.getFrame().setComponent(sList.getGameComponent());
	    }
	};

	start.addActionListener(al);
	quit.addActionListener(al);
	enter.addActionListener(al);

	this.add(start);
	this.add(highscore);
	this.add(quit, "wrap");
	this.add(textLabel);
	this.add(textField);
	this.add(enter);

    }
}
