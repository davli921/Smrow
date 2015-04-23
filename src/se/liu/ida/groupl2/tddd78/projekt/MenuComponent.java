package se.liu.ida.groupl2.tddd78.projekt;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The MenuComponent contains options in the form of buttons which you
 * can use to start,quit, or modify player names.
 */

public class MenuComponent extends JComponent
{

    public MenuComponent() {

	final int textFieldWidth = 105;
	final int textFieldHeight = 10;

	this.setLayout(new MigLayout());

	JButton start = new JButton("Start");
	JButton quit = new JButton("Quit");

	JLabel textLabel = new JLabel("Player1");
	JTextField textField = new JTextField();
	Dimension textDim = new Dimension(textFieldWidth, textFieldHeight);
	textField.setPreferredSize(textDim);
	JButton enter = new JButton("Enter");

	ActionListener al = e -> {
	    StateList stateList = StateList.getInstance();
	    if (e.getSource().equals(start)) {
		stateList.setFrameState(FrameState.GAME);
		stateList.getFrame().getGameBoard().getPlayer1().setName("Player1");
		stateList.getFrame().getGameBoard().getPlayer2().setName("Player2");
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
		    stateList.getFrame().getGameBoard().getPlayer1().setName("Player1");
		} else {
		    stateList.getFrame().getGameBoard().getPlayer1().setName(player1Name);
		}
	    }

	    else if (e.getSource().equals(enter) && textLabel.getText().equals("Player2")) {
		String player2Name = textField.getText();

		if (player2Name.isEmpty()) {
		    stateList.getFrame().getGameBoard().getPlayer2().setName("Player2");
		} else {
		    stateList.getFrame().getGameBoard().getPlayer2().setName(player2Name);
		}
		stateList.setFrameState(FrameState.GAME);
	    }
	};

	start.addActionListener(al);
	quit.addActionListener(al);
	enter.addActionListener(al);

	this.add(start, "span 2");
	this.add(quit, "wrap");
	this.add(textLabel);
	this.add(textField);
	this.add(enter);

    }
}
