package de.berndniklas.PlanetGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.PropertyListParser;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

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

			Collection<Player> values = allPlayerDict.values();
			for (Iterator<Player> iterator = values.iterator(); iterator
					.hasNext();) {
				Player player = iterator.next();
				StringBuilder outPutString = new StringBuilder(10000);
				outPutString.append("Infos zu Spieler: ");
				outPutString.append(player.name);
				outPutString.append(" Runde: ");
				outPutString.append(turnNumber.intValue());
				outPutString.append(" \n\n");

				for (Planet planet : planets) {
					if (Player.isPlanetOutPutForPlayer(player, planet)) {
						outPutString.append(planet.toString());
						outPutString.append("\n\n");
					}
				}
				File playerFile = new File(turnPath, player.name + ".out");
				Files.write(outPutString, playerFile, Charsets.UTF_8);

			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
