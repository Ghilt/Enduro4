package result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import members.Competitor;
import members.Time;
import sort.Formater;

/**
 * @author Henrik & Philip
 * 
 *         Parses files.
 */
public class Parser {

	public Parser() {
	}

	/**
	 * Parses the input string matrix. First parses the first line to get the
	 * type of each column. Then loop each row and add the data in each row to
	 * either a new or an already existing competitor.
	 * 
	 * @param input
	 *            The string matrix of data to parse.
	 * @param competitors
	 *            The hashmap of competitors to modify.
	 * @return
	 * @throws ParserException
	 *             If the input is incorrect.
	 */
	public Map<Integer, Competitor> parse(ArrayList<ArrayList<String>> input,
			Map<Integer, Competitor> cs) throws ParserException {
		ArrayList<Identifier> types = new ArrayList<Identifier>();

		Map<Integer, Competitor> competitors = new HashMap<Integer, Competitor>(
				cs);

		if (input.size() < 2)
			throw new ParserException("Invalid input.");

		ArrayList<String> firstLine = input.get(0);
		// Parses the first line to know what each column means.
		types = parseIdentifier(firstLine);

		if (types.size() < 1 || types.get(0) != Identifier.start_nr)
			throw new ParserException("Missing start number.");

		String classType = "";
		for (int i = 1; i < input.size(); i++) {
			ArrayList<String> row = input.get(i);

			if (row.size() != 1 && row.size() != types.size()) {
				throw new ParserException("Column length mismatch.");
			} else if (row.size() == 1) {
				classType = row.get(0);
				continue;
			}

			// Startnbr is always first column.
			int startNbr = Integer.valueOf(row.get(0));

			Competitor comp = competitors.get(startNbr);
			if (comp == null) {
				comp = new Competitor(startNbr);
			}

			if (classType != "") {
				comp.setClassType(classType);
			}

			// Starts at index 1 because first column (startnbr) is already
			// parsed
			for (int j = 1; j < row.size(); j++) {
				switch (types.get(j)) {
				case finish_time:
					comp.addFinishTime(new Time(row.get(j)));
					break;
				case name:
					comp.addName(row.get(j));
					break;
				case start_time:
					comp.addStartTime(new Time(row.get(j)));
					break;
				default:
					throw new ParserException("Invalid type.");
				}
			}

			competitors.put(startNbr, comp);
		}
		return competitors;
	}

	/**
	 * Creates a new HashMap of competitors and calls the other parse method.
	 * 
	 * @param input
	 *            The string matrix of data.
	 * @return A hashmap of competitors.
	 * @throws ParserException
	 *             If the input file is incorrect.
	 */
	public Map<Integer, Competitor> parse(ArrayList<ArrayList<String>> input)
			throws ParserException {
		Map<Integer, Competitor> competitors = new HashMap<Integer, Competitor>();
		competitors = parse(input, competitors);

		return competitors;
	}

	/**
	 * Container for column types.
	 */
	private enum Identifier {
		start_time, finish_time, name, start_nr;
	}

	/**
	 * Parses the first lines and returns an arraylist containing enum types the
	 * types of the columns.
	 * 
	 * @param firstLine
	 *            The first line containing types of columns.
	 * @return the arraylist of types
	 * @throws ParserException
	 *             if a type is invalid
	 */
	private ArrayList<Identifier> parseIdentifier(ArrayList<String> firstLine)
			throws ParserException {
		ArrayList<Identifier> types = new ArrayList<Identifier>();

		for (String s : firstLine) {
			if (s.equalsIgnoreCase(Formater.START_NR)) {
				types.add(Identifier.start_nr);
			} else if (s.equalsIgnoreCase(Formater.START_TIME)) {
				types.add(Identifier.start_time);
			} else if (s.equalsIgnoreCase(Formater.FINISH_TIME)) {
				types.add(Identifier.finish_time);
			} else if (s.equalsIgnoreCase(Formater.NAME)) {
				types.add(Identifier.name);
			} else {
				throw new ParserException("Invalid strin: " + s);
			}
		}

		return types;
	}

}
