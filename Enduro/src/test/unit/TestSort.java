package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import members.Competitor;
import members.Time;

import org.junit.Before;
import org.junit.Ignore;

import sort.CompetitorPrinter;
import sort.StdCompetitorPrinter;

public class TestSort {
	private List<Competitor> competitors;
	private CompetitorPrinter cp;

	@Before
	public void Initialize() {
		cp = new StdCompetitorPrinter();
		competitors = new ArrayList<Competitor>();
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
				firstLine, scan.nextLine());
	}

	@Ignore
	public void testSorterCreatesFile() {
		Competitor fastest = new Competitor(3);
		Competitor slowest = new Competitor(1);
		Competitor secondFastest = new Competitor(2);

		fastest.addStartTime(new Time(10000));
		fastest.addFinishTime(new Time(30000));

		slowest.addStartTime(new Time(20000));
		slowest.addFinishTime(new Time(70000));

		secondFastest.addStartTime(new Time(30000));
		secondFastest.addFinishTime(new Time(60000));

		competitors.add(secondFastest);
		competitors.add(slowest);
		competitors.add(fastest);

		File file = new File("src/test/tmp/sorted_result.txt");

		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(competitors);

		cp.printResults(competitors, "src/test/tmp/sorted_result.txt");

		testFirstLineInResult(scan);

		assertTrue(file.exists());

		assertTrue(scan.hasNext());
		assertEquals(fastest.toString(), scan.nextLine());

		assertTrue(scan.hasNext());
		assertEquals(secondFastest.toString(), scan.nextLine());

		assertTrue(scan.hasNext());
		assertEquals(slowest.toString(), scan.nextLine());
	}

}
