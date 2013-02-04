package acceptanstest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sort.*;
import result.Parser;



import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class Test3_5 {
	private SorterMain sorter;
	private Scanner scan1;
	private Scanner scan2;
	
	@Before
	public void initialize(){
	}
	
	
	@Ignore
	public void testResult() throws FileNotFoundException {
		File file1 = new File("src/acceptanstest/TEST3resultat.txt");
		File file2 = new File("src/acceptanstest/TEST3resultat2.txt");
		scan1 = new Scanner(file1);
		scan2 = new Scanner(file2);
		String line1, line2;
		while (scan1.hasNext() && scan2.hasNext()) {
			line1 = scan1.nextLine();
			line2 = scan2.nextLine();
			assertEquals(line1, line2);
		}
		
		
		
	}
	

}
