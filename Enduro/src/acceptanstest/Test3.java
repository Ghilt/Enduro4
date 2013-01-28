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
import org.junit.Ignore;
import org.junit.Test;


public class Test3 {
	private SorterMain sorter;
	private Scanner scan1;
	private Scanner scan2;
	
	@Before
	public void initialize(){
		ReadResult readResult = new ReadResult(
				new File("acceptans/register.txt"),
				new File("acceptans/starttider.txt"),
				new File("acceptans/maltider.txt"));
		
		List<Competitor> competitors = new ArrayList<Competitor>(readResult.openResultFile().values());
		
		sorter.printResults(competitors, "acceptans/resultat2.txt");
		
		
	}
	
	@Ignore
	public void testResult() throws FileNotFoundException {
		File file1 = new File("acceptans/resultat.txt");
		File file2 = new File("acceptans/resultat2.txt");
		scan1 = new Scanner(file1);
		//System.out.println(scan1.nextLine());
		//System.out.println(scan1.next());
		scan2 = new Scanner(file2);
		String line1, line2;
		assertTrue(scan1.hasNext());
//		System.out.println(scan1.nextLine());
//		System.out.println(scan2.nextLine());
		
		
		
	}
	

}
