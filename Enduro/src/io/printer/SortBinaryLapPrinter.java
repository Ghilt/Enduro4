package io.printer;

import io.Formater;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.Lap;

public class SortBinaryLapPrinter extends BinaryLapPrinter {

	private int maxBinLaps;

	/**
	 * First row = "Plac; StartNr; Namn; Totaltid; #Etapper;"
	 */
	@Override
	protected void appendFirstRow(StringBuilder sb) throws IOException {
		sb.append(Formater.formatColumns(Formater.PLACEMENT,
				Formater.START_NR, Formater.NAME, Formater.TOTAL_TIME,
				Formater.BINARY_LAP_NUMBER));
		sb.append(Formater.COLUMN_SEPARATOR);
	}

	@Override
	public String row(Competitor c) {

		StringBuilder sb = new StringBuilder();

		appendPlacement(sb, c);

		appendCompetitorInfo(sb, c);

		appendNumberOfBinLaps(sb, c);

		appendBinaryLaps(sb, c);

		return sb.toString();
	}

	/**
	 * Appends the competitors binary lap times.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitor which info to append
	 */
	protected void appendBinaryLaps(StringBuilder sb, Competitor c) {
		List<Lap> binLaps = c.getBinaryLaps();
		for (int i = 0; i < binLaps.size() - 1; i++) {
			sb.append(binLaps.get(i).getTotal() + Formater.COLUMN_SEPARATOR);
		}
		sb.append(binLaps.get(binLaps.size() - 1).getTotal());
	}

	/**
	 * Appends the number of binary laps the competitors has completed.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitor which info to append
	 */
	private void appendNumberOfBinLaps(StringBuilder sb, Competitor c) {
		sb.append(c.getNumberOfBinaryLaps() + Formater.COLUMN_SEPARATOR);
	}

	/**
	 * Append the competitors start nbr, name, nbr of binary laps and total time
	 * to the stringbuilder.
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

	/**
	 * Append first row and then competitors info for each competitor.
	 */
	@Override
	protected void appendRows(StringBuilder sb, List<Competitor> competitors)
			throws IOException {
		maxBinLaps = getMaxLaps(competitors);

		for (int i = 1; i < maxBinLaps; i++) {
			sb.append(Formater.BINARY_LAP_TIME + i + Formater.COLUMN_SEPARATOR);
		}
		sb.append(Formater.BINARY_LAP_TIME + maxBinLaps);

		sb.append(Formater.LINE_BREAK);

	}

	/**
	 * Loops the sorted list of competitors and add a placement to each.
	 */
	protected void setPlacements(List<Competitor> competitors) {
		maxBinLaps = getMaxLaps(competitors);
		for (int i = 0; i < competitors.size(); i++) {
			if (competitors.get(i).getNumberOfBinaryLaps() == maxBinLaps) {
				competitors.get(i).setPlac(i + 1);
			}
		}

	}

}
