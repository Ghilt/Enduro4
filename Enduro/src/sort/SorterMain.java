package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import result.Parser;


public class SorterMain {
	
	/**

	 * Read input files and create list with competitors, and call printResults
	 * to print the results to the output file.
	 * 
	 * @param args Arg0 = Input registered, Arg1 = Input Start Times, Arg2 = Input Finish Times, Arg3 = Input names, Arg4 = Output Result
	 */
	public static void main(String[] args) {
		/*Parser readResult = new Parser(
				new File(args[0]),
				new File(args[1]),
				new File(args[2]),
				new File(args[3]));
		
		List<Competitor> competitors = new ArrayList<Competitor>(readResult.openResultFile().values());
		//Collections.sort(competitors); 
		printResults(competitors, args[4], new StdCompetitorPrinter());*/
	}
	
}
