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

			/*
			 * 			            if counter == 1 {
			                planet = findPlanetWithDice(planetDice, planetArray: planetArray)
			                var distLevel = DistanceLevel(aStartPlanet: planet, aDistanceLevel: distanceLevelHomes)
			                self.passedPlanets = distLevel.passedPlanets
			                self.nextLevelPlanets = distLevel.nextLevelPlanets
			            } else {
			                planet = findPlanetWithDice(planetDice, planetArray: self.nextLevelPlanets)
			                var distLevel = DistanceLevel(aStartPlanet: planet, aDistanceLevel: distanceLevelHomes)
			                for planetFromPassedPlanets in distLevel.passedPlanets {
			                    if contains(self.passedPlanets, planetFromPassedPlanets) != true {
			                        self.passedPlanets.append(planetFromPassedPlanets)
			                    }
			                }

			                var removePlanets: Array <Planet> = Array()
			                for planetFromNextLevel in self.nextLevelPlanets {
			                    if contains(self.passedPlanets, planetFromNextLevel) {
			                        removePlanets.append(planetFromNextLevel)
			                    }
			                }
			                for removePlanet in removePlanets {
			                    self.nextLevelPlanets.removeObject(removePlanet)
			                }

			                for planetFromNextLevel in distLevel.nextLevelPlanets {
			                    if contains(self.passedPlanets, planetFromNextLevel) != true {
			                        if contains(self.nextLevelPlanets, planetFromNextLevel) != true {
			                            self.nextLevelPlanets.append(planetFromNextLevel)
			                        }
			                    }
			                }
			            }
			            planet.player = player

			            fleetsOnHomePlanet -= planet.fleets.count

			            for fleet in planet.fleets {
			                fleet.player = player
			                fleet.ships = startShipsCount
			            }

			            for index in 1...fleetsOnHomePlanet {
			                var fleetAndPlanet = findFleetAndPlanetWithDice(fleetDice, planetArray: planetArray)
			                fleetAndPlanet.fleet.player = player
			                fleetAndPlanet.fleet.ships = startShipsCount
			                fleetAndPlanet.planet.fleets.removeObject(fleetAndPlanet.fleet)
			                planet.fleets.append(fleetAndPlanet.fleet)
			            }
			            counter++

			 */

		}
	}		
}

/*	    
	    func findPlanetWithDice(dice:Dice, planetArray:Array <Planet>) -> Planet {
	        var result:Planet? = nil
	        var found = false
	        
	        while (!found) {
	            result = planetWithNumber(planetArray, dice.roll())
	            if result != nil {
	                if result!.player == nil {
	                    found = true
	                }
	            }
	        }
	        return result!
	    }
	    
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

