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
				FleetAndPlanetDTO fleetAndPlanet = findFleetAndPlanetWithDice(fleetDice,  planetArray);
				Fleet fleet = fleetAndPlanet.fleet;
				fleet.player = player;
				fleet.ships = startShipsCount;

				Planet aPlanet = fleetAndPlanet.planet;
				aPlanet.fleets.remove(fleet);
				planet.fleets.add(fleet);
			}
			counter++;
		}
	}

	private FleetAndPlanetDTO findFleetAndPlanetWithDice(Dice dice,
			ArrayList<Planet> planetArray) {
		FleetAndPlanetDTO result = new  FleetAndPlanetDTO();
		boolean found = false;
		while (!found) {
			FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planetArray, dice.roll());
			if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
				if (aFleetAndHomePlanet.fleet.player == null) {
					found = true;
					result.fleet = aFleetAndHomePlanet.fleet;
					result.planet = aFleetAndHomePlanet.planet;
				}
			}
		}
		return result;
	}
}		

