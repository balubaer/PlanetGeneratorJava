package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

public class DistanceLevel {

	public ArrayList<Planet> passedPlanets = new ArrayList<Planet>();
	public ArrayList<Planet> nextLevelPlanets = new ArrayList<Planet>();

	private Planet startPlanet;
	int distanceLevel;
	boolean stopCreateNewNextLevelPlanets = false;

	public DistanceLevel(Planet aStartPlanet, int aDistanceLevel) {
		if (aDistanceLevel < 1) {
			distanceLevel = 1;
		} else {
			distanceLevel = aDistanceLevel;
		}
		startPlanet = aStartPlanet;
		for (int levelCount = 1; levelCount <= distanceLevel; levelCount++) {
			if (levelCount == 1) {
				passedPlanets.add(startPlanet);
				ArrayList<Planet> planets = startPlanet.port.planets;
				if (planets != null) {
					for (Planet planet : planets) {
						nextLevelPlanets.add(planet);
					}
				}
			} else {
				createNewNextLevelPlanets();
				if (stopCreateNewNextLevelPlanets) {
					distanceLevel = levelCount - 1;
					break;
				}
			}
		}
	}

	private void createNewNextLevelPlanets() {
		ArrayList<Planet> oldNextLevelPlanets = new ArrayList<Planet>(nextLevelPlanets);
		ArrayList<Planet> newPassedPlanets = passedPlanets;

		for (Planet planet : nextLevelPlanets) {
			if (Planet.containsPlanet(newPassedPlanets, planet) == false) {
				newPassedPlanets.add(planet);
			}
		}
		nextLevelPlanets.clear();

		for (Planet planet : oldNextLevelPlanets) {

			Port port = planet.port;

			if (port != null) {
				ArrayList<Planet> planetsFromPort = port.planets;

				for (Planet planetFromPort : planetsFromPort) {

					if (Planet.containsPlanet(newPassedPlanets, planetFromPort) == false) {
						if (Planet.containsPlanet(nextLevelPlanets, planetFromPort) == false) {
							nextLevelPlanets.add(planetFromPort);
						}
					}
				}
			}
		}
		if (nextLevelPlanets.size() == 0) {
			stopCreateNewNextLevelPlanets = true;
			nextLevelPlanets = oldNextLevelPlanets;
		} else {
			passedPlanets = newPassedPlanets;
		}

	}

	public void goNextLevel() {
		distanceLevel++;
		createNewNextLevelPlanets();
	}

}
