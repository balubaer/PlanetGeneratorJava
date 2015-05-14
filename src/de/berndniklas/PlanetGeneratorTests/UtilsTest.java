package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import de.berndniklas.PlanetGenerator.Utils;

public class UtilsTest {

	@Test
	public void testCreateBracketAndCommarStringWithStringArray() {
		ArrayList<String> stringArray = new ArrayList<String>();
		stringArray.add("Bastian");
		stringArray.add("Jonathan");
		stringArray.add("Anke");
		stringArray.add("Bernd");
		
		String newString = Utils.createBracketAndCommarStringWithStringArray(stringArray);
		
		assertEquals("Utils.createBracketAndCommarStringWithStringArray is not correct", newString, "(Bastian,Jonathan,Anke,Bernd)");
	    }
	
	@Test
	public void testStr2Int() {
	    assertEquals("is numeric", (Integer)(-5), Utils.str2Int("-5"));
	    assertEquals("is numeric", (Integer)50, Utils.str2Int("50.00"));
	    assertEquals("is numeric", (Integer)20, Utils.str2Int("$ 20.90"));
	    assertEquals("is numeric", (Integer)5, Utils.str2Int(" 5.321"));
	    assertEquals("is numeric", (Integer)1000, Utils.str2Int("1,000.50"));
	    assertEquals("is numeric", (Integer)0, Utils.str2Int("0.50"));
	    assertEquals("is numeric", (Integer)0, Utils.str2Int(".50"));
	    assertEquals("is numeric", (Integer)0, Utils.str2Int("-.10"));
	    assertEquals("is numeric", (Integer)Integer.MAX_VALUE, Utils.str2Int(""+Integer.MAX_VALUE));
	    assertEquals("is numeric", (Integer)Integer.MIN_VALUE, Utils.str2Int(""+Integer.MIN_VALUE));
	    assertEquals("Not is numeric", null, Utils.str2Int("czv.,xcvsa"));
	    /**
	     * Dynamic test
	     */
	    for(Integer num = 0; num < 1000; num++) {
	        for(int spaces = 1; spaces < 6; spaces++) {
	            String numStr = String.format("%0"+spaces+"d", num);
	            Integer numNeg = num * -1;
	            assertEquals(numStr + ": is numeric", num, Utils.str2Int(numStr));
	            assertEquals(numNeg + ": is numeric", numNeg, Utils.str2Int("- " + numStr));
	        }
	    }
	}
	@Test
	public void testExtractIntFromcommantElementString() {
		assertEquals("extractIntFromcommantElementString Fail", 10, Utils.extractIntFromcommantElementString("F10"));
		assertEquals("extractIntFromcommantElementString Fail", 5, Utils.extractIntFromcommantElementString("U5"));
		assertEquals("extractIntFromcommantElementString Fail", 2, Utils.extractIntFromcommantElementString("W2"));
		assertEquals("extractIntFromcommantElementString Fail", 3, Utils.extractIntFromcommantElementString("T3"));
		assertEquals("extractIntFromcommantElementString Fail", 853, Utils.extractIntFromcommantElementString("D853"));
	}
}
