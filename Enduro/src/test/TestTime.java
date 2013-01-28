package test;

import static org.junit.Assert.*;

import org.junit.Test;

import sort.Time;

public class TestTime {

	@Test
	public void testCreateObject() {
		assertFalse(new Time(5000)==null);
	}
	
	@Test
	public void testConvertFromStringSimple() {
		assertEquals(new Time(new String("1;00;00")), new Time(3600));
	}
	
	@Test
	public void testConvertFromStringComplicated() {
		assertEquals(new Time("5;23;01"), new Time(19381));
	}
	
	@Test
	public void testConvertFromInvalidStringGives0() {
		assertEquals(new Time("bajs"), new Time(0));
	}

}
