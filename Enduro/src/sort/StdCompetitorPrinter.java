package sort;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.NullTime;
import members.Time;

/**
 * @author Andrée & Victor
 * 
 *         Prints according to first iteration race.
 */
public class StdCompetitorPrinter extends Printer {

	/**
	 * @return Total time elapsed, or Null time string
	 */
	protected Time totalTime(Competitor c) {
		return (c.getStartTimes().isEmpty() || c.getFinishTimes().isEmpty()) ? new NullTime()
				: c.getStartTimes().get(0)
						.difference(c.getFinishTimes().get(0));
	}

	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();

		sb.append(Formater.formatColumns(c.getIndex(), c.getName(),
				totalTime(c), (c.getStartTimes().isEmpty() ? NO_START : c
						.getStartTimes().get(0).toString()), (c
						.getFinishTimes().isEmpty() ? NO_END : c
						.getFinishTimes().get(0).toString())));
		boolean fail = false;
		if (c.getStartTimes().size() > 1) {
			sb.append("; ");
			fail = true;
			sb.append(Formater.formatList(MULTIPLE_STARTS, c.getStartTimes()
					.toArray()));
		}

		if (c.getFinishTimes().size() > 1) {
			if (!fail) {
				sb.append(";");
				fail = true;
			}

			sb.append(" ");
			sb.append(Formater.formatList(MULTIPLE_ENDS, c.getFinishTimes()
					.toArray()));
		}

		if (totalTime(c).compareTo(MINIMUM_TOTAL_TIME) <= 0) {
			if (!fail)
				sb.append(";");

			sb.append(" ");
			sb.append(IMPOSSIBLE_TOTAL_TIME);
		}

		return sb.toString();
	}

	protected void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException {
		fileWriter.append(Formater.formatColumns(Formater.START_NR,
				Formater.NAME, Formater.TOTAL_TIME, Formater.START_TIME,
				Formater.FINISH_TIME));
		fileWriter.append("\n");
	}
}
