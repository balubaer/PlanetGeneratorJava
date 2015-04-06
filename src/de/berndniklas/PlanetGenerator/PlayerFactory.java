package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class PlayerFactory {

	Dice planetDice;
	Dice fleetDice;
	Dice playerDice;
	int distanceLevelHomes;
	ArrayList<String> playerNameArray;
	ArrayList<Planet> nextLevelPlanets;
	//Planet lastPlayerPlanet = null;
	public HashMap<String, Planet> homePlanetsMap = new HashMap<String, Planet>();

	public PlayerFactory(ArrayList<String> aPlayerNameArray) {
		planetDice = new Dice();
		fleetDice = new Dice();
		playerDice = new Dice();
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
		int counter = 1;
		planetDice.setSites(planetArray.size());
		fleetDice.setSites(fleetCount);
		int playerNameCount = playerNameArray.size();
		distanceLevelHomes = aDistanceLevelHomes;
		for (int i = 0; i < playerNameCount; i++) {
			int fleetsOnHomePlanet = aFleetsOnHomePlanet;
			Player player = findePlayerWithDice();
			Planet planet;
			if (counter == 1) {
				planet = findPlanetWithDice(planetDice, planetArray);
			} else {
				this.nextLevelPlanets = makeNextLevelPlanets();
				planet = findPlanetWithDice(planetDice, this.nextLevelPlanets);
			}
			planet.player = player;
			homePlanetsMap.put(player.name, planet);

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

	private ArrayList<Planet> makeNextLevelPlanets() {
		Collection<Planet> values = homePlanetsMap.values();
		ArrayList<Planet> result = new ArrayList<Planet>();
		ArrayList<Planet> allPassedPlanets = new ArrayList<Planet>();
		ArrayList<Planet> allNextLevelPlanets = new ArrayList<Planet>();
		boolean finishCreate = false;
		int startDistanceLevelHomes = distanceLevelHomes;

		while (finishCreate == false) {
			for (Iterator<Planet> iterator = values.iterator(); iterator
					.hasNext();) {
				Planet planet = iterator.next();
				DistanceLevel distLevel = new DistanceLevel(planet, startDistanceLevelHomes);
				for (Planet planetFromPassedPlanets : distLevel.passedPlanets) {
					if (Planet.containsPlanet(allPassedPlanets, planetFromPassedPlanets) == false) {
						allPassedPlanets.add(planetFromPassedPlanets);
					}
				}

				for (Planet planetFromNextLevel : distLevel.nextLevelPlanets) {
					if (Planet.containsPlanet(allNextLevelPlanets, planetFromNextLevel) == false){
						allNextLevelPlanets.add(planetFromNextLevel);
					}
				}
				for (Planet planetFromNextLevel : allNextLevelPlanets) {

					if (Planet.containsPlanet(allPassedPlanets, planetFromNextLevel) == false){
						result.add(planetFromNextLevel);
					}
				}
			}
			if (result.size() > 0) {
				finishCreate = true;
			} else {
				startDistanceLevelHomes--;
				allPassedPlanets.clear();
				allNextLevelPlanets.clear();
			}
		}
		distanceLevelHomes = startDistanceLevelHomes;
		return result;
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

