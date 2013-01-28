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
	public Sorter(List<Competitor> competitors, String resultPath, String registerPath) {
		result = new File(resultPath);
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
		
		String s = sb.toString();
		return s.substring(0, s.length() - 2);
	}


	public List<Competitor> sort() {
		// TODO Auto-generated method stub
		return competitors;
	}
}
