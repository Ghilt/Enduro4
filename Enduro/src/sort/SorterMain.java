package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import result.ReadResult;


public class SorterMain {
	
	/**
	 * Read input files and create list with competitors, and call printResults
	 * to print the results to the output file.
	 * 
	 * @param args Arg0 = Input registered, Arg1 = Input Start Times, Arg2 = Input Finish Times, Arg3 = Output result
	 */
	public static void main(String[] args) {
		ReadResult readResult = new ReadResult(
				new File(args[0]),
				new File(args[1]),
				new File(args[2]));
		
		List<Competitor> competitors = new ArrayList<Competitor>(readResult.openResultFile().values());
		//Collections.sort(competitors); 
		printResults(competitors, args[3]);
	}
	
	/**
	 * Prints the result in the list with competitors to the output file.
	 * 
	 * @param competitors	list of competitors
	 * @param output		the file to write to
	 */
	public static void printResults(List<Competitor> competitors, String output) {
		try {
			File outputFile = new File(output);
			FileWriter fileWriter = new FileWriter(outputFile);
			fileWriter.append("StartNr; TotalTid; StartTid; Måltid\n");
			for(Competitor comp : competitors) {
				fileWriter.append("" + comp + "\n");
			}
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
