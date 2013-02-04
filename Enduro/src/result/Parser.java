package result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import sort.Competitor;
import sort.Time;

public class Parser {
	private final static String START_NO = "StartNr";
	private final static String START_TIME = "Starttid";
	private final static String FINISH_TIME = "Måltid";
	private final static String NAME = "Namn";
	
	
	
	public Parser(){
	}
	
	/**
	 * Parses the input string matrix.
	 * First parses the first line to get the type of each column.
	 * Then loop each row and add the data in each row to either a new or
	 * an already existing competitor.
	 * 
	 * @param input				The string matrix of data to parse.
	 * @param competitors		The hashmap of competitors to modify.
	 * @return					
	 * @throws ParserException	If the input is incorrect.
	 */
	public HashMap<Integer, Competitor> parse(ArrayList<ArrayList<String>> input, HashMap<Integer, Competitor> competitors) throws ParserException {
		ArrayList<Identifier> types = new ArrayList<Identifier>();
		
		if(input.size() <= 1) {
			throw new ParserException("Invalid input.");
		}
		ArrayList<String> firstLine = input.get(0);
		types = parseIdentifier(firstLine);
		
		if(types.size() <= 1 || types.get(0) != Identifier.start_nr) {
			throw new ParserException("Missing start number.");
		}
		
		for(int i = 1; i < input.size(); i++) {
			ArrayList<String> row = input.get(i);
			if(row.size() != types.size()) {
				throw new ParserException("Column length mismatch.");
			}
			int startNbr = Integer.valueOf(row.get(0));
			
			Competitor comp = competitors.get(startNbr);
			if(comp == null) {
				comp = new Competitor(startNbr);
			}
			
			for(int j = 1; j < row.size(); j++) {
				switch(types.get(j)) {
				case finish_time:
					comp.addFinishTime(new Time(row.get(j)));
					break;
				case name:
					//comp.addName(row.get(j));
					break;
				case start_time:
					comp.addStartTime(new Time(row.get(j)));
					break;
				}
			}
			
			competitors.put(startNbr, comp);
		}
		return competitors;
	}

	
	/**
	 * Creates a new HashMap of competitors and calls the other parse method.
	 * 
	 * @param input		The string matrix of data.
	 * @return			A hashmap of competitors.
	 * @throws ParserException	If the input file is incorrect.
	 */
	public HashMap<Integer, Competitor> parse(
			ArrayList<ArrayList<String>> input) throws ParserException {
		HashMap<Integer, Competitor> competitors = new HashMap<Integer, Competitor>();
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
	 * Parses the first lines and returns an arraylist containing enum
	 * types the types of the columns.
	 * @param firstLine		The first line containing types of columns.
	 * @return	the arraylist of types
	 */
	private ArrayList<Identifier> parseIdentifier(ArrayList<String> firstLine) {
		ArrayList<Identifier> types = new ArrayList<Identifier>();
		
		for(String s : firstLine) {
			if(s.equalsIgnoreCase(START_NO)) {
				types.add(Identifier.start_nr);
			} else if(s.equalsIgnoreCase(START_TIME)) {
				types.add(Identifier.start_time);
			} else if(s.equalsIgnoreCase(FINISH_TIME)) {
				types.add(Identifier.finish_time);
			} else if(s.equalsIgnoreCase(NAME)) {
				types.add(Identifier.name);
			}
		}
		
		return types;
	}

}

		