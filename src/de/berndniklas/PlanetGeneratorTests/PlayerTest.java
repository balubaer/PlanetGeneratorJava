package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.berndniklas.PlanetGenerator.Fleet;
import de.berndniklas.PlanetGenerator.Player;

public class PlayerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testName() {
		Player aPlayer = new Player();
		aPlayer.name = "ZAPHOD";
		
		assertEquals("Player.toString is not correct", aPlayer.toString(), "[ZAPHOD]");
		assertEquals("Player.name is not correct", aPlayer.name, "ZAPHOD");
	}

	@Test
	public void testIsPlayerInFleetsWithPlayer() {
		Player aPlayer = new Player();
		aPlayer.name = "ZAPHOD";
		ArrayList<Fleet> fleets = new ArrayList<Fleet>();
		Fleet fleet = new Fleet();
        fleet.number = 1;

		fleets.add(fleet);

		fleet = new Fleet();
		fleet.number = 2;
		fleet.player = aPlayer;
		fleets.add(fleet);
		
		assertTrue("### Player.isPlayerInFleetsWithPlayer liefert falsches Ergebnis ###", Player.isPlayerInFleetsWithPlayer(aPlayer, fleets));
		
		aPlayer = new Player();
		aPlayer.name = "Test Player";
		assertFalse("### Player.isPlayerInFleetsWithPlayer liefert falsches Ergebnis ###", Player.isPlayerInFleetsWithPlayer(aPlayer, fleets));
	}
}
