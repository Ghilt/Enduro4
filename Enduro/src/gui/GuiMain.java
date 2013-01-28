package gui;

public class GuiMain {

	/**
	 * @param args
	 *            Argo = Output file
	 */
	public static void main(String[] args) {
		String str = "default_race_file.txt";
		// if(args.length>0){
		// str = args[0];
		// }
		new Gui(str);
	}

}
