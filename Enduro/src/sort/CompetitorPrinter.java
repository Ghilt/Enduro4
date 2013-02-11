package sort;

import java.util.List;

import members.Competitor;

/**
 * @author Andrée & Victor
 * 
 *         For printing competitors.
 */
public interface CompetitorPrinter {
	/**
	 * Prints a competitor as a string.
	 * 
	 * @param c
	 *            The Competitor
	 * @return The formatted String
	 */
	public String row(Competitor c);

	/**
	 * Prints an entire list of competitors into a file.
	 * 
	 * @param competitors
	 * @param filepath
	 *            The file to print to. For instance "src/test/emptyfile.txt"
	 */
	public void printResults(List<Competitor> competitors, String filepath);
}
