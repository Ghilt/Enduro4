package gui;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EntryField extends JTextField {

	/**
	 *  A field that only accepts valid characters as entries.
	 */
	public EntryField() {
		super();
	}

	@Override
	public void processKeyEvent(KeyEvent ev) {

		char c = ev.getKeyChar();
		try {
			// Ignore all non-printable characters. Just check the printable
			// ones.
			// If it is not printable, a number, a comma, or a dash, ignore it.
			if (c != ',' && c != '-') {
				if ((c > 31 && c < 127)) {
					Integer.parseInt(c + "");
				}
			}
			super.processKeyEvent(ev);
		} catch (NumberFormatException nfe) {
			// Do nothing. Character inputted is not a number, so ignore it.
		}

	}

}
