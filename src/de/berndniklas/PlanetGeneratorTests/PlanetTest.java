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
	}

}
