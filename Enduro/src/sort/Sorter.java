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
	 * Return a string formated for a column
	 * @param s String
	 * @return String as column
	 */
	public static String formatColumn(Object o) {
		return o + "; ";
	}
	
	public static String formatColumns(Object... objs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : objs) {
			sb.append(formatColumn(o));
		}
		
		return sb.toString();
	}


	public List<Competitor> sort() {
		// TODO Auto-generated method stub
		return competitors;
	}
}
