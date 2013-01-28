package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sort.Time;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private JPanel northPanel;
	private JPanel southPanel;
	private JTextArea textArea;
	private JTextField textField;

	public Gui() {
		setTitle("ENDURO");
		setLayout(new BorderLayout());
		northPanelSetUp();
		southPanelSetUp();

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setResizable(false);
		setVisible(true);
		pack();

	}

	private void northPanelSetUp() {
		northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		textField = new JTextField(25);
		northPanel.add(textField, BorderLayout.WEST);
		northPanel.add(new RegisterButton(this), BorderLayout.EAST);
		southPanel = new JPanel();
		add(southPanel, BorderLayout.SOUTH);

	}

	private void southPanelSetUp() {
		southPanel = new JPanel();
		add(southPanel, BorderLayout.CENTER);
		this.textArea = new JTextArea("");
		southPanel.add(textArea);
		textArea.setEditable(false);
		// southPanel.setMinimumSize(new Dimension(400,400));
		// textArea.setMinimumSize(new Dimension(400,400));
		textArea.setPreferredSize(new Dimension(600, 500));
		// textArea.setMaximumSize(new Dimension(1400,1400));
	}

	public void register() {
		String comNr = textField.getText();
		textField.setText("");
		Time t = Time.fromCurrentTime();
		textArea.setText(textArea.getText()+"\n"+comNr+"; "+t+"; ");
	}

}
