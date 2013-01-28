package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import sort.Time;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private JPanel northPanel;
	private JScrollPane southPanel;
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
		addRespondToKey();			
		northPanel.add(textField, BorderLayout.WEST);
		northPanel.add(new RegisterButton(this), BorderLayout.EAST);
		southPanel = new JScrollPane();
		add(southPanel, BorderLayout.SOUTH);

	}

	private void addRespondToKey() {
		textField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					register();
				}			
			}});
	}

	private void southPanelSetUp() {
		this.textArea = new JTextArea(42,30);
		southPanel = new JScrollPane(textArea);
		add(southPanel, BorderLayout.CENTER);
		//southPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//southPanel.add(textArea);
		textArea.setEditable(false);
		//southPanel.setMinimumSize(new Dimension(400,400));
		textArea.setMinimumSize(new Dimension(400,400));
		//textArea.setPreferredSize(new Dimension(600, 500));
		// textArea.setMaximumSize(new Dimension(1400,1400));
	}

	public void register() {
		String comNr = textField.getText();
		textField.setText("");
		Time t = Time.fromCurrentTime();
		textArea.invalidate();
		repaint();
		textArea.setText(textArea.getText()+"\n"+comNr+"; "+t+"; ");
	}

}
