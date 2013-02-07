package sort;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.NullTime;
import members.Time;

public class LapCompetitorPrinter extends Printer {

	private int maxLaps;

	/**
	 * @return Total time elapsed, or Null time string
	 */
	@Override
	protected Time totalTime(Competitor c) {
		return (c.getStartTimes().isEmpty() || c.getFinishTimes().isEmpty()) ? new NullTime()
				: c.getStartTimes()
						.get(0)
						.difference(
								c.getFinishTimes().get(
										c.getFinishTimes().size() - 1));
	}

	@Override
	public String row(Competitor c) {
		StringBuilder sb = new StringBuilder();
		sb.append(Formater.formatColumns(c.getIndex(), c.getName(),
				c.getNumberOfLaps(), totalTime(c))
				+ "; ");
		int i = 0;
		while (i < c.getLaps().size()) {
			sb.append(Formater.formatColumns(c.getLaps().get(i).getTotal())
					+ "; ");
			i++;
		}
		while (i < maxLaps) {
			sb.append("; ");
			i++;
		}
		sb.append(c.getStartTimes().get(0) + "; ");
		i = 0;
		while (i < c.getLaps().size() - 1) {
			sb.append(Formater.formatColumns(c.getLaps().get(i).getEnd())
					+ "; ");
			i++;
		}
		while (i < maxLaps - 1) {
			sb.append("; ");
			i++;
		}
		sb.append(Formater.formatColumns(c.getFinishTimes().get(
				c.getFinishTimes().size() - 1)));
		return sb.toString();
	}

	@Override
	protected void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException {
		maxLaps = getMaxLaps(competitors);
		fileWriter.append("StartNr; Namn; #Varv; TotalTid; ");
		for (int i = 1; i < maxLaps + 1; i++) {
			fileWriter.append("Varv" + i + "; ");
		}
		fileWriter.append("StartTid; ");
		for (int i = 1; i < maxLaps; i++) {
			fileWriter.append("Varvning" + i + "; ");
		}
		fileWriter.append("Måltid\n");
	}

	private int getMaxLaps(List<Competitor> competitors) {
		int a = competitors.get(0).getNumberOfLaps();
		for (Competitor c : competitors) {
			if (c.getNumberOfLaps() > a) {
				a = c.getNumberOfLaps();
			}
		}
		return a;
	}
}
