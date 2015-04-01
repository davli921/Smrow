package se.liu.ida.groupl2.tddd78.projekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuComponent extends JComponent
{

    public MenuComponent() {

	InputMap inputMap = new InputMap();
	ActionMap actionMap = new ActionMap();

	this.setLayout(new BorderLayout(0,10));

	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new BorderLayout());

	JButton start = new JButton("Start");
	JButton highscore = new JButton("Highscore");
	JButton quit = new JButton("Quit");

	ActionListener al = e -> {
	    if(e.getSource().equals(start)){
		TestClass.gameState = "game";
	    }
	    else if(e.getSource().equals(quit)){
		System.exit(0);
	    }
	};

	start.addActionListener(al);
	quit.addActionListener(al);

	buttonPanel.add(start, BorderLayout.NORTH);
	buttonPanel.add(highscore, BorderLayout.CENTER);
	buttonPanel.add(quit, BorderLayout.SOUTH);
	this.add(buttonPanel, BorderLayout.NORTH);

	JPanel textPanel = new JPanel();
	JLabel textLabel = new JLabel("Test");
	JTextField textField = new JTextField();
	textField.setPreferredSize(new Dimension(100,20));
	textPanel.add(textLabel,BorderLayout.NORTH);
	textPanel.add(textField, BorderLayout.SOUTH);
	this.add(textPanel, BorderLayout.CENTER);

	JButton enter = new JButton("Enter");
	this.add(enter, BorderLayout.SOUTH);

	Action write = new AbstractAction()
	{
	    @Override public void actionPerformed(final ActionEvent e) {
		System.out.println(textField.getText());
	    }
	};

	this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Write");
	this.getActionMap().put("Write", write);

    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(200,150);
    }
}
