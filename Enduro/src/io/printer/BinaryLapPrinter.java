package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.Lap;

public class BinaryLapPrinter extends Printer {

	protected int maxLaps;

	protected void appendFirstRow(FileWriter fileWriter) throws IOException {
		fileWriter
				.append(Formater.formatColumns(Formater.START_NR,
						Formater.NAME, Formater.TOTAL_TIME,
						Formater.BINARY_LAP_NUMBER));
		fileWriter.append(Formater.COLUMN_SEPARATOR);
	}

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();

		appendCompetitorInfo(sb, c);

		appendBinaryLaps(sb, c);

		sb.setLength(sb.length() - 2);
		return sb.toString();
	}

	/**
	 * Appends the total time of each binary lap for the competitor, as well as
	 * each start and finish time for each binary lap.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitor who's times to append
	 */
	protected void appendBinaryLaps(StringBuilder sb, Competitor c) {
		List<Lap> binLaps = c.getBinaryLaps();
		sb.append(binLaps.size() + Formater.COLUMN_SEPARATOR);
		for (Lap l : binLaps) {
			sb.append(l.getTotal() + Formater.COLUMN_SEPARATOR);
		}
		for (Lap l : binLaps) {
			sb.append(l.getStart() + Formater.COLUMN_SEPARATOR);
			sb.append(l.getEnd() + Formater.COLUMN_SEPARATOR);
		}
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
	protected void appendCompetitorInfo(StringBuilder sb, Competitor c) {
		sb.append(Formater.formatColumns(c.getIndex(), c.getName(),
				c.getTotalTime(c.getBinaryLaps()))
				+ Formater.COLUMN_SEPARATOR);
	}

	@Override
	protected void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException {
		maxLaps = getMaxLaps(competitors);

		appendFirstRow(fileWriter);

		for (int i = 1; i < maxLaps + 1; i++) {
			fileWriter.append(Formater.BINARY_LAP_TIME + i
					+ Formater.COLUMN_SEPARATOR);
		}
		for (int i = 0; i < maxLaps - 1; i++) {
			fileWriter.append(Formater.START_TIME + (i + 1)
					+ Formater.COLUMN_SEPARATOR);
			fileWriter.append(Formater.FINISH_TIME + (i + 1)
					+ Formater.COLUMN_SEPARATOR);
		}
		fileWriter.append(Formater.START_TIME + maxLaps
				+ Formater.COLUMN_SEPARATOR);
		fileWriter.append(Formater.FINISH_TIME + maxLaps + Formater.LINE_BREAK);

	}

	/**
	 * Returns the maximum number of laps ran by any competitor.
	 * 
	 * @param competitors
	 *            list of competitors
	 * @return the maximum number of laps ran by any competitor
	 */
	@Override
	protected int getMaxLaps(List<Competitor> competitors) {
		int a = competitors.get(0).getNumberOfBinaryLaps();
		for (Competitor c : competitors) {
			if (c.getNumberOfLaps() > a) {
				a = c.getNumberOfBinaryLaps();
			}
		}
		return a;

	}

}
