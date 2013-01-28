package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.*;

import sort.Competitor;
import sort.Sorter;
import static org.junit.Assert.*;

public class TestSorter {
	Sorter s;
	Competitor comp;
	
	@Before
	public void Initialize() {
		comp = new Competitor(1);
		
		s = new Sorter(comp);
		
	}

	@Test
	public void testSorterCreatesFile() {
		File file = new File("sorted_result.txt");
		s.printResult();
		assertTrue(file.exists());
	}
	
	@Test
	public void testResultFile() throws IOException {
		s.printResult();
		String test = "StartNr; TotalTid; StartTid; Måltid\n1; 1.23.45; 12.00.00; 13.23.34";
		try {
			FileReader reader = new FileReader("sorted_result.txt");
			for(int i = 0; i < test.length(); i++) {
				assertEquals(test.charAt(i), reader.read());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

}
