package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class CvsReader {

	private File file;
	private final static String DELIMITER = ";";

	/**
	 * Creates a new reader of the chosen file.
	 * 
	 * @param filepath
	 *            The path to read from. For instance: "src/test/emptyfile.txt"
	 */
	public CvsReader(String filepath) {
		file = new File(filepath);
	}

	/**
	 * Reads and returns a String matrix of rows and columns from the read file.
	 * Rows is the outer Arraylist.
	 * 
	 * @return A NxM matrix of strings.
	 * @throws FileNotFoundException
	 *             If the file is not found.
	 */
	public ArrayList<ArrayList<String>> readAll() throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

		while (scan.hasNextLine()) {
			ArrayList<String> row = new ArrayList<String>();
			String line = scan.nextLine();
			if (line.length() > 0) {
				String[] cols = line.split(DELIMITER);
				for (int i = 0; i < cols.length; i++) {
					cols[i] = cols[i].trim();
					row.add(cols[i]);
				}
				ret.add(row);
			}
		}
		return ret;
	}

}
