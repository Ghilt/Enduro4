package io.printer;

import io.Formater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import members.Competitor;
import members.Lap;
import members.NullTime;
import members.Station;

public class BinaryLapPrinter extends Printer {

	protected int maxLaps;

	public BinaryLapPrinter(int maxLaps) {
		super();
		this.maxLaps = maxLaps;
	}

	public BinaryLapPrinter() {
		this(0);
	}

	protected void appendFirstRow(StringBuilder sb) throws IOException {
		sb.append(Formater.formatColumns(Formater.START_NR, Formater.NAME,
				Formater.TOTAL_TIME, Formater.BINARY_LAP_NUMBER));
		sb.append(Formater.COLUMN_SEPARATOR);
	}

	@Override
	protected void appendRows(StringBuilder sb, List<Competitor> competitors)
			throws IOException {
		maxLaps = getMaxLaps(competitors);

		for (int i = 1; i < maxLaps + 1; i++) {
			sb.append(Formater.BINARY_LAP_TIME + i + Formater.COLUMN_SEPARATOR);
		}
		for (int i = 1; i < maxLaps; i++) {
			sb.append(Formater.START_TIME + i + Formater.COLUMN_SEPARATOR);
			sb.append(Formater.FINISH_TIME + i + Formater.COLUMN_SEPARATOR);
		}
		sb.append(Formater.START_TIME + maxLaps + Formater.COLUMN_SEPARATOR);
		sb.append(Formater.FINISH_TIME + maxLaps + Formater.LINE_BREAK);

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
				c.getTotalTime(new ArrayList<Lap>(c.getBinaryLaps().values())))
				+ Formater.COLUMN_SEPARATOR);
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

		Map<Integer, Lap> binLaps = c.getBinaryLaps();
		int nrFullLaps = 0;
		for (Lap l : binLaps.values()) {
			if (!l.getEnd().isNull() && !l.getStart().isNull()) {
				nrFullLaps++;
			}
		}
		sb.append(nrFullLaps + Formater.COLUMN_SEPARATOR);

		for (int i = 1; i < maxLaps + 1; i++) {
			Lap currentLap = binLaps.get(i);
			if (currentLap != null) {
				sb.append(currentLap.getTotal() + Formater.COLUMN_SEPARATOR);
			} else {
				sb.append(Formater.COLUMN_SEPARATOR);
			}
		}
		for (int i = 1; i < maxLaps + 1; i++) {
			Lap currentLap = binLaps.get(i);
			if (currentLap != null) {
				if (currentLap.getStart().isNull()) {
					sb.append(Printer.NO_START + Formater.COLUMN_SEPARATOR);
				} else {
					sb.append(currentLap.getStart() + Formater.COLUMN_SEPARATOR);
				}
				if (currentLap.getEnd().isNull()) {
					sb.append(Printer.NO_END + Formater.COLUMN_SEPARATOR);
				} else {
					sb.append(currentLap.getEnd() + Formater.COLUMN_SEPARATOR);
				}
			} else {
				sb.append(Formater.COLUMN_SEPARATOR);
				sb.append(Formater.COLUMN_SEPARATOR);
			}
		}
		List<Station>[] extras = c.getExtraLapsBinary();
		List<Station> extraStarts = extras[0];
		List<Station> extraFinishes = extras[1];
		if (extraStarts.size() > 0) {
			sb.append(Printer.MULTIPLE_STARTS_CLEAN + " ");
			for (Station s : extraStarts) {
				sb.append(Formater.BINARY_LAP_TIME + s.nr + " " + s.time
						+ Formater.COLUMN_SEPARATOR);
			}
		}
		if (extraFinishes.size() > 0) {
			sb.append(Printer.MULTIPLE_ENDS_CLEAN + " ");
			for (Station s : extraFinishes) {
				sb.append(Formater.BINARY_LAP_TIME + s.nr + " " + s.time
						+ Formater.COLUMN_SEPARATOR);
			}
		}

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
			if (c.getNumberOfBinaryLaps() > a) {
				a = c.getNumberOfBinaryLaps();
			}
		}
		return a;

	}

}
