package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.berndniklas.PlanetGenerator.Planet;
import de.berndniklas.PlanetGenerator.Port;

public class PortTest {

	@Test
	public void test() {
		Planet aPlanet100 = new Planet();
		aPlanet100.setNumber(100);

		Planet aPlanet55 = new Planet();
		aPlanet55.setNumber(55);
		Planet aPlanet60 = new Planet();
		aPlanet60.setNumber(60);
		
		Port aPort = new Port();
		aPort.planets.add(aPlanet55);
		aPort.planet = aPlanet100;
		assertEquals("Port.toString is not correct", aPort.toString(), "W100(55)");
		
		aPort.planets.add(aPlanet60);
		assertEquals("Port.toString is not correct", aPort.toString(), "W100(55,60)");
		
	}
	
	@Test
	public void testHasConnectionToPlanet() {
		Planet aPlanet100 = new Planet();
		aPlanet100.setNumber(100);

		Planet aPlanet55 = new Planet();
		aPlanet55.setNumber(55);
		Planet aPlanet55_1 = new Planet();
		aPlanet55_1.setNumber(55);
		
		Planet aPlanet60 = new Planet();
		aPlanet60.setNumber(60);
		
		Port aPort = new Port();
		aPort.planets.add(aPlanet55);
		aPort.planet = aPlanet100;
		assertTrue("Port.hasConnectionToPlanet is not correct", aPort.hasConnectionToPlanet(aPlanet55_1));

		assertFalse("Port.hasConnectionToPlanet is not correct", aPort.hasConnectionToPlanet(aPlanet60));

	}

}
