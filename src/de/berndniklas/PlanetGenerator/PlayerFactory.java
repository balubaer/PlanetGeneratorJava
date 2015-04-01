package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;


public class PlayerFactory {

	Dice planetDice;
	Dice fleetDice;
	ArrayList<String> playerNameArray;
	ArrayList<Planet> passedPlanets;
	ArrayList<Planet> nextLevelPlanets = new ArrayList<Planet>();

	public PlayerFactory(ArrayList<String> aPlayerNameArray) {
		planetDice = new Dice();
		fleetDice = new Dice();
		playerNameArray = aPlayerNameArray;
	}

	private Planet findPlanetWithDice(Dice dice, ArrayList<Planet> planetArray) {
		Planet result = null;
		boolean found = false;

		while (!found) {
			result = Planet.planetWithNumber(planetArray, dice.roll());
			if (result != null) {
				if (result.player == null) {
					found = true;
				}
			}
		}
		return result;
	}

	public void createWithPlanetArray(ArrayList<Planet> planetArray,
			int fleetCount, int aFleetsOnHomePlanet, int startShipsCount,
			int distanceLevelHomes) {
		int counter = 1;
		planetDice.setSites(planetArray.size());
		fleetDice.setSites(fleetCount);
		for (String name : playerNameArray) {
			int fleetsOnHomePlanet = aFleetsOnHomePlanet;
			Player player = new Player();
			Planet planet;
			player.name = name;
			if (counter == 1) {
				planet = findPlanetWithDice(planetDice, planetArray);
				DistanceLevel distLevel = new DistanceLevel(planet, distanceLevelHomes);
				this.passedPlanets = distLevel.passedPlanets;
				this.nextLevelPlanets = distLevel.nextLevelPlanets;
			} else {
				planet = findPlanetWithDice(planetDice, this.nextLevelPlanets);
				DistanceLevel distLevel = new DistanceLevel(planet, distanceLevelHomes);
				for (Planet planetFromPassedPlanets : distLevel.passedPlanets) {
					if (Planet.containsPlanet(this.passedPlanets, planetFromPassedPlanets) == false) {
						this.passedPlanets.add(planetFromPassedPlanets);
					}
				}
				ArrayList<Planet> removePlanets = new ArrayList<Planet>();
				for (Planet planetFromNextLevel : this.nextLevelPlanets) {
					if (Planet.containsPlanet(this.passedPlanets, planetFromNextLevel)){
						removePlanets.add(planetFromNextLevel);

					}
				}

				for (Planet removePlanet : removePlanets) {
					this.nextLevelPlanets.remove(removePlanet);
				}
				for (Planet planetFromNextLevel : distLevel.nextLevelPlanets) {
					if (Planet.containsPlanet(this.passedPlanets, planetFromNextLevel) == false) {
						if (Planet.containsPlanet(this.nextLevelPlanets, planetFromNextLevel) == false) {
							this.nextLevelPlanets.add(planetFromNextLevel);
						}
					}
				}
			}
			 planet.player = player;

			 fleetsOnHomePlanet -= planet.fleets.size();
			 for (Fleet fleet : planet.fleets) {
				 fleet.player = player;
				 fleet.ships = startShipsCount;
			 }
			 
			 for (int index = 1; index <= fleetsOnHomePlanet; index++) {
				/* var fleetAndPlanet = findFleetAndPlanetWithDice(fleetDice, planetArray: planetArray)
			                fleetAndPlanet.fleet.player = player
			                fleetAndPlanet.fleet.ships = startShipsCount
			                fleetAndPlanet.planet.fleets.removeObject(fleetAndPlanet.fleet)
			                planet.fleets.append(fleetAndPlanet.fleet)*/
			}
	            counter++;
		}
	}
}		


/*	    
	    func findFleetAndPlanetWithDice(dice:Dice, planetArray:Array <Planet>) -> (fleet:Fleet, planet:Planet) {
	        var fleet:Fleet? = nil
	        var planet:Planet? = nil
	        var found = false

	        while (!found) {
	        var aFleetAndHomePlanet = fleetAndHomePlanetWithNumber(planetArray, dice.roll())
	            if aFleetAndHomePlanet.fleet != nil && aFleetAndHomePlanet.homePlanet != nil {
	                if aFleetAndHomePlanet.fleet!.player == nil {
	                    found = true
	                    fleet = aFleetAndHomePlanet.fleet!
	                    planet = aFleetAndHomePlanet.homePlanet
	                }
	            }
	        }
	        return (fleet!, planet!)
	    }
	    */

