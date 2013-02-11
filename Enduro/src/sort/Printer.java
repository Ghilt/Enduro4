package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.Time;

/**
 * @author Andrée & Victor
 * 
 *         For printing competitors.
 */
public abstract class Printer implements CompetitorPrinter {
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";
	public static final String MULTIPLE_STARTS = "Flera starttider?";
	public static final String MULTIPLE_ENDS = "Flera måltider?";
	public static final String IMPOSSIBLE_TOTAL_TIME = "Omöjlig Totaltid?";
	public static final String IMPOSSIBLE_LAP_TIME = "Omöjlig varvtid?";
	public static final Time MINIMUM_TOTAL_TIME = new Time("00.15.00");

	@Override
	public abstract String row(Competitor c);

	/**
	 * Prints the result in the list with competitors to the output file.
	 * Divides the list of competitors into different lists depending on
	 * class types. Send one list at a time to the printer.
	 * 
	 * @param competitors
	 *            list of competitors
	 * @param filepath
	 *            the file to write to
	 */
	@Override
	public void printResults(List<Competitor> competitors, String filepath) {
		try {
			File outputFile = new File(filepath);
			FileWriter fileWriter = new FileWriter(outputFile);

			int fromIndex = 0;
			int toIndex = 0;

			while (toIndex < competitors.size()) {
				String classType = competitors.get(toIndex).getClassType();
				String prevClassType = classType;
				toIndex = getNewIndex(competitors, fromIndex, toIndex,
						prevClassType, classType);
				
				// classList now contains competitors of the same class
				List<Competitor> classList = competitors.subList(fromIndex,
						toIndex - 1);

				fromIndex = toIndex - 1;
				
				// Do not write a line if there's no class type
				if (classType != "") {
					fileWriter.append(prevClassType + "\n");
				}
				prevClassType = classType;

				appendRows(fileWriter, classList);

				for (Competitor comp : classList) {
					fileWriter.append("" + row(comp) + "\n");
				}
			}

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Searches the list of competitors and increases toIndex as long as the
	 * class type of the competitor at index toIndex is the same as the previous
	 * class type. When it finds a new class type, search ends and the current
	 * toIndex is returned.
	 * 
	 * @param competitors
	 *            the total list of competitors
	 * @param fromIndex
	 *            the index to start search at
	 * @param toIndex
	 *            the current toIndex
	 * @param prevClassType
	 *            the last found class type
	 * @param classType
	 *            the current clas type
	 * @return the next toIndex
	 */
	private int getNewIndex(List<Competitor> competitors, int fromIndex,
			int toIndex, String prevClassType, String classType) {

		toIndex++;
		while (toIndex < competitors.size()
				&& classType.compareTo(prevClassType) == 0) {
			classType = competitors.get(toIndex).getClassType();
			toIndex++;
		}
		if (toIndex == competitors.size()) {
			toIndex++;
		}

		return toIndex;
	}

	protected abstract void appendRows(FileWriter fileWriter,
			List<Competitor> competitors) throws IOException;

}
