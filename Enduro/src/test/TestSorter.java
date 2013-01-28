package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.*;

import sort.Competitor;
import sort.Sorter;
import sort.Time;
import static org.junit.Assert.*;

public class TestSorter {
	Sorter s;
	List<Competitor> competitors;
	Time start;
	Time finish;
	
	@Before
	public void Initialize() {
		competitors = new ArrayList<Competitor>();
		start = new Time(123456);
		finish = new Time(654321);
		Competitor comp = new Competitor(1);
		comp.addStartTime(start);
		comp.addFinishTime(finish);
		
		competitors.add(comp);
		s = new Sorter(competitors);
		
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
		String firstLine = "StartNr; TotalTid; StartTid; Måltid";
		File file = new File("sorted_result.txt");
		
		Scanner scan = new Scanner(file);
		assertTrue(scan.hasNext());
		assertEquals("First line is missing, empty result list", scan.nextLine(), firstLine);
		
		for(Competitor comp : competitors) {
			// Need implementation in competitor to work (toString()).
//			assertTrue(scan.hasNext());
//			assertEquals(scan.hasNext(), comp.toString());
		}
	}
	
	
	
	
	

}
