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
public class SortStdCompetitorPrinter extends StdCompetitorPrinter {

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
		
		

//		failed = appendMultipleTimes(sb, MULTIPLE_STARTS, c.getStartTimes(),
//				failed);
//
//		failed = appendMultipleTimes(sb, MULTIPLE_ENDS, c.getFinishTimes(),
//				failed);

		failed = appendImpossibleTotalTime(sb, c, failed);

		return sb.toString();
	}
	
	private void appendPlacement(StringBuilder sb, Competitor c) {
		if(c.getPlac() == 0) {
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
	protected void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException {
		fileWriter.append(Formater.formatColumns("Plac",Formater.START_NR,
				Formater.NAME, Formater.TOTAL_TIME));
		fileWriter.append("\n");
	}
}
