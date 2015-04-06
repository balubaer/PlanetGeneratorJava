package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.berndniklas.PlanetGenerator.DistanceLevel;
import de.berndniklas.PlanetGenerator.PersistenceManager;
import de.berndniklas.PlanetGenerator.Planet;
import de.berndniklas.PlanetGenerator.PlanetGenerator;
import de.berndniklas.PlanetGenerator.Player;

public class DistanceLevelTest {
	ArrayList<Planet> planetArray;
	HashMap<String, Player> allPlayerDict;

//    var allPlayerDict: [String: Player]?

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String plistPath = PlanetGenerator.getResourcePathForFile("planets.plist");
		PersistenceManager persManager = new PersistenceManager();

		planetArray = persManager.readPlanetPListWithPath(plistPath);
		allPlayerDict = persManager.allPlayerDict;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
if (planetArray != null && allPlayerDict != null) {
            
            Planet planet1 = Planet.planetWithNumber(planetArray, 1);
            
            if (planet1 != null) {
            	DistanceLevel disLevel = new DistanceLevel(planet1, 1);
                
            	assertTrue("### nextLevelPlanets Anzahl falsch ###",disLevel.nextLevelPlanets.size() == 1, );
                if (disLevel.nextLevelPlanets.count == 1) {
                    XCTAssertTrue(disLevel.nextLevelPlanets[0].number == 2, "### Es ist nicht Planet 2 ###")
                }
                disLevel.goNextLevel()
                XCTAssertTrue(disLevel.nextLevelPlanets.count == 1, "### nextLevelPlanets Anzahl falsch ###")
                if (disLevel.nextLevelPlanets.count == 1) {
                    XCTAssertTrue(disLevel.nextLevelPlanets[0].number == 3, "### Es ist nicht Planet 3 ###")
                }
            } else {
                XCTFail("### TestDistanceLevel.testDistanceLevel Planet 1 nicht vorhanden ###")
            }
            
        } else {
            XCTFail("### TestDistanceLevel.testDistanceLevel planetArray and allPlayerDict are nil ###")
        }

		fail("Not yet implemented");
	}

}
