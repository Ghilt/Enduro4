package sort;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Sorter {

	
	private File result;
	private Competitor competitor;

	
	/**
	 * Create Sorter   
	 */
	public Sorter(Competitor competitor) {
		result = new File("sorted_result.txt");
		this.competitor = competitor;
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
	
	/**
	 * Return a string formated for a column
	 * @param s String
	 * @return String as column
	 */
	public static String formatColumn(String s) {
		return s + "; ";
	}
	
	public static String formatColumns(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(formatColumn(s));
		}
		
		return sb.toString();
	}
}
