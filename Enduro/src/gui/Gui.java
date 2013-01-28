package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sort.Time;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private static final int maxNrOfEntriesShown = 3;
	private JPanel controlNorthPanel;
	private JScrollPane textCenterPanel;
	private JTextArea textArea;
	private JTextField textField;

	private Font bigFont;
	private GuiPrinter printer;
	private Dimension screenSize;

	public Gui(String output) {
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		bigFont = new Font("Times New Roman", Font.BOLD, screenSize.height/7);
		 
		setTitle("ENDURO");
		setLayout(new BorderLayout());
		controlNorthPanelSetUp();
		textCentralPanelSetUp();
		
		printer = new GuiPrinter(output);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();

		setSize(screenSize);
	}

	private void controlNorthPanelSetUp() {
		
		controlNorthPanel = new JPanel();
		add(controlNorthPanel, BorderLayout.NORTH);
		
		int textFieldLength = (screenSize.width/3)/bigFont.getSize();
		textField = new JTextField(textFieldLength);
		textField.setFont(bigFont);
		//textField.setPreferredSize(textFieldDimension);
		addRespondToKey();
		
		controlNorthPanel.add(textField, BorderLayout.WEST);
		Dimension buttonDimension = new Dimension(screenSize.width/3, screenSize.height/7);
		controlNorthPanel.add(new RegisterButton(this, buttonDimension ), BorderLayout.EAST);
	}

	/**
	 * Makes the enter key have the same function as the button.
	 */
	private void addRespondToKey() {
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					register();
				}
			}
		});
	}

	private void textCentralPanelSetUp() {
		
		this.textArea = new JTextArea(6, 12);
		textArea.setFont(bigFont);
		textArea.setEditable(false);
		//textArea.setMinimumSize(textAreaDimension);
		
		textCenterPanel = new JScrollPane(textArea);
		add(textCenterPanel, BorderLayout.CENTER);
		
		// southPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// southPanel.add(textArea);
		// southPanel.setMinimumSize(new Dimension(400,400));
		// textArea.setPreferredSize(new Dimension(600, 500));
		// textArea.setMaximumSize(new Dimension(1400,1400));
	}

	/**
	 * Tells the program to read the textField and write to file etc.
	 */
	public void register() {
		String comNr = textField.getText();
		textField.setText("");
		
		Time t = Time.fromCurrentTime();
		String temp = comNr + "; " + t;
		
		printer.writeLine(temp);
		
		String[] temprows = textArea.getText().split("\\n");
		for (int i = 0; i < temprows.length && i < maxNrOfEntriesShown; i++) {
			temp = temp + "\n" + temprows[i];
		}
		textArea.setText(temp);
		textField.requestFocus();
		
		textArea.invalidate();
		repaint();
	}

}
