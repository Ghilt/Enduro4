package test.unit.gui;

import static org.junit.Assert.assertTrue;
import gui.register.Gui;
import io.reader.CvsReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestGui {

	private static final String TMP = "src/test/tmp/";
	private static final String NAME = TMP + "tider.txt";

	@Before
	public void setUp() {
		new File(NAME).delete();
	}

	@Test
	public void testGui() {
		Gui g = new Gui(NAME, "icon.png");
		g.dispose();
		// Will be true if the program does not crash!
	}

	@Test
	public void testWriteTime() throws FileNotFoundException {
		Gui g = new Gui(NAME, "icon.png");
		g.getTextField().setText("1");
		g.register();
		g.dispose();

		CvsReader parser = new CvsReader(NAME);
		ArrayList<ArrayList<String>> rows = parser.readAll();
		for (ArrayList<String> cols : rows) {
			assertTrue(cols.get(0).equalsIgnoreCase("1"));
			// for(String cell : cols){
			// }
		}
		// Will be true if the program does not crash!
	}

	@Test
	public void testWriteTimeComplex() throws FileNotFoundException {
		Gui g = new Gui(NAME, "icon.png");
		g.register();
		g.undo();
		g.getTextField().setText("1");
		g.register();
		g.register();
		g.getTextField().setText("3");
		g.register();
		g.dispose();

		CvsReader parser = new CvsReader(NAME);
		ArrayList<ArrayList<String>> rows = parser.readAll();
		assertTrue(rows.get(0).get(0).equalsIgnoreCase("1"));
		assertTrue(rows.get(1).get(0).equalsIgnoreCase("3"));
		// Will be true if the program does not crash!
	}
}
