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

	@Test
	public void testCompareTo() {
		Planet aPlanet100 = new Planet();
		aPlanet100.setNumber(100);
		assertEquals("compareTo ist not correct", aPlanet100.compareTo(aPlanet100), 0);

		Planet aPlanet55 = new Planet();
		aPlanet55.setNumber(55);
		assertEquals("compareTo ist not correct", aPlanet100.compareTo(aPlanet55), 1);
		assertEquals("compareTo ist not correct", aPlanet55.compareTo(aPlanet100), -1);
	}
}
