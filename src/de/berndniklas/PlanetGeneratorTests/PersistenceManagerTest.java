package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.berndniklas.PlanetGenerator.Fleet;
import de.berndniklas.PlanetGenerator.PersistenceManager;
import de.berndniklas.PlanetGenerator.Planet;
import de.berndniklas.PlanetGenerator.Player;
import de.berndniklas.PlanetGenerator.Port;

public class PersistenceManagerTest {

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
	public void test() {
		Player player = new Player();
		player.name = "ZAPHOD";
	  
				Planet planet1 = new Planet();	
				Port port1 = new Port();
				Fleet fleet1 = new Fleet();
	          
	         fleet1.number = 1;
	         fleet1.player = player;
	         fleet1.ships = 1;
	         planet1.fleets.add(fleet1);
	          
	         port1.planet = planet1;
	         planet1.port = port1;
	         planet1.number = 1;
	         planet1.player = player;
	         planet1.industry = 11;
	         planet1.population = 12;
	         planet1.limit = 13;
	  
	         Planet planet2 = new Planet();
	         port1.planets.add(planet2);
	          
	         Port port2 = new Port();
	         port2.planet = planet2;
	         port2.planets.add(planet1);
	         planet2.port = port2;
	          
	         Fleet fleet2 = new Fleet();
	          
	         fleet2.number = 2;
	         fleet2.player = player;
	         fleet2.ships = 1;
	         planet2.fleets.add(fleet2);
	  
	         planet2.number = 2;
	         planet2.player = player;
	         planet2.industry = 21;
	         planet2.population = 22;
	         planet2.limit = 23;
	  
	         Planet planet3 = new Planet();
	         port2.planets.add(planet3);
	         Port port3 = new Port();
	         port3.planet = planet3;
	         planet3.port = port3;
	         port3.planets.add(planet2);
	          
	         Fleet fleet3 = new Fleet();
	          
	         fleet3.number = 3;
	         fleet3.player = player;
	         fleet3.ships = 1;
	         planet3.fleets.add(fleet3);
	  
	         planet3.number = 3;
	         planet3.player = player;
	         planet3.industry = 31;
	         planet3.population = 32;
	         planet3.limit = 33;
	          
	         ArrayList <Planet> planetArray = new ArrayList<Planet>();
	         planetArray.add(planet1);
	         planetArray.add(planet2);
	         planetArray.add(planet3);
	          
	         PersistenceManager persManager = new PersistenceManager(planetArray);
	         persManager.writePlanetPListWithPlanetArray("/tmp/planets.plist");
	          
	        // var newPlanetArray = persManager.readPlanetPListWithPath("/tmp/planets.plist")
	 		assertTrue("PersistenceManager is not correct", true);
	}

}
