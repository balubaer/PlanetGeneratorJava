package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Planet implements Comparable<Planet> {
	public int number;
	public Port port;
	public Player player;
	public ArrayList<Fleet> fleets = new ArrayList<Fleet>();
	public ArrayList<FleetMovement> fleetMovements = new ArrayList<FleetMovement>();
	boolean ambushOff;
	public int industry;
	int usedIndustry;
	/*		    var effectiveIndustry: Int {
			        var result: Int = 0
			        if industry <= metal {
			            result = industry
			        } else {
			            result = metal
			        }
			        return result
			    }*/
	public int metal;
	int mines;
	public int population;
	public int limit;
	int round;
	int iShips;
	int pShips;
	public int dShips;
	boolean dShipsFired;
	boolean dShipsAmbush;

	ArrayList<Fleet> hitAmbuschFleets = new ArrayList<Fleet>();
	

	ArrayList<Player> hitAmbuschPlayers = new ArrayList<Player>();

	int hitedShotsDShips;
	public Fleet dShipsFiredFleet;

	//TODO: niklas Kunstwerke ... V70:Plastik Mondstein
	public String name() {
		StringBuilder sb = new StringBuilder(50);
		sb.append("W");
		sb.append(number);
		return sb.toString();
	}

	public String fireAmbuschFleets() {
		StringBuilder sb = new StringBuilder(50);
		sb.append("Ambush:");

        for (Fleet hitAmbushfleet : hitAmbuschFleets) {
    		sb.append("F");
    		sb.append(hitAmbushfleet.number);
    		sb.append(",");
        }
        sb.setLength(sb.length() - 1);

        return sb.toString();
    }
	
	public String toString() {
		StringBuilder sb = new StringBuilder(1000);

		if (port != null) {
			sb.append(port.toString());
		} else {
			sb.append(this.name());
		}
		if (player != null) {
			sb.append(" ");
			sb.append(player.toString());
		}

		String resouceString = this.createResourceString();

		if (resouceString.length() != 0) {
			sb.append(" ");
			sb.append(resouceString);
		}

		if (fleets.size() > 0) {
			for (Fleet fleet : fleets) {
				sb.append("\n   ");
				sb.append(fleet.toString());
			} 
		}

		int fleetMovementsCount = fleetMovements.size();

		if (fleetMovementsCount > 0) {
			int counter = 0;
			sb.append("\n   (");
			for (FleetMovement fleetMovement : fleetMovements) {
				sb.append(fleetMovement.toString());
				counter++;
				if (counter < fleetMovementsCount) {
					sb.append(" ");
				}
			} 
			sb.append(")");
		}

		return sb.toString();
	}

	public void setNumber(int aNumber) {
		this.number = aNumber;
	}

	public String createResourceString() {
		String result = "";
		StringBuilder sb = new StringBuilder(1000);
		ArrayList<String> resourceArray = new ArrayList<String>();
		if (ambushOff == true) {
			resourceArray.add("Ambush 'Aus' f�r diese Runde!!!");
		}
		if (industry != 0) {
			sb.append("Industrie=");
			sb.append(industry);
			resourceArray.add(sb.toString());
			sb.setLength(0);
		}
		if (metal != 0) {
			// resourceArray.add("Metall=\(metal)")
		}
		if (mines != 0) {
			//resourceArray.append("Minen=\(mines)")
		}
		if (population != 0) {
			//resourceArray.append("Bevoelkerung=\(population)")
		}
		if (limit != 0) {
			//resourceArray.append("Limit=\(limit)")
		}
		if (round != 0) {
			//resourceArray.append("Runden=\(round)")
		}
		if (iShips != 0) {
			//resourceArray.append("I-Schiffe=\(iShips)")
		}
		if (pShips != 0) {
			//resourceArray.append("P-Schiffe=\(pShips)")
		}
		if (number == 191) {
			System.out.println("Hallo");
		}
		if (dShips != 0) {
			if (dShipsAmbush) {
				sb.append("D-Schiffe=");
				sb.append(dShips);
				sb.append(" (Ambusch: {");
                if (hitAmbuschFleets.size() > 0) {
                    int counter = 0;
                    
                    for (Fleet fleet : hitAmbuschFleets) {
        				sb.append(fleet.name());
                        counter++;
                        if (counter < hitAmbuschFleets.size()) {
            				sb.append(", ");
                        }
                    }
    				sb.append("}");
                }
				sb.append(")");
				resourceArray.add(sb.toString());
				sb.setLength(0);
			} else if (dShipsFired) {
				if (dShipsFiredFleet != null) {
					sb.append("D-Schiffe=");
					sb.append(dShips);
					sb.append(" (feuert auf ");
					sb.append(dShipsFiredFleet.name());
					sb.append(")");
					resourceArray.add(sb.toString());
					sb.setLength(0);
				}
			} else {
				sb.append("D-Schiffe=");
				sb.append(dShips);
				resourceArray.add(sb.toString());
				sb.setLength(0);
			}
		}
		if (resourceArray.size() != 0) {
			result = Utils.createBracketAndCommarStringWithStringArray(resourceArray);
		}
		return result;
	}

	@Override
	public int compareTo(Planet o) {
		int result = 0;
		if (this.number < o.number) {
			result = -1;
		}
		if (this.number > o.number) {
			result = 1;
		}
		if (this.number == o.number) {
			result = 0;
		}
		return result;
	}

	public boolean equals(Planet o) {
		boolean result = this.number == o.number;

		return result;
	}

	public boolean deepEquals(Planet o) {
		boolean result = this.number == o.number;
		if (result) {
			result = this.port.equals(o.port);
		}

		if (result) {
			result = this.player.equals(o.player);
		}

		return result;
	}
	/*


		var resouceString = createResourceString()

				if countElements(resouceString) != 0 {
					desc += " "
							desc += resouceString
				}

		if fleets.count > 0 {
			for fleet in fleets {
				desc += "\n   "
						desc += fleet.description
			}
		}

		var fleetMovementsCount = fleetMovements.count

				if fleetMovementsCount > 0 {
					var counter = 0

							desc += "\n   ("
									for fleetMovement in fleetMovements {
										desc += fleetMovement.description
												counter++
												if counter < fleetMovementsCount {
													desc += " "
												}
									}
					desc += ")"
				}

				return desc
	 */

	public boolean hasConnectionToPlanet(Planet aPlant) {
		boolean result = false;
		if (port != null) {
			result = port.hasConnectionToPlanet(aPlant);
		}
		return result;
	}


	//}


	public static Planet planetWithNumber(ArrayList<Planet> planetArray, int number) {
		Planet result = null;
		for (Planet planet : planetArray) {
			if (planet.number == number) {
				result = planet;
				break;
			}
		}
		return result;
	}

	public static boolean containsPlanet(ArrayList<Planet> planets, Planet aPlanet) {
		boolean result = false;
		Iterator<Planet> it = planets.iterator();
		while (it.hasNext()) {
			Planet aPlanetFromArrayList = it.next();
			if (aPlanetFromArrayList.equals(aPlanet)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public void addHitAmbushFleets(Fleet aFleet) {
		if (hitAmbuschFleets.contains(aFleet)  != true) {
			Fleet fleetClone = new Fleet();
            fleetClone.player = aFleet.player;
            fleetClone.ships = aFleet.ships;
            fleetClone.number = aFleet.number;
			hitAmbuschFleets.add(fleetClone);
		}
	}		

	public Element getXMLElementForPlayer(Document doc, Player aPlayer) {
		Element childElementPlanet = doc.createElement("world");

		Attr attr = doc.createAttribute("completeInfo");
		attr.setValue("True");
		childElementPlanet.setAttributeNode(attr);

		attr = doc.createAttribute("hasSeen");
		attr.setValue(aPlayer.name + ":0");
		childElementPlanet.setAttributeNode(attr);

		if (player != null) {
			attr = doc.createAttribute("owner");
			attr.setValue(player.name);
			childElementPlanet.setAttributeNode(attr);
		}

		attr = doc.createAttribute("index");
		attr.setValue(Integer.toString(number));
		childElementPlanet.setAttributeNode(attr);

		attr = doc.createAttribute("unloadCounter");
		attr.setValue("");
		childElementPlanet.setAttributeNode(attr);

		this.addXMLConnectOnParent(doc, childElementPlanet);
		this.addXMLPassingOnParent(doc, childElementPlanet);
		this.addXMLFleetOnParent(doc, childElementPlanet);

		Element childElementHomeFleet = doc.createElement("homeFleet");

		attr = doc.createAttribute("key");
		attr.setValue("D");
		childElementHomeFleet.setAttributeNode(attr);

		attr = doc.createAttribute("ships");
		attr.setValue(Integer.toString(dShips));
		childElementHomeFleet.setAttributeNode(attr);

		 if (dShipsAmbush) {
				attr = doc.createAttribute("fired");
				attr.setValue(fireAmbuschFleets());
				childElementHomeFleet.setAttributeNode(attr);
		 } else if (dShipsFired) {
			 if (dShipsFiredFleet != null) {
				 attr = doc.createAttribute("fired");
				 attr.setValue("AF" + Integer.toString(dShipsFiredFleet.number));
				 childElementHomeFleet.setAttributeNode(attr);
			 }
		 }
		childElementPlanet.appendChild(childElementHomeFleet);
		return childElementPlanet;
	}

	private void  addXMLPassingOnParent(Document doc, Element parent) {
		if (fleetMovements.size() > 0) {
			for (FleetMovement fleetMovement : fleetMovements) {
				fleetMovement.addXMLPassingOnParent(doc, parent);
			}
		}
	}
	
	private void addXMLFleetOnParent(Document doc, Element parent) {
		for (Fleet fleet : fleets) {
			fleet.addXMLFleetOnParent(doc, parent);
		}
	}

	private void addXMLConnectOnParent(Document doc, Element parent) {
		if (port != null) {
			port.addXMLConnectOnParent(doc, parent);

		}
	}

}
