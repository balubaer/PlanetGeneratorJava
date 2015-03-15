package de.berndniklas.PlanetGenerator;

public class Planet implements Comparable<Planet> {
	int number;
	Port port;
	Player player;
	// var fleets: Array <Fleet>
	// var fleetMovements: Array <FleetMovement> = Array()
	boolean ambushOff;
	int industry;
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
	int metal;
	int mines;
	int population;
	int limit;
	int round;
	int iShips;
	int pShips;
	int dShips;
	boolean dShipsFired;
	boolean dShipsAmbush;

	// var hitAmbuschPlayers: Array <Player> = Array()

	int hitedShotsDShips;

	//TODO: niklas Kunstwerke ... V70:Plastik Mondstein
	private String name() {
		StringBuilder sb = new StringBuilder(50);
		sb.append("W");
		sb.append(number);
		return sb.toString();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(1000);
		sb.append(this.name());
		return sb.toString();
	}
	
	public void setNumber(int aNumber) {
		this.number = aNumber;
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
		
	/*			if (port != null) {
					//sb.append(b) = port!.description
				}
		if player != nil {
			desc += " \(player!.description)"
		}

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

	//}
}

//}
