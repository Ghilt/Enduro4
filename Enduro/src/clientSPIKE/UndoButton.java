package clientSPIKE;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class UndoButton extends JButton implements ActionListener {

	public static final String DEFAULT_TEXT = "ÅNGRA";
	private Gui frame;

	/**
	 * A button that triggers undo() within a target frame.
	 * 
	 * @param frame
	 *            The frame to call.
	 * 
	 * @param dim
	 *            The size of the button
	 */
	public UndoButton(Gui frame, Dimension dim) {
		setPreferredSize(dim);
		setFont(frame.getSmallFont());
		this.frame = frame;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
//		frame.undo();
	}

}
