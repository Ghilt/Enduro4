package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Sorter {

	
	private File result;

	
	/**
	 * Create Sorter   
	 */
	public Sorter() {
		result = new File("sorted_result.txt");
	}
	
	
	/**
	 * Assemble the files
	 * 
	 *    @param files the files containing times
	 */
	public void merge(File ...files ){
		
	}
	
	/**
	 * Print the result list   
	 */
	public void printResult(){

		try {
			FileWriter fileWriter = new FileWriter(result);
			fileWriter.append("StartNr; TotalTid; StartTid; Måltid\n");
			fileWriter.append("1; 1.23.45; 12.00.00; 13.23.34");
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
