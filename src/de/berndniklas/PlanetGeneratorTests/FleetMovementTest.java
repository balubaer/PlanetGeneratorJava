package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.berndniklas.PlanetGenerator.Fleet;
import de.berndniklas.PlanetGenerator.FleetMovement;
import de.berndniklas.PlanetGenerator.Planet;
import de.berndniklas.PlanetGenerator.Player;

public class FleetMovementTest {

	Fleet fleet;
	Planet planet;
	Player player;

	@Before
	public void setUp() throws Exception {
		fleet = new Fleet();
		planet = new Planet();
		player = new Player();

		player.name = "ZAPHOD";
		fleet.player = player;
		fleet.number = 25;
		planet.number = 46;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {

		FleetMovement fleetMove = new FleetMovement();

		fleetMove.fleet = fleet;
		fleetMove.toPlanet = planet;
		assertEquals("FleetMovement.toString is not correct", fleetMove.toString(), "F25[ZAPHOD]-->W46");
	}

}
