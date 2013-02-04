package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.junit.*;

import sort.Competitor;
import sort.CompetitorPrinter;
import sort.SorterMain;
import sort.StdCompetitorPrinter;
import sort.Time;
import static org.junit.Assert.*;

public class TestResultFile {
	
	private List<Competitor> competitors;
	private Time start;
	private Time finish;
	private CompetitorPrinter cp;
	
	@Before
	public void Initialize() {
		cp = new StdCompetitorPrinter();
		competitors = new ArrayList<Competitor>();
		start = new Time(123456);
		finish = new Time(654321);
		Competitor comp = new Competitor(1);
		comp.addStartTime(start);
		comp.addFinishTime(finish);

		competitors.add(comp);
	}

	/**
	 * Private helpmethod for testing the first line in the resultfile.
	 * 
	 * @param scan
	 *            The scanner reading the resultfile.
	 */
	private void testFirstLineInResult(Scanner scan) {
		String firstLine = "StartNr; TotalTid; StartTid; Måltid";

		assertTrue(scan.hasNext());
		assertEquals("First line is missing, empty result list",
				scan.nextLine(), firstLine);
	}

	@Test
	public void testSorterMainCreatesFile() {
		File file = new File("sorted_result.txt");
		//Collections.sort(competitors);
		
		SorterMain.printResults(competitors, "sorted_result.txt", cp);
		assertTrue(file.exists());
	}

	@Test
	public void testResultFile() throws IOException {
		//Collections.sort(competitors);
		
		SorterMain.printResults(competitors, "sorted_result.txt", cp);

		File file = new File("sorted_result.txt");

		Scanner scan = new Scanner(file);

		testFirstLineInResult(scan);

		for (Competitor comp : competitors) {
			// Need implementation in competitor to work (toString()).
			assertTrue(scan.hasNext());
			assertEquals(scan.nextLine(), cp.row(comp));
		}
	}

	@Test
	public void testMissingStartTime() throws IOException {
		Competitor competitor = new Competitor(2);
		competitor.addFinishTime(finish);
		competitors.add(competitor);

		
		SorterMain.printResults(competitors, "sorted_result.txt", cp);

		File file = new File("sorted_result.txt");

		Scanner scan = new Scanner(file);

		testFirstLineInResult(scan);

		assertTrue(scan.hasNext());
		assertEquals(scan.nextLine(), cp.row(competitors.get(0)));

		assertTrue(scan.hasNext());
		Competitor comp = competitors.get(1);
		assertTrue("Competitor should not contain start time!", comp
				.getStartTimes().size() == 0);

		assertEquals("2; " + Time.NULL_TIME + "; Start?; "
				+ comp.getFinishTimes().get(0) + ";", scan.nextLine());
	}

	@Test
	public void testMissingFinishTime() throws IOException {
		Competitor competitor = new Competitor(2);
		competitor.addStartTime(start);
		competitors.add(competitor);
		
		SorterMain.printResults(competitors, "sorted_result.txt", cp);

		File file = new File("sorted_result.txt");

		Scanner scan = new Scanner(file);

		testFirstLineInResult(scan);

		assertTrue(scan.hasNext());
		assertEquals(scan.nextLine(), cp.row(competitors.get(0)));

		assertTrue(scan.hasNext());
		Competitor comp = competitors.get(1);
		assertTrue("Competitor should not contain finish time!", comp
				.getFinishTimes().size() == 0);

		assertEquals("2; " + Time.NULL_TIME + "; "
				+ comp.getStartTimes().get(0) + "; Slut?;", scan.nextLine());
	}

	@Test
	public void testMultipleStartTimes() throws IOException {
		Competitor competitor = new Competitor(2);
		competitor.addStartTime(start);
		Time time1 = new Time(23456);
		Time time2 = new Time(45678);
		competitor.addStartTime(time1);
		competitor.addStartTime(time2);
		
		
		competitor.addFinishTime(finish);
		competitors.add(competitor);
		//Collections.sort(competitors);
		
		SorterMain.printResults(competitors, "sorted_result.txt", cp);

		File file = new File("sorted_result.txt");

		Scanner scan = new Scanner(file);

		testFirstLineInResult(scan);

		assertTrue(scan.hasNext());
		assertEquals(scan.nextLine(), cp.row(competitors.get(0)));

		assertTrue(scan.hasNext());
		assertEquals("2; " + start.difference(finish) + "; " + start + "; " + finish + "; Flera starttider? " + time1 + ", " + time2, scan.nextLine());
	}

}
