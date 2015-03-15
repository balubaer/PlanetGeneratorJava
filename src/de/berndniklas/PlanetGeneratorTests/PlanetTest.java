package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.berndniklas.PlanetGenerator.Planet;

public class PlanetTest {

	@Test
	public void testToString() {
		Planet aPlanet = new Planet();
		aPlanet.setNumber(100);
		assertEquals("toString is not correct", aPlanet.toString(), "W100");
		
		aPlanet = new Planet();
		aPlanet.setNumber(55);
		assertEquals("toString is not correct", aPlanet.toString(), "W55");
		
		aPlanet = new Planet();
		aPlanet.setNumber(1);
		assertEquals("toString is not correct", aPlanet.toString(), "W1");
	}

	@Test
	public void testCompareTo() {
		Planet aPlanet100 = new Planet();
		aPlanet100.setNumber(100);
		assertEquals("compareTo is not correct", aPlanet100.compareTo(aPlanet100), 0);

		Planet aPlanet55 = new Planet();
		aPlanet55.setNumber(55);
		assertEquals("compareTo is not correct", aPlanet100.compareTo(aPlanet55), 1);
		assertEquals("compareTo is not correct", aPlanet55.compareTo(aPlanet100), -1);
	}
}
