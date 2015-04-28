package se.liu.ida.groupl2.tddd78.projekt;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.util.List;
import java.awt.event.ActionListener;

/**
 * The MenuComponent contains options in the form of buttons which you
 * can use to start,quit, or modify player names.
 */

public class MenuComponent extends JComponent
{

    private int n = 0;

    public MenuComponent() {

	final int textFieldWidth = 105;
	final int textFieldHeight = 10;

	this.setLayout(new MigLayout());

	JButton start = new JButton("Start");
	JButton quit = new JButton("Quit");

	JLabel textLabel = new JLabel("Player 1");
	JTextField textField = new JTextField();
	Dimension textDim = new Dimension(textFieldWidth, textFieldHeight);
	textField.setPreferredSize(textDim);
	JButton enter = new JButton("Enter");

	ActionListener al = e -> {
	    StateList stateList = StateList.getInstance();
	    if (e.getSource().equals(start)) {
		stateList.setFrameState(FrameState.GAME);
		List<Player> players = stateList.getFrame().getGameBoard().getPlayers();
		for (int i=0; i<players.size(); i++) {
		    players.get(i).setName("Player " + (i+1));
		}
	    } else if (e.getSource().equals(quit)) {
		System.exit(0);
	    }

	    /*
	    // The following branches sets player#:s name to
	    // the text "in textField"
	    else if (e.getSource().equals(enter) && textLabel.getText().equals("Player 1")) {
		String player1Name = textField.getText();
		textLabel.setText("Player 2");
		textField.setText("");
		textField.requestFocus();

		if (player1Name.isEmpty()) {
		    stateList.getFrame().getGameBoard().getPlayers().get(0).setName("Player1");
		} else {
		    stateList.getFrame().getGameBoard().getPlayers().get(0).setName(player1Name);
		}
	    }

	    else if (e.getSource().equals(enter) && textLabel.getText().equals("Player 2")) {
		String player2Name = textField.getText();

		if (player2Name.isEmpty()) {
		    stateList.getFrame().getGameBoard().getPlayers().get(1).setName("Player 2");
		} else {
		    stateList.getFrame().getGameBoard().getPlayers().get(1).setName(player2Name);
		}
		stateList.setFrameState(FrameState.GAME);
	    }
	    */

	    else if (e.getSource().equals(enter)) {
		String playerName = textField.getText();
		if (n==stateList.getFrame().getGameBoard().getPlayers().size()) {
		    stateList.setFrameState(FrameState.GAME);
		} else {
		    textLabel.setText("Player " +(n+2));
		    textField.setText("");
		    textField.requestFocus();

		    if (playerName.isEmpty()) {
			stateList.getFrame().getGameBoard().getPlayers().get(n).setName("Player " +(n+1));
		    } else {
			stateList.getFrame().getGameBoard().getPlayers().get(n).setName(playerName);
		    }

		}
		this.n++;
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
