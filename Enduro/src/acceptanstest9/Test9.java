package acceptanstest9;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import members.*;

import result.CvsReader;
import result.Parser;
import result.ParserException;

public class Test9 {
	private CvsReader reader;
	private Parser parser;
	

	
	@Before
	public void initialize(){
		parser = new Parser();
			
}
	
	@Test
	public void testResult() throws FileNotFoundException, ParserException{
		reader= new CvsReader("src/acceptanstest9/namnfil.txt");
		Map <Integer, Competitor> c = new HashMap<Integer, Competitor>();
		
		parser.parse(reader.readAll(), c);
		
		reader = new CvsReader("src/acceptanstest9/starttider.txt");
		parser.parse(reader.readAll(), c);
		
		reader = new CvsReader("src/acceptanstest9/maltider.txt");
		parser.parse(reader.readAll(), c);
		
		
	}
	
	
}