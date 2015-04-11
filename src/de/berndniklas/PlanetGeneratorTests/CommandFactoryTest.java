package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import de.berndniklas.PlanetGenerator.CommandFactory;
import de.berndniklas.PlanetGenerator.Fleet;
import de.berndniklas.PlanetGenerator.FleetAndPlanetDTO;
import de.berndniklas.PlanetGenerator.PersistenceManager;
import de.berndniklas.PlanetGenerator.Planet;
import de.berndniklas.PlanetGenerator.PlanetGenerator;
import de.berndniklas.PlanetGenerator.Player;

public class CommandFactoryTest {

	String commandsString;
	ArrayList<Planet> planetArray;
	HashMap<String, Player> allPlayerDict;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String plistPath = PlanetGenerator.getResourcePathForFile("planets.plist");
		String commandFilePathString = PlanetGenerator.getResourcePathForFile("commands.txt");
		PersistenceManager persManager = new PersistenceManager();

		planetArray = persManager.readPlanetPListWithPath(plistPath);
		allPlayerDict = persManager.allPlayerDict;
		
		File commandFilePath = new File(commandFilePathString);

		commandsString = Files.toString(commandFilePath, Charsets.UTF_8);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		if ((planetArray != null) && (allPlayerDict != null)) {
			CommandFactory commandFactory = new CommandFactory(planetArray, allPlayerDict);
            if (commandsString != null) {
                
                //Test Flotte 1
    			FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planetArray, 1);

                if ((aFleetAndHomePlanet.planet != null) && (aFleetAndHomePlanet.fleet != null)) {
    				assertTrue("### Flotte 1 Anzahl Schiffe falsch ###", aFleetAndHomePlanet.fleet.ships == 0);
    				assertTrue("### Planet 1 Anzahl Metalle falsch ###", aFleetAndHomePlanet.planet.metal == 1);
                } else {
            		fail("### Flotte 1 nicht gefunden  ###");
                }
                
                commandFactory.setCommandStringsWithLongString("ZAPHOD", commandsString);
                commandFactory.coreGame = true;

                commandFactory.executeCommands();
            }
		}
	}

}
