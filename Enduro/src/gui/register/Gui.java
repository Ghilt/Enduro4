package gui.register;

import gui.tools.EntryField;
import gui.tools.GuiPrinter;

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

import members.Formater;
import members.Time;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private static final int MAX_ENTRIES_SHOWN = 4;

	private JPanel controlNorthPanel;
	private JScrollPane textCenterPanel;
	private JTextArea textArea;
	private EntryField textField;

	private RegisterButton entryButton;
	private UndoButton undoButton;

	private Font bigFont;
	private Font smallFont;

	private GuiPrinter printer;
	private Dimension screenSize;

	private boolean emptyEntry;

	/**
	 * A simple frame for entering times of racers.
	 * 
	 * @param output
	 *            The file to write the entries to.
	 */
	public Gui(String output) {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		bigFont = new Font("Times New Roman", Font.BOLD, screenSize.height / 8);
		smallFont = new Font("Times New Roman", Font.BOLD,
				screenSize.height / 14);

		setLayout(new BorderLayout());
		controlNorthPanelSetUp();
		textCentralPanelSetUp();
		setTitle("ENDURO");

		printer = new GuiPrinter(output);
		emptyEntry = !printer.lastRowHasNr();
		entryButton.setText(emptyEntry ? RegisterButton.REQUEST_ENTRY
				: RegisterButton.DEFAULT_TEXT);
		if (emptyEntry) {
			textArea.setText(printer.getLastRow());
			undoButton.setEnabled(true);
		}

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();

		setSize(screenSize);
	}

	/**
	 * Sets up the upper bar and its formatting.
	 */
	private void controlNorthPanelSetUp() {

		// Format dimensions
		int textFieldLength = (screenSize.width / 8) / bigFont.getSize();
		Dimension buttonDimension = new Dimension(screenSize.width / 3,
				screenSize.height / 7);

		// Add the Panel
		controlNorthPanel = new JPanel();
		controlNorthPanel.setLayout(new BorderLayout());
		add(controlNorthPanel, BorderLayout.NORTH);

		// Create text field
		textField = new EntryField();
		textField.setFont(bigFont);
		textField.setColumns(textFieldLength);
		addRespondToKey();
		controlNorthPanel.add(textField, BorderLayout.CENTER);

		// Add register button
		entryButton = new RegisterButton(this, buttonDimension);
		controlNorthPanel.add(entryButton, BorderLayout.EAST);

		// Add undo button
		undoButton = new UndoButton(this, buttonDimension);
		undoButton.setText(UndoButton.DEFAULT_TEXT);
		undoButton.setEnabled(false);
		controlNorthPanel.add(undoButton, BorderLayout.WEST);
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

	/**
	 * Sets up the central text field showing recent entries.
	 */
	private void textCentralPanelSetUp() {

		// Format and create text area.
		this.textArea = new JTextArea(6, 12);
		textArea.setFont(bigFont);
		textArea.setFocusable(false);
		textArea.setEditable(false);

		// Add the panel
		textCenterPanel = new JScrollPane(textArea);
		add(textCenterPanel, BorderLayout.CENTER);

	}

	/**
	 * Tells the program to read the textField and write to file, update
	 * textArea, and act on emptyEntry
	 */
	public void register() {
		// Read and then flush the textField
		String competitorNumber = textField.getText();
		textField.setText("");

		// Format the new entry.
		Time t = Time.fromCurrentTime();
		String temp = Formater.formatColumns(competitorNumber, t);

		if (competitorNumber.isEmpty()) {
			entryButton.setText(RegisterButton.REQUEST_ENTRY);
			updateTextArea(temp);
			if (!emptyEntry)
				printer.writeLine(temp);
			emptyEntry = true;
		} else {
			if (emptyEntry) {
				printer.enterLateNumber(competitorNumber);
				emptyEntry = false;
				entryButton.setText(RegisterButton.DEFAULT_TEXT);
				addToStartOfTextArea(competitorNumber);
			} else {
				printer.writeLine(temp);
				updateTextArea(temp);
			}
		}
		undoButton.setEnabled(emptyEntry);

		// Finishing updates
		textField.requestFocus();
		textArea.invalidate();
		repaint();
	}

	/**
	 * Removes the last line of the file and the top line of the textArea
	 */
	public void undo() {
		if (emptyEntry) {
			printer.clearLastLine();
			emptyEntry = false;
			undoButton.setEnabled(emptyEntry);
			String temp = "";
			String[] temprows = textArea.getText().split("\\n");
			for (int i = 1; i < temprows.length && i < MAX_ENTRIES_SHOWN; i++) {
				temp = temp + temprows[i] + "\n";
			}
			textArea.setText(temp);

			// Finishing updates
			textField.requestFocus();
			textArea.invalidate();
			repaint();
		}
	}

	/**
	 * In case of empty entry, the next valid entry adds to the first line
	 * instead of creating a new one.
	 * 
	 * @param comNr
	 */
	private void addToStartOfTextArea(String comNr) {
		String[] str = textArea.getText().split(
				System.getProperty("line.separator"));
		str[0] = comNr + str[0];
		String newText = "";
		for (String s : str) {
			if (!s.isEmpty())
				newText = newText + s + System.getProperty("line.separator");
		}
		textArea.setText(newText);
		textArea.setCaretPosition(0);
	}

	/**
	 * Updates the TextArea with a new line. Valid or invalid.
	 * 
	 * @param temp
	 * 
	 */
	private void updateTextArea(String temp) {
		if (printer.lastRowHasNr()) {
			String[] temprows = textArea.getText().split("\\n");
			for (int i = 0; i < temprows.length && i + 1 < MAX_ENTRIES_SHOWN; i++) {
				temp = temp + "\n" + temprows[i];

			}
			textArea.setText(temp);
		}
	}

	public Font getSmallFont() {
		return smallFont;
	}

	public Font getBigFont() {
		return bigFont;
	}

}
