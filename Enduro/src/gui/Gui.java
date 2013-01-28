package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame {

	private JPanel northPanel; 
	private JPanel southPanel;
	
	
	public Gui() {
		setTitle("ENDURO");
		setLayout(new BorderLayout());
		addPanels();
		
		
	}


	private void addPanels() {
		northPanel = new JPanel();
		southPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		
		
		
	}
	
	
	
}
