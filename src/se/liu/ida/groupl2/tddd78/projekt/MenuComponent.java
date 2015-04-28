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

    private int playerNumber = 0;

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

	    else if (e.getSource().equals(enter)) {
		String playerName = textField.getText();
		textLabel.setText("Player " +(playerNumber +2));
		textField.setText("");
		textField.requestFocus();

		if (playerName.isEmpty()) {
		    stateList.getFrame().getGameBoard().getPlayers().get(playerNumber).setName("Player " +(playerNumber +1));
		} else {
		    stateList.getFrame().getGameBoard().getPlayers().get(playerNumber).setName(playerName);
		}

		}
		this.playerNumber++;
	    	if (playerNumber ==stateList.getFrame().getGameBoard().getPlayers().size()) {
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
