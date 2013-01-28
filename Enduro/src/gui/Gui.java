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

	private static final int maxNrOfEntriesShown = 5;
	private JPanel controlNorthPanel;
	private JScrollPane textCenterPanel;
	private JTextArea textArea;
	private JTextField textField;

	private Font bigFont = new Font("Times New Roman", Font.BOLD, 60);
	private GuiPrinter printer;

	public Gui(String output) {
		setTitle("ENDURO");
		setLayout(new BorderLayout());
		controlNorthPanelSetUp();
		textCentralPanelSetUp();
		
		printer = new GuiPrinter(output);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize);
	}

	private void controlNorthPanelSetUp() {
		
		controlNorthPanel = new JPanel();
		add(controlNorthPanel, BorderLayout.NORTH);
		
		textField = new JTextField(10);
		textField.setFont(bigFont);
		textField.setPreferredSize(new Dimension(400, 90));
		addRespondToKey();
		
		controlNorthPanel.add(textField, BorderLayout.WEST);
		controlNorthPanel.add(new RegisterButton(this), BorderLayout.EAST);
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
		
		this.textArea = new JTextArea(42, 30);
		textArea.setFont(bigFont);
		textArea.setEditable(false);
		textArea.setMinimumSize(new Dimension(400, 400));
		
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
