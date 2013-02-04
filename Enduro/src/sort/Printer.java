package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import members.Competitor;
import members.NullTime;
import members.Time;

/**
 * @author Andrée & Victor
 *
 * For printing competitors.
 */
public abstract class Printer implements CompetitorPrinter {
	public static final String NO_START = "Start?";
	public static final String NO_END = "Slut?";
	public static final String MULTIPLE_STARTS = "Flera starttider?";
	public static final String MULTIPLE_ENDS = "Flera måltider?";
	public static final String IMPOSSIBLE_TOTAL_TIME = "Omöjlig Totaltid?";
	public static final Time MINIMUM_TOTAL_TIME = new Time("00.15.00");
	
	
	/**
	 * @return Total time elapsed, or Null time string
	 */
	protected abstract Time totalTime(Competitor c);
	
	
	@Override
	public abstract String row(Competitor c);
	
	/**
	 * Prints the result in the list with competitors to the output file.
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
			
			
			appendRows(fileWriter, competitors);
			
			for (Competitor comp : competitors) {
				fileWriter.append("" + row(comp) + "\n");
			}
			
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void appendRows(FileWriter fileWriter, List<Competitor> competitors)  throws IOException;
	
}
