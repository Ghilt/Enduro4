package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GuiPrinter {

	/**
	 * Starts a new GuiPrinter surrounding target file.
	 * 
	 * @param filelink
	 *            The location of the file, including name, to write to.
	 */
	public GuiPrinter(String filelink) {
		super();
		this.file = new File(filelink);
		
	}

	private File file;

	/**
	 * Appends the string to the end of the file
	 * 
	 * @param line
	 *            The data to print
	 */
	public void writeLine(String line) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}
}
