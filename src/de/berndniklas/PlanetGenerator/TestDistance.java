package de.berndniklas.PlanetGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.PropertyListParser;

public class TestDistance {

	public static void main(String[] args) {
		try {
			String plistPath = PlanetGenerator.getResourcePathForFile("TestDistance.plist");
			File file = new File(plistPath);
			NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(file);
			String playPath = rootDict.objectForKey("playPath").toString();
			String playName = rootDict.objectForKey("playName").toString();
			NSNumber turnNumber = (NSNumber) rootDict.objectForKey("turn");

			File turnPath = new File(playPath, playName);
			turnPath = new File(turnPath.toString(), "Turn" + turnNumber.toString());


			File planetPlistFilePath = new File(turnPath.toString(), "Turn" + turnNumber.toString() + ".plist");



			if (planetPlistFilePath.exists() == false) {
				System.out.println("Fehler: Planeten File " + planetPlistFilePath.toString() + " ist nicht vorhanden!!!");
			}

			PersistenceManager persManager = new PersistenceManager();
			ArrayList<Planet> planets = persManager.readPlanetPListWithPath(planetPlistFilePath.toString());

			HashMap<String, Player> allPlayerDict = persManager.allPlayerDict;

			Collection<Player> values = allPlayerDict.values();
			
			for (Iterator<Player> iterator = values.iterator(); iterator.hasNext();) {
				Planet aHomePlanet = null;

				Player player = iterator.next();
				for (Planet planet : planets) {
					if (planet.player != null && player.name.equals(planet.player.name)){
						aHomePlanet = planet;
						break;
					}
				}
				if (aHomePlanet != null) {
					DistanceLevel disLevel = new DistanceLevel(aHomePlanet, 1);
					boolean foundDistanceLevel = false;
						        
					while (foundDistanceLevel != true) {
						if (TestDistance.testPlayerInNextLevelPlanets(disLevel.nextLevelPlanets, player) == false) {
							foundDistanceLevel = true;
						} else {
							disLevel.goNextLevel();
						}
					}
					if (aHomePlanet.player != null) {
						System.out.println("Spieler " + aHomePlanet.player.name + " distanceLevel: " + disLevel.distanceLevel);
						System.out.println("nextLevelPlanets " + disLevel.nextLevelPlanets.size());
					}
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static boolean testPlayerInNextLevelPlanets(ArrayList<Planet> nextLevelPlanets, Player aPlayer) {
		boolean result = true;

		if (nextLevelPlanets.size() > 0) {
			for (Planet planet : nextLevelPlanets) {
				if (planet.player != null) {
					if (aPlayer != planet.player) {
						result = false;
						break;
					}
				}
			} 

		} else {
			result = false;
		}
		return result;
	}

}
