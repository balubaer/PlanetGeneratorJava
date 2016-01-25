package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Collection;
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
	
	private Planet findPlanetWithMinPlanetArea(ArrayList<Planet> planetArray) {
		Planet result;
        HashMap<Integer, Integer> planetAndAreaCount = new HashMap<Integer, Integer>();
        int foundPlanetNumber = 1;
        int foundAreaCount = planetArray.size();
        
        for (Planet planet : planetArray) {
        	DistanceLevel distLevel = new DistanceLevel(planet, distanceLevelHomes);
                    int count = distLevel.passedPlanets.size();
                    planetAndAreaCount.put(planet.number, Integer.valueOf(count));
		} 

		Set<Integer> keysFromAllPlayerDict = planetAndAreaCount.keySet();
        Iterator<Integer> it = keysFromAllPlayerDict.iterator();
		while (it.hasNext()) {
			Integer planetNumber = (Integer) it.next();
			Integer areaCount = planetAndAreaCount.get(planetNumber);
			Planet planet = Planet.planetWithNumber(planetArray, planetNumber.intValue());
			if (planet != null) {
				if (foundAreaCount > areaCount.intValue() && planet.player == null) {
					foundAreaCount = areaCount.intValue();
					foundPlanetNumber = planetNumber.intValue();
				}
			}
		} 
        result = Planet.planetWithNumber(planetArray, foundPlanetNumber);
        System.out.println("#### Gefundenen Planeten: " + foundPlanetNumber);

        if (result == null) {
            result = findPlanetWithDice(planetDice, planetArray);
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
			Planet planet;
			System.out.println("#### " + Integer.valueOf(counter).toString() + " Player: " + player.name);

			if (counter == 1) {
				planet = findPlanetWithDice(planetDice, planetArray);
			} else {
				this.nextLevelPlanets = new ArrayList<Planet>(makeNextLevelPlanets());
				//planet = findPlanetWithDice(planetDice, this.nextLevelPlanets);
				planet = findPlanetWithMinPlanetArea(nextLevelPlanets);

			}

			System.out.println("#### " + Integer.valueOf(counter).toString() + " vor setPlayer Planet: " + Integer.valueOf(planet.number).toString());
			//+ " Player: " + planet.player.toString());

			planet.player = player;
			homePlanetsMap.put(player.name, planet);
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

	private HashSet<Planet> makeNextLevelPlanets() {
		Collection<Planet> values = homePlanetsMap.values();
		HashSet<Planet> result = new HashSet<Planet>();
		HashSet<Planet> allPassedPlanets = new HashSet<Planet>();
		HashSet<Planet> allNextLevelPlanets = new HashSet<Planet>();
		boolean finishCreate = false;
		int startDistanceLevelHomes = distanceLevelHomes;

		while (finishCreate == false) {
			for (Iterator<Planet> iterator = values.iterator(); iterator
					.hasNext();) {
				Planet planet = iterator.next();
				DistanceLevel distLevel = new DistanceLevel(planet, startDistanceLevelHomes);
				for (Planet planetFromPassedPlanets : distLevel.passedPlanets) {
					allPassedPlanets.add(planetFromPassedPlanets);
				}

				for (Planet planetFromNextLevel : distLevel.nextLevelPlanets) {
					allNextLevelPlanets.add(planetFromNextLevel);
				}
				for (Planet planetFromNextLevel : allNextLevelPlanets) {
					result.add(planetFromNextLevel);
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

