package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Iterator;

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

	ArrayList<Player> hitAmbuschPlayers = new ArrayList<Player>();

	int hitedShotsDShips;

	//TODO: niklas Kunstwerke ... V70:Plastik Mondstein
	public String name() {
		StringBuilder sb = new StringBuilder(50);
		sb.append("W");
		sb.append(number);
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
		if (dShips != 0) {
			if (dShipsAmbush) {
				sb.append("D-Schiffe=");
				sb.append(dShips);
				sb.append(" (Ambusch)");
				resourceArray.add(sb.toString());
				sb.setLength(0);
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
}
