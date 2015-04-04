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
	HashMap<Object, Object> allPlayerDict = new HashMap<Object, Object>();

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
					/* if (planet.player.role == null) {
			                        planet.player.role = new Role();
			                    }*/
					NSDictionary playerDict = new NSDictionary();
					playerDict.put("points", planet.player.points);
					playerDict.put("role", planet.player.points);

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
	/*
			    func readPlanetPListWithPath(aPath: String) -> Array <Planet> {
			        // if let dict = NSDictionary(contentsOfFile: path) as? Dictionary<String, AnyObject>
			        var dictFormPList = NSDictionary(contentsOfFile: aPath) as? Dictionary<String, AnyObject>
			        var planetArrayFormPList:Array? = (dictFormPList!["planets"] as NSArray)
			        var portDictFormPList = (dictFormPList!["ports"] as? Dictionary<String, AnyObject>)
			        var fleetDictFormPList = dictFormPList?["fleets"] as? Dictionary<String, AnyObject>
			       // var playerDictFormPList:AnyObject? = dictFormPList?.objectForKey("player")
			        var playerDictFormPList = dictFormPList?["player"] as? Dictionary<String, AnyObject>

			        var planetArray: Array <Planet> = Array()
			        var portConnections: [Int: Array<Int>] = Dictionary()

			        if planetArrayFormPList != nil {
			            for planetDict in planetArrayFormPList! {
			                var planet = Planet()

			                var intValue = planetDict["number"]

			                planet.number = Int((intValue as NSNumber))

			                var playerName:String? = planetDict["player"] as String?

			                if playerName != nil {
			                    var player: Player? = allPlayerDict[playerName!]

			                    if player == nil {
			                        var playerDict = playerDictFormPList![playerName!] as? Dictionary<String, AnyObject>
			                        player = Player()
			                        player!.name = playerName!
			                        if playerDict != nil {
			                            intValue = playerDict!["points"]
			                            player!.points = Int((intValue as NSNumber))
			                            var role = Role()
			                            var roleName = (playerDict!["role"] as? String)
			                            if roleName != nil {
			                                role.name = roleName!
			                            }
			                            player!.role = role
			                        }
			                        allPlayerDict[playerName!] = player!
			                    }
			                    planet.player = player!
			                }

			                var fleetArray: Array<Int>? = planetDict["fleets"] as? Array<Int>

			                if fleetArray != nil {
			                    for aFleetNumber in fleetArray! {
			                        //Create a Fleet
			                        var fleet = Fleet()
			                        fleet.number = aFleetNumber
			                        planet.fleets.append(fleet)
			                    }
			                }

			                intValue = planetDict["industry"]
			                planet.industry = Int((intValue as NSNumber))

			                intValue = planetDict["metal"]
			                planet.metal = Int((intValue as NSNumber))

			                intValue = planetDict["mines"]
			                planet.mines = Int((intValue as NSNumber))

			                intValue = planetDict["population"]
			                planet.population = Int((intValue as NSNumber))

			                intValue = planetDict["limit"]
			                planet.limit = Int((intValue as NSNumber))

			                intValue = planetDict["round"]
			                planet.round = Int((intValue as NSNumber))

			                intValue = planetDict["iShips"]
			                planet.iShips = Int((intValue as NSNumber))

			                intValue = planetDict["pShips"]
			                planet.pShips = Int((intValue as NSNumber))

			                intValue = planetDict["dShips"]

			                var aNumber = intValue as? NSNumber
			                if aNumber != nil {
			                    planet.dShips = Int(aNumber!)
			                }


			                planetArray.append(planet)
			            }
			            //Set Ports to Planet and complete the Fleets
			            for planet in planetArray {
			                //Create a Port
			                var port = Port()
			                port.planet = planet
			                planet.port = port

			                //Get Planets Array from Plist-PortArray
			                var intArrayWithPlanetNumbers = portDictFormPList![String(planet.number)] as Array <NSNumber>

			                for planetNumber in intArrayWithPlanetNumbers {
			                    var aPlanet: Planet? = planetWithNumber(planetArray, Int(planetNumber))

			                    if aPlanet != nil {
			                        port.planets.append(aPlanet!)
			                    }
			                }

			                //Complete the Fleets
			                for fleet in planet.fleets {
			                    var fleetFromPlist = fleetDictFormPList![String(fleet.number)] as? Dictionary <String, AnyObject>
			                    if fleetFromPlist != nil {
			                        fleet.ships = Int(fleetFromPlist!["ships"] as NSNumber)

			                        var playerName:String? = fleetFromPlist!["player"] as String?

			                        if playerName != nil {
			                            var player = allPlayerDict[playerName!]
			                            if player != nil {
			                                fleet.player = player
			                            }
			                        }
			                        fleet.cargo = Int(fleetFromPlist!["cargo"] as NSNumber)
			                    }
			                }
			            }
			        }
			        return planetArray
			    }
	 */
}
