package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.berndniklas.PlanetGenerator.Fleet;
import de.berndniklas.PlanetGenerator.FleetMovement;
import de.berndniklas.PlanetGenerator.Player;

public class FleetTest {

	@Test
	public void testName() {
		Fleet fleet = new Fleet();
		assertEquals("Fleet.name is not correct", fleet.name(), "F0[---]");


		Player player = new Player();
		player.name = "ZAPHOD";
		fleet.player = player;
		fleet.number = 25;
		assertEquals("Fleet.name is not correct", fleet.name(), "F25[ZAPHOD]");
	}

	@Test
	public void testToString() {
        Fleet fleet = new Fleet();
		assertEquals("Fleet.name is not correct", fleet.toString(), "F0[---] = 0");

		Player player = new Player();
        player.name = "ZAPHOD";
        fleet.player = player;
        fleet.ships = 99;
        fleet.number = 25;
         
        assertEquals("Fleet.toString is not correct", fleet.toString(), "F25[ZAPHOD] = 99");

        FleetMovement fleetMovement = new FleetMovement();
        fleet.fleetMovements.add(fleetMovement);
        fleet.cargo = 7;
        assertEquals("Fleet.toString is not correct", fleet.toString(), "F25[ZAPHOD] = 99 (bewegt,Fracht=7)");
        fleet.fleetMovements.clear();
        fleet.ambush = true;
        fleet.cargo = 0;
        assertEquals("Fleet.toString is not correct", fleet.toString(), "F25[ZAPHOD] = 99 (Ambush)");
	}
         
}
