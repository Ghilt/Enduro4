package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.Lap;

public class SortBinaryLapPrinter extends BinaryLapPrinter {

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();

		appendPlacement(sb, c);
		
		addString(sb, c.getIndex() + "");
		addString(sb, c.getName() + "");
		addString(sb, c.getTotalTime(c.getBinaryLaps()) + "");

		List<Lap> etapper = c.getBinaryLaps();

		addString(sb, etapper.size() + "");

		appendEtapper(sb, etapper);

		sb.setLength(sb.length() - Formater.COLUMN_SEPARATOR.length());
		return sb.toString();
	}

	/**
	 * Append the competitors start nbr, name, nbr of laps and total time to the
	 * stringbuilder.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitor which info to append
	 */
	private void appendPlacement(StringBuilder sb, Competitor c) {
		if (c.getPlac() == 0) {
			sb.append(Formater.COLUMN_SEPARATOR);
		} else {
			sb.append(c.getPlac() + Formater.COLUMN_SEPARATOR);
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

		int i = 1;
		for (; i < maxLaps; i++) {
			fileWriter.append(Formater.BINARY_LAP_TIME + i
					+ Formater.COLUMN_SEPARATOR);
		}
		fileWriter.append(Formater.BINARY_LAP_TIME + i);
		fileWriter.append("\n");

	}

	protected void appendFirstRow(FileWriter fileWriter) throws IOException {
		fileWriter.append(Formater.formatColumns(Formater.PLACEMENT, Formater.START_NR, Formater.NAME, Formater.TOTAL_TIME, Formater.BINARY_LAP_NUMBER));
		fileWriter.append(Formater.COLUMN_SEPARATOR);
	}
}
