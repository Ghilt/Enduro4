package server;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGui extends JFrame {
	private JTextArea textArea;
	private Font bigFont;
	private StringBuilder sb;

	public ServerGui() {
		super();
		this.setTitle("Server");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize = new Dimension((int)screenSize.getHeight(), (int)screenSize.getWidth()/4);
		bigFont = new Font("Times New Roman", Font.BOLD, screenSize.height / 8);
		this.setPreferredSize(screenSize);
		
		sb = new StringBuilder();
		sb.append("Server up and running.\n");
		
		
		textCentralPanelSetUp();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();

		setSize(screenSize);
		
		
	}
	
	private void textCentralPanelSetUp() {

		// Format and create text area.
		this.textArea = new JTextArea(6, 12);
		textArea.setFont(bigFont);
		textArea.setText(sb.toString());
		textArea.setFocusable(false);
		textArea.setEditable(false);
		

		// Add the panel
		Panel panel = new Panel();
		panel.add(textArea);
		add(panel);

	}

	public void setText(String string) {
		sb.append(string + "\n");
		textArea.setText(sb.toString());
	}
}
