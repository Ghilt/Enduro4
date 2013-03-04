package gui.tools;

import io.Formater;
import io.reader.IntervalParser;
import io.reader.IntervalParser.Interval;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import client.Output;

public class GuiPrinter {

	private File file;
	
	private Output out;

	/**
	 * Starts a new GuiPrinter surrounding target file.
	 * 
	 * @param filelink
	 *            The location of the file, including name, to write to.
	 * @param out 
	 */
	public GuiPrinter(String filelink, Output out) {
		super();
		this.file = new File(filelink);
		this.out = out;

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

	/**
	 * @return The entire last row of the file.
	 */
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
	 * Removes the last line in the file.
	 */
	public void clearLastLine() {
		try {
			ArrayList<String> lines = getLines();
			lines.remove(lines.size() - 1);
			writeLines(true, lines.toArray());
		} catch (FileNotFoundException e1) {
		} catch (IOException e) {
		}
	}

	/**
	 * Adds all Intervals in IntervalParser with last line's time.
	 * 
	 * @param p
	 *            IntervalParser
	 */
	public void enterLateNumber(IntervalParser p) {
		try {
			ArrayList<String> lines = getLines();
			String time = lines.get(lines.size() - 1);
			lines.remove(lines.size() - 1);

			for (Interval c : p.getIntervals())
				for (int i : c.getNumbers())
					lines.add(i + time);

			writeLines(true, lines.toArray());
		} catch (FileNotFoundException e1) {
		} catch (IOException e) {
		}
	}

	/**
	 * Write objects to file
	 * 
	 * @param overwrite
	 *            If file should be overwritten
	 * @param lines
	 *            Objects
	 */
	public void writeLines(boolean overwrite, Object... lines) {
		String ret = "";
		for (Object s : lines)
			ret = ret + s + System.getProperty("line.separator");

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file, !overwrite));
			writer.write(ret);
//			send(ret);
		} catch (IOException e) {
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}

//	private void send(String ret) {
//		String[] send = ret.split(Formater.COLUMN_SEPARATOR);
//		int lengthOfArray;
//		if(!send[0].isEmpty()){
//			for (int i = 0; i < send.length; i++) {
//				lengthOfArray = send[i].getBytes().length;
//				byte[] b = new byte[lengthOfArray + 1];
//				// b[0] = Byte.parseByte(Integer.toString(lengthOfArray));
//				b[0] = (byte) lengthOfArray;
//				for (int k = 1; k <= send[i].getBytes().length; k++) {
//					b[k] = send[i].getBytes()[k - 1];
//				}
//				out.sendMessage(b);
//			}
//		}
//	}

	/**
	 * Returns all lines in a document.
	 * 
	 * @return String ArrayList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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
