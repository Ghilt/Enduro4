package gui.register;

import gui.tools.GuiPrinter;
import gui.tools.NumberEntryField;
import io.Formater;
import io.reader.IntervalParser;
import io.reader.IntervalParser.Interval;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import client.Output;

import members.Time;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private static final int MAX_ENTRIES_SHOWN = 4;

	private JPanel controlNorthPanel;
	private JScrollPane textCenterPanel;
	private JTextArea textArea;
	private NumberEntryField textField;

	private RegisterButton entryButton;
	private UndoButton undoButton;

	private Font bigFont;
	private Font smallFont;

	private Dimension screenSize;

	private boolean emptyEntry;

	private Output out;

	private GuiPrinter printer;

	private Time lateTime;

	/**
	 * A simple frame for entering times of racers.
	 */
	public Gui(Output out, String filePath) {
		this.out = out;

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		bigFont = new Font("Times New Roman", Font.BOLD, screenSize.height / 8);
		smallFont = new Font("Times New Roman", Font.BOLD,
				screenSize.height / 14);

		setLayout(new BorderLayout());
		controlNorthPanelSetUp();
		textCentralPanelSetUp();
		setTitle("ENDURO");

		printer = new GuiPrinter(filePath);
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
		textField = new NumberEntryField();
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
		String entry = textField.getText();
		textField.setText("");

		// Format the new entry.
		Time t = Time.fromCurrentTime();
		String temp = Formater.formatColumns(entry, t);
		IntervalParser p = new IntervalParser(entry);

		if (entry.isEmpty() || !p.isValid()) {
			lateTime = t;
			entryButton.setText(RegisterButton.REQUEST_ENTRY);
			String textOutput = p.isValid() ? temp : Formater.formatColumns("",
					t);

			updateTextArea(textOutput);
			if (!emptyEntry)
				printer.writeLines(false, textOutput);

			emptyEntry = true;
		} else {
			if (emptyEntry) {
				printer.enterLateNumber(p);
				emptyEntry = false;
				entryButton.setText(RegisterButton.DEFAULT_TEXT);
				addToStartOfTextArea(entry);
				temp = Formater.formatColumns(entry, lateTime);
			} else {
				printInterval(entry, p);
				updateTextArea(temp);
			}
		}
		if (!emptyEntry) {
			String[] send = temp.split(Formater.COLUMN_SEPARATOR);
			int lengthOfArray;
			List<Interval> intervals = p.getIntervals();
			for(Interval interval : intervals) {
				for(int nbr : interval.getNumbers()) {
					String line = nbr + "; " + t.toString();
					send = line.split(Formater.COLUMN_SEPARATOR);
					System.out.println(line);
					for (int i = 0; i < send.length; i++) {
						lengthOfArray = send[i].getBytes().length;
						byte[] b = new byte[lengthOfArray + 1];
						// b[0] = Byte.parseByte(Integer.toString(lengthOfArray));
						b[0] = (byte) lengthOfArray;
						for (int k = 1; k <= send[i].getBytes().length; k++) {
							b[k] = send[i].getBytes()[k - 1];
						}
						out.sendMessage(b);	
					}
				}
			}
		}
		undoButton.setEnabled(emptyEntry);

		// Finishing updates
		textField.requestFocus();
		textArea.invalidate();
		repaint();
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

	public Font getSmallFont() {
		return smallFont;
	}

	public Font getBigFont() {
		return bigFont;
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

	private void printIntervals(IntervalParser p, Time time) {
		for (Interval c : p.getIntervals())
			for (int i : c.getNumbers())
				printer.writeLines(false, Formater.formatColumns(i, time));
	}

	/**
	 * Parses the string as an interval and prints one line for every number
	 * contained.
	 * 
	 * @param entry
	 */
	private void printInterval(String entry, IntervalParser p) {
		Time t = Time.fromCurrentTime();
		printIntervals(p, t);
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
}
