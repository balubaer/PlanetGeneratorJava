package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class PlayerFactory {

	Dice planetDice;
	Dice fleetDice;
	Dice playerDice;
	int distanceLevelHomes;
	ArrayList<String> playerNameArray;
	HashSet<Planet> allPassedPlanets;
	HashSet<Planet> allNextLevelPlanets;

	public HashMap<String, Planet> homePlanetsMap = new HashMap<String, Planet>();

	public PlayerFactory(ArrayList<String> aPlayerNameArray) {
		planetDice = new Dice();
		fleetDice = new Dice();
		playerDice = new Dice();
		allPassedPlanets = new HashSet<Planet>();
		allNextLevelPlanets = new HashSet<Planet>();
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
	
	private Player findePlayerWithDice() {
		Player result = new Player();
		playerDice.setSites(playerNameArray.size());
		int index = playerDice.roll() - 1;
		String playerName = playerNameArray.remove(index);
		result.name = playerName;
		return result;
	}

	public void createWithPlanetArray(ArrayList<Planet> planetArray,
			int fleetCount, int aFleetsOnHomePlanet, int startShipsCount,
			int aDistanceLevelHomes) {
		planetDice.setSites(planetArray.size());
		fleetDice.setSites(fleetCount);
		int playerNameCount = playerNameArray.size();
		distanceLevelHomes = aDistanceLevelHomes;
		for (int counter = 1; counter <= playerNameCount; counter++) {
			Player player = findePlayerWithDice();
			Planet planet = null;
			System.out.println("#### " + Integer.valueOf(counter).toString() + " Player: " + player.name);

			if (counter == 1) {
				planet = findPlanetWithDice(planetDice, planetArray);
				makeNextLevelPlanets(planet);
			} else {
				boolean planetFound = false;
				while (!planetFound) {
					planet = findPlanetInAllNextLevelPlanets(player);
					if (planet == null) {
						distanceLevelHomes--;
						if (distanceLevelHomes <= 0) {
							break;
						}
					} else {
						planetFound = true;
					}
				}
				makeNextLevelPlanets(planet);
			}
			if (planet != null) {
				System.out.println("#### " + Integer.valueOf(counter).toString() + " vor setPlayer Planet: " + Integer.valueOf(planet.number).toString());
				//+ " Player: " + planet.player.toString());

				planet.player = player;
				homePlanetsMap.put(player.name, planet);
			} else {
				System.out.println("#### " + Integer.valueOf(counter).toString() + " vor setPlayer Planet: null");

			}
		}

		Set<String> keysPlayerNames = homePlanetsMap.keySet();
		Iterator<String> it = keysPlayerNames.iterator();
		int fleetsOnHomePlanet = aFleetsOnHomePlanet;

		while (it.hasNext()) {
			String PlayerName = (String) it.next();
			Planet planet = homePlanetsMap.get(PlayerName);
			fleetsOnHomePlanet -= planet.fleets.size();
			for (Fleet fleet : planet.fleets) {
				fleet.player = planet.player;
				fleet.ships = startShipsCount;
			}

			for (int index = 1; index <= fleetsOnHomePlanet; index++) {
				FleetAndPlanetDTO fleetAndPlanet = findFleetAndPlanetWithDice(fleetDice,  planetArray);
				Fleet fleet = fleetAndPlanet.fleet;
				fleet.player = planet.player;
				fleet.ships = startShipsCount;

				Planet aPlanet = fleetAndPlanet.planet;
				aPlanet.fleets.remove(fleet);
				planet.fleets.add(fleet);
			}
		}
	}

	private Planet findPlanetInAllNextLevelPlanets(Player player) {
		Planet result = null;
	
		for (Planet planetFromNextLevel : allNextLevelPlanets) {
			int distance = TestDistance.distanceToNextPlayer(planetFromNextLevel, player);
			if (distance == distanceLevelHomes) {
				result = planetFromNextLevel;
				break;
			}
		}
		return result;
	}

	private void makeNextLevelPlanets(Planet planet) {
		if (planet != null) {
			DistanceLevel distLevel = new DistanceLevel(planet, distanceLevelHomes);
			for (Planet planetFromPassedPlanets : distLevel.passedPlanets) {
				allPassedPlanets.add(planetFromPassedPlanets);
			}

			for (Planet planetFromNextLevel : distLevel.nextLevelPlanets) {
				if (allPassedPlanets.contains(planetFromNextLevel) == false) {
					allNextLevelPlanets.add(planetFromNextLevel);
				}
			}
			ArrayList<Planet> nextLevelPlanets = new ArrayList<Planet>(allNextLevelPlanets);
			for (Planet planetFromNextLevel : nextLevelPlanets) {
				if (allPassedPlanets.contains(planetFromNextLevel)) {
					allNextLevelPlanets.remove(planetFromNextLevel);
				}
			}
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

