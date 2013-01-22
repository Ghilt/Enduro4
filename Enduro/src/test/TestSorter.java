package test;

import java.io.File;

import org.junit.*;

import sort.Sorter;
import static org.junit.Assert.*;

public class TestSorter {

	@Test
	public void testSorterCreatesFile() {
		File file = new File("sorted_result.txt");	
		Sorter s = new Sorter();
		s.printResult();
		assertTrue(file.exists());
		
		
		
	}
	
	
	

}
