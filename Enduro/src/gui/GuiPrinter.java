package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import sort.Formater;

public class GuiPrinter {

	private File file;

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

	/**
	 * @return false if the last line starts with column separator
	 * @see #Formater
	 */
	public boolean lastRowHasNr() {
		try {
			ArrayList<String> lines = getLines();
			return !lines.get(lines.size() - 1).startsWith(
					Formater.COLUMN_SEPARATOR);
		} catch (Exception e) {
			return true;
		}
	}

	public String getLastRow() {
		ArrayList<String> lines;
		try {
			lines = getLines();
			return lines.get(lines.size() - 1);
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * Adds the string comNr to the beginning of the last line.
	 * 
	 * @param comNr
	 *            The string to add.
	 */
	public void enterLateNumber(String comNr) {
		try {
			ArrayList<String> lines = getLines();
			lines.set(lines.size() - 1, comNr + lines.get(lines.size() - 1));
			writeLines(lines);
		} catch (FileNotFoundException e1) {
		} catch (IOException e) {
		}

	}

	private void writeLines(ArrayList<String> lines) {
		String ret = "";
		for (String s : lines) {
			ret = ret + s + System.getProperty("line.separator");
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(ret);
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

	private ArrayList<String> getLines() throws FileNotFoundException,
			IOException {
		FileInputStream in = new FileInputStream(file);

		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		ArrayList<String> lines = new ArrayList<String>();
		String tmp;
		while ((tmp = br.readLine()) != null) {
			if (!tmp.isEmpty())
				lines.add(tmp);
		}
		br.close();
		in.close();
		return lines;
	}
}
