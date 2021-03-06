package io.printer;

import io.Formater;

import java.io.IOException;
import java.util.List;

import members.Competitor;

public class LapPrinter extends Printer {

	protected int maxLaps;
	
	@Override
	protected void appendFirstRow(StringBuilder sb) throws IOException {
		sb.append(Formater.formatColumns(Formater.START_NR, Formater.NAME,
				Formater.LAP_NUMBER, Formater.TOTAL_TIME));
		sb.append(Formater.COLUMN_SEPARATOR);
	}

	@Override
	protected void appendRows(StringBuilder sb, List<Competitor> competitors)
			throws IOException {
		maxLaps = getMaxLaps(competitors);

		for (int i = 1; i < maxLaps + 1; i++) {
			sb.append(Formater.LAP_TIME + i + Formater.COLUMN_SEPARATOR);
		}
		sb.append(Formater.START_TIME + Formater.COLUMN_SEPARATOR);
		for (int i = 1; i < maxLaps; i++) {
			sb.append(Formater.LAP_FINISH_TIME + i + Formater.COLUMN_SEPARATOR);
		}
		sb.append(Formater.FINISH_TIME + Formater.LINE_BREAK);
	}
	
	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();

		appendCompetitorInfo(sb, c);

		appendLapTimes(sb, c);

		appendStartTimes(sb, c);

		appendLapFinishTimes(sb, c);

		appendFinishTime(sb, c);
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
				c.getNumberOfLaps(), c.getTotalTime(c.getLaps()))
				+ Formater.COLUMN_SEPARATOR);
	}

	/**
	 * Append the competitors lap times to the stringbuilder.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitors who's lap times to append
	 */
	protected void appendLapTimes(StringBuilder sb, Competitor c) {
		int i = 0;

		if (c.startMissing())
			sb.append(Formater.COLUMN_SEPARATOR);

		for (; i < c.getLaps().size(); i++) {
			sb.append(Formater.formatColumns(c.getLaps().get(i).getTotal())
					+ Formater.COLUMN_SEPARATOR);
		}
		/*
		 * Must add additional ';' if the competitors nbr of laps is less than
		 * the maximum nbr of laps ran by any competitor
		 */
		int diff = c.startMissing() ? 1 : 0;
		for (; i < maxLaps - diff; i++) {
			sb.append(Formater.COLUMN_SEPARATOR);
		}
	}

	/**
	 * Append the competitors start times to the stringbuilder.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitors who's lap times to append
	 */
	private void appendStartTimes(StringBuilder sb, Competitor c) {
		if (c.getStartTimes().isEmpty()) {
			sb.append(NO_START + "; ");
		} else {
			sb.append(c.getStartTimes().get(0) + Formater.COLUMN_SEPARATOR);
		}

	}

	/**
	 * Append the competitors lap finish times ("varvning") to the stringbuilder
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitor who's lap finish times to append.
	 */

	protected void appendLapFinishTimes(StringBuilder sb, Competitor c) {

		if (c.getFinishTimes().isEmpty()) {
			sb.append(NO_END);
		} else {
			int i;
			/*
			 * When finishTimes size equals laps size all except the last one
			 * (goal) are printed.
			 */
			int diff = c.startMissing() ? 1 : 0;
			for (i = 0; i < c.getLaps().size() + diff - 1; i++) {
				sb.append(Formater.formatColumns(c.getFinishTimes().get(i))
						+ Formater.COLUMN_SEPARATOR);
			}

			/*
			 * Must add additional ';' if the competitors nbr of laps is less
			 * than the maximum nbr of laps ran by any competitor
			 */
			for (; i < maxLaps - 1; i++) {
				sb.append(Formater.COLUMN_SEPARATOR);
			}
		}
	}

	/**
	 * Append the competitors finish time to the stringbuilder.
	 * 
	 * @param sb
	 *            the stringbuilder to append to
	 * @param c
	 *            the competitors who's lap times to append
	 */
	private void appendFinishTime(StringBuilder sb, Competitor c) {
		if (!c.getFinishTimes().isEmpty()) {
			sb.append(Formater.formatColumns(c.getFinishTimes().get(
					c.getFinishTimes().size() - 1)));
		}
		if (c.getStartTimes().size() > 1) {
			sb.append("; " + MULTIPLE_STARTS + " ");
			for (int i = 1; i < c.getStartTimes().size(); i++) {
				sb.append(c.getStartTimes().get(i));
			}
		}
		appendCheckImpossibleLapTime(sb, c);

	}

	private void appendCheckImpossibleLapTime(StringBuilder sb, Competitor c) {
		for (int i = 0; i < c.getLaps().size(); i++) {
			if (c.getLaps().get(i).getTotal().compareTo(MINIMUM_TOTAL_TIME) <= 0) {
				sb.append(Formater.formatColumns("; " + IMPOSSIBLE_LAP_TIME));
				break;
			}
		}

	}

}
