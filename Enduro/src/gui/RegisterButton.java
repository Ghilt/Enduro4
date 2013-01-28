package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RegisterButton extends JButton implements ActionListener {

	private Gui frame;

	public RegisterButton(Gui frame, Dimension dim) {
		super("REGISTER");
		setPreferredSize(dim);
		this.frame = frame;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.register();
	}

}
