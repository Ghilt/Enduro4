package gui;

public class GuiMain {

	/**
	 * Launches the Gui for entering race times.
	 * 
	 * @param args
	 *            Arg0 = Output file
	 */
	public static void main(String[] args) {
		String str = "default_race_file.txt";
		new Gui(str);
	}

}
