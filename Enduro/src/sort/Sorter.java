package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Sorter {

	
	private File result;
	private List<Competitor> competitors;

	
	/**
	 * Create Sorter   
	 */
	public Sorter(List<Competitor> competitors) {
		result = new File("sorted_result.txt");
		this.competitors = competitors;
	}
	
	
	/**
	 * Assemble the files
	 * 
	 *    @param files the files containing times
	 */
	public void merge(File ...files ){
		// TODO
	}
	
	/**
	 * Print the result list   
	 */
	public void printResult(){

		try {
			FileWriter fileWriter = new FileWriter(result);
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
