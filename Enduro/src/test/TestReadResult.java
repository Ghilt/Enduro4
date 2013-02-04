package test;

import static org.junit.Assert.*;
import java.io.File;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import result.ReadResult;
import sort.Competitor;
import result.CvsReader;

public class TestReadResult {
	private ReadResult result;
	
	@Before
	public void initialize(){
		result = new ReadResult(new File("register_simple.txt"), new File("start_simple.txt"), new File("end_simple"), new File ("names.txt"));
	}

	@Test(expected = FileNotFoundException.class)
	public void testNoSuchFile() throws FileNotFoundException {
		CvsReader read = new CvsReader("File does not exist");
		read.readAll();

	}

	@Test
	public void testReadEmptyFile() throws FileNotFoundException {
		CvsReader read = new CvsReader("src/test/emptyfile.txt");
		ArrayList<ArrayList<String>> array = read.readAll();
		assertEquals(array.size(), 0);
	}

	@Test
	public void testAddRegToResult() {
		assertFalse(result.openResultFile().size()== 0);
	}
	
	@Test
	public void testAddStartToResult(){
		assertTrue(result.openResultFile().get(1).getStartTimes().get(0)!=null);
	}
	@Test
	public void testAddEndToResult(){
		assertTrue(result.openResultFile().get(1).getFinishTimes().get(0)!=null);
	}
	
	@Test
	public void testStartTimeAdded(){
		assertEquals(result.openResultFile().get(1).getStartTimes().get(0).toString(), "00.00.00");
	}
	

	@Test	
	public void testFinishTimeAdded(){
		assertEquals(result.openResultFile().get(1).getFinishTimes().get(0).toString(), "01.40.40");
	}
	public void testReadEntireFile() throws FileNotFoundException {
		CvsReader read = new CvsReader("src/test/simpleReadFile.txt");
		ArrayList<ArrayList<String>> array = read.readAll();
		assertEquals("Row length invalid", 3, array.size());
		assertEquals("Column length invalid", 4, array.get(0).size());
		assertEquals("A cell is read invalid", array.get(1).get(2), "art3");

	}
	
	@Test
	public void testNameAdded(){
		HashMap<Integer,Competitor> list = result.openResultFile();
		
		assertEquals(list.get(1).getName(), "Anders Asson");
		assertEquals(list.get(2).getName(), "Berit Bsson");
		assertEquals(list.get(15).getName(), "Zlatan Ibra");
	}

}
