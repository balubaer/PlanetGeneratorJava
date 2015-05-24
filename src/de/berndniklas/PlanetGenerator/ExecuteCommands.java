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

public class ExecuteCommands {

	public static void main(String[] args)
	{
		try {
			String plistPath = PlanetGenerator.getResourcePathForFile("ExecuteCommands.plist");
			File file = new File(plistPath);
			NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(file);
			String playPath = rootDict.objectForKey("playPath").toString();
			String playName = rootDict.objectForKey("playName").toString();
			NSNumber coreGameNumber = (NSNumber) rootDict.objectForKey("coreGame");
			boolean coreGame = false;

			if (coreGameNumber != null) {
				coreGame = coreGameNumber.boolValue();
			}

			NSNumber turnNumber = (NSNumber) rootDict.objectForKey("turn");
			int turn = turnNumber.intValue();
			int turnBefore = turn - 1;	

			File turnPath = new File(playPath, playName);
			File turnBeforePath = new File(turnPath.toString(),"Turn" + turnBefore);

			turnPath = new File(turnPath.toString(), "Turn" + turnNumber.toString());


			File planetPlistFileBeforePath = new File(turnBeforePath, "Turn" + turnBefore +  ".plist");
			File planetPlistFilePath = new File(turnPath.toString(), "Turn" + turnNumber.toString() + ".plist");

			if (turnPath.exists() == false) {
				turnPath.mkdir();
			}

			PersistenceManager persManager = new PersistenceManager();

			ArrayList<Planet> planets = persManager.readPlanetPListWithPath(planetPlistFileBeforePath.toString());


			HashMap<String, Player> allPlayerDict = persManager.allPlayerDict;

			CommandFactory commandFactory = new CommandFactory(planets, allPlayerDict);

			commandFactory.coreGame = coreGame;

			//Execute Commands Result
			Collection<Player> values = allPlayerDict.values();
			for (Iterator<Player> iterator = values.iterator(); iterator.hasNext();) {
				Player player = iterator.next();
				File commandFilePath = new File(turnPath, player.name + ".txt");

				String commandsString = Files.toString(commandFilePath, Charsets.UTF_8);

				if (commandsString != null) {
					commandFactory.setCommandStringsWithLongString(player.name, commandsString);
				} else {
					System.out.println("Fehler: CommandsString konnte nicht erzeugt werden für Spieler " + player.name+ "!!!");
				}

			}

			commandFactory.executeCommands();

			FinalPhaseCoreGame finalPhase = new FinalPhaseCoreGame(planets, allPlayerDict);

			finalPhase.doFinal();

			values = allPlayerDict.values();
			for (Iterator<Player> iterator = values.iterator(); iterator.hasNext();) {
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

			persManager.planetArray = planets;
			persManager.writePlanetPListWithPlanetArray(planetPlistFilePath.toString());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
