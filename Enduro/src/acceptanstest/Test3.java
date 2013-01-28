package acceptanstest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sort.*;
import result.ReadResult;



import org.junit.Before;
import org.junit.Test;


public class Test3 {
	private SorterMain sorter;
	private Scanner scan1;
	private Scanner scan2;
	
	@Before
	public void initialize(){
		ReadResult readResult = new ReadResult(
				new File("acceptanstest/register.txt"),
				new File("acceptanstest/starttider.txt"),
				new File("acceptanstest/maltider.txt"));
		
		List<Competitor> competitors = new ArrayList<Competitor>(readResult.openResultFile().values());
		
		sorter.printResults(competitors, "acceptanstest/resultat2.txt");
		
		
	}
	
	@Test
	public void testResult() throws FileNotFoundException {
		File file1 = new File("acceptanstest/resultat.txt");
		File file2 = new File("acceptanstest/resultat2.txt");
		scan1 = new Scanner(file1);
		scan2 = new Scanner(file2);
		String line1, line2;
		assertTrue(scan1.hasNext());
//		System.out.println(scan1.nextLine());
//		System.out.println(scan2.nextLine());
		
		
		
	}
	

}
