package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;

public class SortLapPrinter extends LapPrinter {

	protected void appendFirstRow(FileWriter fileWriter) throws IOException {
		fileWriter.append(Formater.formatColumns(Formater.PLACEMENT,
				Formater.START_NR, Formater.NAME, Formater.LAP_NUMBER,
				Formater.TOTAL_TIME));
		fileWriter.append(Formater.COLUMN_SEPARATOR);
	}

	@Override
	public String row(Competitor c) {

		StringBuilder sb = new StringBuilder();

		appendPlacement(sb, c);

		appendCompetitorInfo(sb, c);

		appendLapTimes(sb, c);

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
		maxLaps = getMaxLaps(competitors);

		appendFirstRow(fileWriter);

		for (int i = 1; i < maxLaps; i++) {
			fileWriter
					.append(Formater.LAP_TIME + i + Formater.COLUMN_SEPARATOR);
		}
		fileWriter.append(Formater.LAP_TIME + maxLaps);
		fileWriter.append(Formater.LINE_BREAK);

	}

	/**
	 * Append the competitors lap finish times ("varvning") to the stringbuilder
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitor who's lap finish times to append.
	 */
	@Override
	protected void appendLapTimes(StringBuilder sb, Competitor c) {
		int i = 0;
		for (; i < c.getLaps().size() - 1; i++) {
			sb.append(Formater.formatColumns(c.getLaps().get(i).getTotal())
					+ Formater.COLUMN_SEPARATOR);
		}
		sb.append(Formater.formatColumns(c.getLaps().get(i).getTotal()));
		/*
		 * Must add additional ';' if the competitors nbr of laps is less than
		 * the maximum nbr of laps ran by any competitor
		 */
		for (; i < maxLaps - 1; i++) {
			sb.append(Formater.COLUMN_SEPARATOR);
		}
	}

}
