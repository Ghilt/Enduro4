package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.Lap;

public class BinaryLapPrinter extends Printer {

	private final static String FIRST_ROW = "StartNr; Namn; Totaltid; #Etapper; ";

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();

		addString(sb, c.getIndex() + "");
		addString(sb, c.getName() + "");
		addString(sb, c.getTotalTime(c.getBinaryLaps()) + "");

		List<Lap> etapper = c.getBinaryLaps();

		addString(sb, etapper.size() + "");

		appendEtapper(sb, etapper);
		appendTimes(sb, etapper);

		sb.setLength(sb.length() - Formater.COLUMN_SEPARATOR.length());
		return sb.toString();
	}

	private void appendTimes(StringBuilder sb, List<Lap> etapper) {
		for (Lap l : etapper) {
			addString(sb, l.getStart() + "");
			addString(sb, l.getEnd() + "");
		}
	}

	private void appendEtapper(StringBuilder sb, List<Lap> etapper) {
		for (Lap l : etapper) {
			addString(sb, l.getTotal() + "");
		}
	}

	@Override
	protected void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException {

		appendFirstRow(fileWriter);

		int maxLaps = 0;
		for (Competitor c : competitors) {
			int temp = c.getBinaryLaps().size();
			if (temp > maxLaps) {
				maxLaps = temp;
			}
		}

		for (int i = 1; i < maxLaps + 1; i++) {
			fileWriter.append(Formater.BINARY_LAP_TIME + i
					+ Formater.COLUMN_SEPARATOR);
		}
		int i = 1;
		for (i = 1; i < maxLaps; i++) {
			fileWriter.append(Formater.START_TIME + i
					+ Formater.COLUMN_SEPARATOR);
			fileWriter.append(Formater.FINISH_TIME + i
					+ Formater.COLUMN_SEPARATOR);
		}
		fileWriter.append(Formater.START_TIME + i + Formater.COLUMN_SEPARATOR);
		fileWriter.append(Formater.FINISH_TIME + i);
		fileWriter.append("\n");

	}

	private void appendFirstRow(FileWriter fileWriter) throws IOException {
		fileWriter.append(FIRST_ROW);
	}

	protected void addString(StringBuilder sb, String s) {
		sb.append(s);
		sb.append(Formater.COLUMN_SEPARATOR);
	}

}
