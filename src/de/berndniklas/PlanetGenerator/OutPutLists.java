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

import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.PropertyListParser;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
			
				try {
					File playerXmlFile = new File(turnPath, player.name + ".xml");

					int turn = turnNumber.intValue() + 1;
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
					attr.setValue(Integer.toString(turn));
					rootElement.setAttributeNode(attr);


					//  Player element
					Element childElementPlayer = player.getXMLElement(doc);
					// childElementPlayer.appendChild(childElementPlayer);

					attr = doc.createAttribute("lastInvolvedTurn");
					attr.setValue("1");
					childElementPlayer.setAttributeNode(attr);

					attr = doc.createAttribute("lastPlayedTurn");
					attr.setValue("1");
					childElementPlayer.setAttributeNode(attr);

					attr = doc.createAttribute("type");
					attr.setValue("Core");
					childElementPlayer.setAttributeNode(attr);

					attr = doc.createAttribute("typeKey");
					attr.setValue("core");
					childElementPlayer.setAttributeNode(attr);

					rootElement.appendChild(childElementPlayer);

					outPutString.append("Infos zu Spieler: ");
					outPutString.append(player.name);
					outPutString.append(" Runde: ");
					outPutString.append(turnNumber.intValue());
					outPutString.append(" \n\n");

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

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
