package de.berndniklas.PlanetGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

public class PersistenceManager {
	ArrayList<Planet> planetArray;
	HashMap<String, Player> allPlayerDict = new HashMap<String, Player>();

	public PersistenceManager(ArrayList<Planet> aPlanetArray) {
		planetArray = aPlanetArray;
	}

	public void writePlanetPListWithPlanetArray(String aPath) {
		NSDictionary dictForPList = new NSDictionary();
		ArrayList<NSDictionary> planetArrayForPList = new ArrayList<NSDictionary>();
		NSDictionary portDictForPList = new NSDictionary();
		NSDictionary fleetDictForPList = new NSDictionary();
		NSDictionary playerDictForPList = new NSDictionary();


		if (planetArray != null) {
			for (Planet planet : planetArray) {
				NSDictionary planetDict = new NSDictionary();

				planetDict.put("number", planet.number);
				planetDict.put("name", planet.name());
				if (planet.player != null) {
					planetDict.put("player", planet.player.name);
					if (planet.player.role == null) {
						planet.player.role = new Role();
					}	
					NSDictionary playerDict = new NSDictionary();
					playerDict.put("points", planet.player.points);
					playerDict.put("role", planet.player.role.name);

					playerDictForPList.put(planet.player.name, playerDict);
				}
				ArrayList<NSNumber> fleetArray = new ArrayList<NSNumber>();
				ArrayList<Fleet> fleets = planet.fleets;
				for (Fleet fleet : fleets) {
					NSDictionary fleetDict = new NSDictionary();
					NSNumber number = new NSNumber(fleet.number);
					fleetArray.add(number);
					fleetDict.put("number", fleet.number);
					fleetDict.put("ships", fleet.ships);
					if (fleet.player != null) {
						fleetDict.put("player", fleet.player.name);
					}
					fleetDict.put("cargo", fleet.cargo);
					fleetDict.put("moved", NSNumber.wrap(fleet.moved()));
					fleetDictForPList.put(String.valueOf(fleet.number), fleetDict);
				}
				planetDict.put("fleets", fleetArray);
				if (planet.port != null && planet.port.planet != null) {
					ArrayList<NSNumber> aPlanetsArray = new ArrayList<NSNumber>();
					ArrayList<Planet> planets = planet.port.planets;

					for (Planet planetFromPort : planets) {
						NSNumber planetNumber = new NSNumber(planetFromPort.number);
						aPlanetsArray.add(planetNumber);

					}
					portDictForPList.put(String.valueOf(planet.port.planet.number), aPlanetsArray);
					//  portDictForPList[String(planet.port!.planet!.number)] = "Hallo"
				}
				NSNumber industriy = new NSNumber(planet.industry);
				planetDict.put("industry", industriy);
				NSNumber metal = new NSNumber(planet.metal);
				planetDict.put("metal", metal);
				NSNumber mines = new NSNumber(planet.mines);
				planetDict.put("mines", mines);
				NSNumber population = new NSNumber(planet.population);
				planetDict.put("population", population);
				NSNumber limit = new NSNumber(planet.limit);
				planetDict.put("limit", limit);
				NSNumber round = new NSNumber(planet.round);
				planetDict.put("round", round);
				NSNumber iShips = new NSNumber(planet.iShips);
				planetDict.put("iShips", iShips);
				NSNumber pShips = new NSNumber(planet.pShips);
				planetDict.put("pShips", pShips);
				NSNumber dShips = new NSNumber(planet.dShips);
				planetDict.put("dShips", dShips);
				planetArrayForPList.add(planetDict);

			}
		}
		dictForPList.put("planets", planetArrayForPList);
		dictForPList.put("player", playerDictForPList);
		dictForPList.put("ports", portDictForPList);
		dictForPList.put("fleets", fleetDictForPList);
		try {
			PropertyListParser.saveAsXML(dictForPList, new File(aPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Planet> readPlanetPListWithPath(String aPath) {
		File file = new File(aPath);
		NSDictionary dictFormPList;
		try {
			dictFormPList = (NSDictionary)PropertyListParser.parse(file);
			NSArray planetArrayFormPList = (NSArray) dictFormPList.objectForKey("planets");
			NSDictionary portDictFormPList = (NSDictionary) dictFormPList.objectForKey("ports");
			NSDictionary fleetDictFormPList = (NSDictionary) dictFormPList.objectForKey("fleets");
			NSDictionary playerDictFormPList = (NSDictionary) dictFormPList.objectForKey("player");
			ArrayList<Planet> planetArray = new ArrayList<Planet>();

			if (planetArrayFormPList != null) {
				int count = planetArrayFormPList.count();
				for (int i = 0; i < count; i++) {
					NSDictionary planetDict = (NSDictionary) planetArrayFormPList.objectAtIndex(i);
					Planet planet = new Planet();

					NSNumber intValue = (NSNumber) planetDict.get("number");

					planet.number = intValue.intValue();

					String playerName = ((NSString) planetDict.get("player")).toString();

					if (playerName != null) {
						Player player = allPlayerDict.get(playerName);

						if (player == null) {
							NSDictionary playerDict = (NSDictionary) playerDictFormPList.get(playerName);
							Player newPlayer = new Player();
							newPlayer.name = playerName;
							if (playerDict != null) {
								intValue = (NSNumber) playerDict.get("points");
								newPlayer.points = intValue.intValue();
								Role role = new Role();
								NSString roleName = (NSString) playerDict.get("role");
								if (roleName != null) {
									role.name = roleName.toString();
								}
								newPlayer.role = role;
							}
							allPlayerDict.put(playerName, newPlayer);
							player = newPlayer;
						}
						planet.player = player;
					}

					NSArray fleetArray = (NSArray) planetDict.get("fleets");

					if (fleetArray != null) {
						int countFleetArray = fleetArray.count();
						for (int j = 0; j < countFleetArray; j++) {
							NSNumber aFleetNumber = (NSNumber) fleetArray.objectAtIndex(j);
							//Create a Fleet
							Fleet fleet = new Fleet();
							fleet.number = aFleetNumber.intValue();
							planet.fleets.add(fleet);
						}
					}

					intValue = (NSNumber) planetDict.get("industry");
					planet.industry = intValue.intValue();

					intValue = (NSNumber) planetDict.get("metal");
					planet.metal = intValue.intValue();

					intValue = (NSNumber) planetDict.get("mines");
					planet.mines = intValue.intValue();

					intValue = (NSNumber) planetDict.get("population");
					planet.population = intValue.intValue();

					intValue = (NSNumber) planetDict.get("limit");
					planet.limit = intValue.intValue();

					intValue = (NSNumber) planetDict.get("round");
					planet.round = intValue.intValue();

					intValue = (NSNumber) planetDict.get("iShips");
					planet.iShips = intValue.intValue();

					intValue = (NSNumber) planetDict.get("pShips");
					planet.pShips = intValue.intValue();

					intValue = (NSNumber) planetDict.get("dShips");
					planet.dShips = intValue.intValue();
					planetArray.add(planet);
				}
				//Set Ports to Planet and complete the Fleets
				for (Planet planet : planetArray) {
					//Create a Port
					Port port = new Port();
					port.planet = planet;
					planet.port = port;

					//Get Planets Array from Plist-PortArray
					NSArray intArrayWithPlanetNumbers = (NSArray) portDictFormPList.get(String.valueOf(planet.number));

					int countIntArrayWithPlanetNumbers = intArrayWithPlanetNumbers.count();
					for (int j = 0; j < countIntArrayWithPlanetNumbers; j++) {
						NSNumber planetNumber = (NSNumber) intArrayWithPlanetNumbers.objectAtIndex(j);
						Planet aPlanet = Planet.planetWithNumber(planetArray, planetNumber.intValue());
						if (aPlanet != null) {
							port.planets.add(aPlanet);
						}
					}

					//Complete the Fleets
					for (Fleet fleet : planet.fleets) {
						NSDictionary fleetFromPlist = (NSDictionary) fleetDictFormPList.get(String.valueOf(fleet.number));
						if (fleetFromPlist != null) {
							NSNumber shipNumber = (NSNumber) fleetFromPlist.get("ships");
							fleet.ships = shipNumber.intValue();

							String playerName = ((NSString) fleetFromPlist.get("player")).toString();

							if (playerName != null) {
								Player player = allPlayerDict.get(playerName);
								if (player != null) {
									fleet.player = player;
								}
							}
							NSNumber cargoNumber = (NSNumber) fleetFromPlist.get("cargo");
							fleet.cargo = cargoNumber.intValue();
						}
					}
				} 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return planetArray;
	}
}

