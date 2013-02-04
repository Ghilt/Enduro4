package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.NullTime;
import members.Time;

public class StdCompetitorPrinter implements CompetitorPrinter {
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";
	public static final String MULTIPLE_STARTS = "Flera starttider?";
	public static final String MULTIPLE_ENDS = "Flera måltider?";
	public static final String IMPOSSIBLE_TOTAL_TIME = "Omöjlig Totaltid?";
	public static final Time MINIMUM_TOTAL_TIME = new Time("00.15.00");

	/**
	 * @return Total time elapsed, or Null time string
	 */
	private Time totalTime(Competitor c) {
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

	/**
	 * Prints the result in the list with competitors to the output file.
	 * 
	 * @param competitors
	 *            list of competitors
	 * @param filepath
	 *            the file to write to
	 */
	public void printResults(List<Competitor> competitors, String filepath) {
		try {
			File outputFile = new File(filepath);
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.append(Formater.formatColumns(Formater.START_NR,
					Formater.NAME, Formater.TOTAL_TIME, Formater.START_TIME,
					Formater.FINISH_TIME));
			fileWriter.append("\n");
			for (Competitor comp : competitors) {
				fileWriter.append("" + row(comp) + "\n");
			}
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
