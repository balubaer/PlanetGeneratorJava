package de.berndniklas.PlanetGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.PropertyListParser;

public class OutPutLists {
	public static void main(String[] args)
	{
		try {
			String plistPath = PlanetGenerator.getResourcePathForFile("OutPutLists.plist");
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

			/*
					//output Result
					for (playerName, player) in allPlayerDict {
					    var outPutString = "Infos zu Spieler: \(playerName) Runde: \(turnNumber)\n\n"
					    for planet in planets {
					        if Player.isPlanetOutPutForPlayer(player, planet: planet) {
					            outPutString += "\(planet.description)\n\n"
					        }
					    }
					    var outPutFilePath = turnPath.stringByAppendingPathComponent("\(playerName).out")
					    outPutString.writeToFile(outPutFilePath, atomically: true, encoding: NSUTF8StringEncoding, error: nil)
					} */
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
