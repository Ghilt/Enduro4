package gui.register;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RegisterButton extends JButton implements ActionListener {

	public static final String DEFAULT_TEXT = "REGISTER";
	public static final String REQUEST_ENTRY = "LÄS IN NR";
	private Gui frame;

	/**
	 * A button that triggers register() within a target frame.
	 * 
	 * @param frame
	 *            The frame to call.
	 * 
	 * @param dim
	 *            The size of the button
	 */
	public RegisterButton(Gui frame, Dimension dim) {
		setPreferredSize(dim);
		setFont(frame.getSmallFont());
		this.frame = frame;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.register();
	}

}
