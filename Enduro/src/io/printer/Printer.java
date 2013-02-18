package io.printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import members.Competitor;
import members.Time;

/**
 * @author Andrée & Victor
 * 
 *         For printing competitors.
 */
public abstract class Printer {
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";
	public static final String MULTIPLE_STARTS = "Flera starttider?";
	public static final String MULTIPLE_STARTS_ETAPP = "Flera starttider Etapp";
	public static final String MULTIPLE_ENDS = "Flera måltider?";
	public static final String MULTIPLE_ENDS_ETAPP = "Flera måltider Etapp";
	public static final String IMPOSSIBLE_TOTAL_TIME = "Omöjlig Totaltid?";
	public static final String IMPOSSIBLE_LAP_TIME = "Omöjlig varvtid?";
	public static final Time MINIMUM_TOTAL_TIME = Time.parse("00.15.00");

	public abstract String row(Competitor c);

	/**
	 * Prints the result in the list with competitors to the output file.
	 * Divides the list of competitors into different lists depending on class
	 * types. Send one list at a time to the printer.
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
			int fromIndex = 0;
			int toIndex = 0;
			// noName are invalid competitors
			ArrayList<Competitor> noNames = new ArrayList<Competitor>();

			while (toIndex < competitors.size()) {
				String classType = competitors.get(toIndex).getClassType();
				String prevClassType = classType;
				toIndex = getNewIndex(competitors, fromIndex, toIndex,
						prevClassType, classType);

				// classList now contains competitors of the same class
				List<Competitor> classList = competitors.subList(fromIndex,
						toIndex - 1);

				setPlacements(classList);

				fromIndex = toIndex - 1;

				// Do not write a line if there's no class type
				if (classType != "") {
					fileWriter.append(prevClassType + "\n");
				}
				prevClassType = classType;

				appendRows(fileWriter, classList);

				for (Competitor comp : classList) {
					// Check if person has name a.k.a this person exists
					if (comp.getName().isEmpty()) {
						noNames.add(comp);
					} else {
						fileWriter.append("" + row(comp) + "\n");
					}
				}
			}

			appendInvalidStartNbrs(fileWriter, noNames);

			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void setPlacements(List<Competitor> competitors) {
		int maxLaps = getMaxLaps(competitors);
		for (int i = 0; i < competitors.size(); i++) {
			if (competitors.get(i).getNumberOfLaps() == maxLaps) {
				competitors.get(i).setPlac(i + 1);
			}
		}

	}

	/**
	 * Prints the invalid competitors A competitor is invalid if they have no
	 * name.
	 * 
	 * @param fileWriter
	 *            write to file
	 * @param noNames
	 *            List with invalid competitors
	 * @throws IOException
	 */
	private void appendInvalidStartNbrs(FileWriter fileWriter,
			ArrayList<Competitor> noNames) throws IOException {
		if (!noNames.isEmpty()) {
			fileWriter.append("Icke existerande startnummer" + "\n");
			appendRows(fileWriter, noNames);
			for (Competitor comp : noNames) {
				fileWriter.append("" + row(comp) + "\n");
			}

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

	/**
	 * Returns the maximum number of laps ran by any competitor.
	 * 
	 * @param competitors
	 *            list of competitors
	 * @return the maximum number of laps ran by any competitor
	 */
	protected int getMaxLaps(List<Competitor> competitors) {
		int a = competitors.get(0).getNumberOfLaps();
		for (Competitor c : competitors) {
			if (c.getNumberOfLaps() > a) {
				a = c.getNumberOfLaps();
			}
		}
		return a;
	}

}
