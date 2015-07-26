package de.berndniklas.PlanetGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
				
				if (commandFilePath.canRead()) {
					String commandsString = Files.toString(commandFilePath, Charsets.UTF_8);

					if (commandsString != null) {
						commandFactory.setCommandStringsWithLongString(player.name, commandsString);
					} else {
						System.out.println("Fehler: CommandsString konnte nicht erzeugt werden f�r Spieler " + player.name+ "!!!");
					}
				}

			}

			commandFactory.executeCommands();

			FinalPhaseCoreGame finalPhase = new FinalPhaseCoreGame(planets, allPlayerDict);

			finalPhase.doFinal();

			values = allPlayerDict.values();
			for (Iterator<Player> iterator = values.iterator(); iterator.hasNext();) {
				Player player = iterator.next();
				StringBuilder outPutString = new StringBuilder(10000);
				try {
					File playerXmlFile = new File(turnPath, player.name + ".xml");

					DocumentBuilderFactory dbFactory =
							DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = 
							dbFactory.newDocumentBuilder();
					Document doc = dBuilder.newDocument();
					// root element
					Element rootElement = doc.createElement("report");
					doc.appendChild(rootElement);
					Attr attr = doc.createAttribute("changeSeq");
					attr.setValue("1");
					rootElement.setAttributeNode(attr);

					attr = doc.createAttribute("endCondition");
					attr.setValue("score:None");
					rootElement.setAttributeNode(attr);

					attr = doc.createAttribute("gameId");
					attr.setValue(playName);
					rootElement.setAttributeNode(attr);

					attr = doc.createAttribute("parametersName");
					attr.setValue("Core");
					rootElement.setAttributeNode(attr);

					attr = doc.createAttribute("parametersToken");
					attr.setValue("core");
					rootElement.setAttributeNode(attr);

					attr = doc.createAttribute("rsw");
					attr.setValue("False");
					rootElement.setAttributeNode(attr);

					attr = doc.createAttribute("turnNumber");
					attr.setValue(Integer.toString(turn +1));
					rootElement.setAttributeNode(attr);
					//  Player element
					Element childElementPlayer = player.getXMLElement(doc);

					attr = doc.createAttribute("lastInvolvedTurn");
					attr.setValue(Integer.toString(turn + 1));
					childElementPlayer.setAttributeNode(attr);

					attr = doc.createAttribute("lastPlayedTurn");
					attr.setValue(Integer.toString(turn));
					childElementPlayer.setAttributeNode(attr);

					attr = doc.createAttribute("type");
					attr.setValue("Core");
					childElementPlayer.setAttributeNode(attr);

					attr = doc.createAttribute("typeKey");
					attr.setValue("core");
					childElementPlayer.setAttributeNode(attr);

					attr = doc.createAttribute("score");
					attr.setValue(Integer.toString(player.points));
					childElementPlayer.setAttributeNode(attr);

					rootElement.appendChild(childElementPlayer);

					outPutString.append("Infos zu Spieler: ");
					outPutString.append(player.name);
					outPutString.append(" Runde: ");
					outPutString.append(turn);
					outPutString.append(" \n\n");
					//TODO: OutputPlyerStatistic (Punkte Anzahl Planeten Flotten Schiffe D-Schiffe

					for (Planet planet : planets) {
						if (Player.isPlanetOutPutForPlayer(player, planet)) {
							Element childElementPlanet = planet.getXMLElementForPlayer(doc, player);
							rootElement.appendChild(childElementPlanet);
							outPutString.append(planet.toString());
							outPutString.append("\n\n");
						}
					}
					File playerFile = new File(turnPath, player.name + ".out");
					Files.write(outPutString, playerFile, Charsets.UTF_8);
					TransformerFactory transformerFactory =
							TransformerFactory.newInstance();
					Transformer transformer =
							transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result =
							new StreamResult(playerXmlFile);
					transformer.transform(source, result);
					// Output to console for testing
					StreamResult consoleResult =
							new StreamResult(System.out);
					transformer.transform(source, consoleResult);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			persManager.planetArray = planets;
			persManager.writePlanetPListWithPlanetArray(planetPlistFilePath.toString());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
