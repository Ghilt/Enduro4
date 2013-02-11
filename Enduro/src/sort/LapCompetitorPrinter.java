package sort;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;

public class LapCompetitorPrinter extends Printer {

	protected int maxLaps;

	private final String FIRST_ROW = "StartNr; Namn; #Varv; Totaltid; ";

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
				c.getNumberOfLaps(), c.getTotalTime())
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
		for (; i < c.getLaps().size(); i++) {
			sb.append(Formater.formatColumns(c.getLaps().get(i).getTotal())
					+ Formater.COLUMN_SEPARATOR);
		}
		/*
		 * Must add additional ';' if the competitors nbr of laps is less than
		 * the maximum nbr of laps ran by any competitor
		 */
		for (; i < maxLaps; i++) {
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
			for (i = 0; i < c.getLaps().size() - 1; i++) {
				sb.append(Formater.formatColumns(c.getLaps().get(i).getEnd())
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
	}
	
	protected void appendFirstRow(FileWriter fileWriter) throws IOException {
		fileWriter.append(FIRST_ROW);
	}

	@Override
	protected void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException {
		maxLaps = getMaxLaps(competitors);
		
		appendFirstRow(fileWriter);

		for (int i = 1; i < maxLaps + 1; i++) {
			fileWriter
					.append(Formater.LAP_TIME + i + Formater.COLUMN_SEPARATOR);
		}
		fileWriter.append(Formater.START_TIME + Formater.COLUMN_SEPARATOR);
		for (int i = 1; i < maxLaps; i++) {
			fileWriter.append(Formater.LAP_FINISH_TIME + i
					+ Formater.COLUMN_SEPARATOR);
		}
		fileWriter.append(Formater.FINISH_TIME + "\n");
	}
}
