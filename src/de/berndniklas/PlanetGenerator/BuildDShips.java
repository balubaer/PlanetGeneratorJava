package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

public class BuildDShips extends Command implements ExecuteCommand {

	ArrayList<Planet> planets;
	final int maxBuild = 4;
	
	public BuildDShips(ArrayList<Planet> aPlanetArray, Player aPlayer) {
		super("", aPlayer, TurnPhase.Building);
		planets = aPlanetArray;	
	}

	private boolean testPlayerInNextLevelPlanets(
			ArrayList<Planet> nextLevelPlanets) {
		boolean result = true;

		if (nextLevelPlanets.size() > 0) {
			for (Planet planet : nextLevelPlanets) {
				if (planet.player != null) {
					if (!this.player.equals(planet.player)) {
						result = false;
						break;
					}
				} else {
					result = false;
					break;
				}
			} 

		} else {
			result = false;
		}
		return result;
	}

	private int calculateNumberOfShipsToBuild(Planet planet) {
		int result = 0;
		boolean foundDistanceLevel = false;
		DistanceLevel disLevel = new DistanceLevel(planet, 1);

		while (foundDistanceLevel != true) {
			if (this.testPlayerInNextLevelPlanets(disLevel.nextLevelPlanets) == false) {
				foundDistanceLevel = true;
			} else {
				if (maxBuild <= disLevel.distanceLevel) {
					foundDistanceLevel = true;
				} else {
					disLevel.goNextLevel();
				}
			}
		}

		result = disLevel.distanceLevel;

		if (result < 1) {
			result = 1;
		}
		return result;
	}

	@Override
	public void executeCommand() {
		for (Planet planet : planets) {
            if (planet.player.equals(this.player)) {
                int shipsToBuild = calculateNumberOfShipsToBuild(planet);
                        
                planet.dShips += shipsToBuild;
            }
		}
	}

	
}
