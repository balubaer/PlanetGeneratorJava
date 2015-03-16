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
}
