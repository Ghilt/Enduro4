package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SorterMain {

	public static void main(String[] args) {
		
		
		
		List<Competitor>  competitors = new ArrayList<Competitor>();
		
		Collections.sort(competitors);
		
		printResults(competitors);
	}
	
	public static void printResults(List<Competitor> competitors) {
		try {
			File outputFile = new File("sorted_result.txt");
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
