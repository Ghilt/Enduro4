package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import members.Competitor;
import members.Time;

/**
 * @author Karl & Jacob
 * 
 *         Prints out a sorted standardlist.
 */
public class SortStdPrinter extends StdPrinter {

	/**
	 * This method is needed because this printer does not consider laps and
	 * therefore the method 'getTotalTime()' in competitor returns an incorrect
	 * time.
	 * 
	 * @return Total time elapsed, or Null time string
	 */

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();
		appendPlacement(sb, c);

		appendCompetitorInfo(sb, c);

		boolean failed = false;

		failed = appendImpossibleTotalTime(sb, c, failed);

		return sb.toString();
	}

	private void appendPlacement(StringBuilder sb, Competitor c) {
		if (c.getPlac() == 0) {
			sb.append(Formater.COLUMN_SEPARATOR);
		} else {
			sb.append(c.getPlac() + Formater.COLUMN_SEPARATOR);
		}
	}

	/**
	 * Append the competitors start nbr, name, total time and if any missing
	 * start/finish times to the stringbuilder.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitor which info to append
	 */
	@Override
	protected void appendCompetitorInfo(StringBuilder sb, Competitor c) {
		Time totalTime = totalTime(c);

		sb.append(Formater.formatColumns(c.getIndex(), c.getName(), totalTime));
	}

	@Override
	protected void appendRows(StringBuilder sb,
			List<Competitor> competitors) throws IOException {
		
	}
	
	@Override
	protected void appendFirstRow(StringBuilder sb) throws IOException {
		sb.append(Formater.formatColumns(Formater.PLACEMENT, Formater.START_NR,
				Formater.NAME, Formater.TOTAL_TIME));
		sb.append(Formater.LINE_BREAK);
	}
}
