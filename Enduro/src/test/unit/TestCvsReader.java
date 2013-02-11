package test.unit;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import result.CvsReader;

public class TestCvsReader {

	@Test(expected = FileNotFoundException.class)
	public void testNoSuchFile() throws FileNotFoundException {
		CvsReader read = new CvsReader("File does not exist");
		read.readAll();
	}

	@Test
	public void testReadEmptyFile() throws FileNotFoundException {
		CvsReader read = new CvsReader("src/test/unit/emptyfile.txt");
		ArrayList<ArrayList<String>> array = read.readAll();
		assertEquals(array.size(), 0);
	}

	@Test
	public void testReadEntireFile() throws FileNotFoundException {
		CvsReader read = new CvsReader("src/test/unit/simpleReadFile.txt");
		ArrayList<ArrayList<String>> array = read.readAll();
		assertEquals("Row length invalid", 3, array.size());
		assertEquals("Column length invalid", 4, array.get(0).size());
		assertEquals("A cell is read invalid", "art3", array.get(1).get(2));
	}

}
