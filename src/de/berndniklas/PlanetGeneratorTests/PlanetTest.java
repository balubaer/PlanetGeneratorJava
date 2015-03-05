package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.berndniklas.PlanetGenerator.Planet;

public class PlanetTest {

	@Test
	public void testToString() {
		Planet aPlanet = new Planet();
		aPlanet.setNumber(100);
		assertEquals("toString ist not correct", aPlanet.toString(), "W(100)");
		
		aPlanet = new Planet();
		aPlanet.setNumber(55);
		assertEquals("toString ist not correct", aPlanet.toString(), "W(55)");
		
		aPlanet = new Planet();
		aPlanet.setNumber(1);
		assertEquals("toString ist not correct", aPlanet.toString(), "W(1)");
	}

}
