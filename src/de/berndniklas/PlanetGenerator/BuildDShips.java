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
		
		if (result > maxBuild) {
			result = maxBuild;
		}

		return result;
	}

	private boolean noEnemyFleetOnPlanet(Planet planet) {
        boolean result = true;
        for (Fleet fleet : planet.fleets) {
            if (planet.player  != null) {
                if (fleet.player != null) {
                    if (planet.player.equals(fleet.player) == false) {
                        if (planet.player.teammates.contains(fleet.player) == false) {
                            result = false;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

	@Override
	public void executeCommand() {
		for (Planet planet : planets) {
			if (planet.player != null) {
				if (planet.player.equals(this.player)) {
					if (noEnemyFleetOnPlanet(planet)) {
						int shipsToBuild = calculateNumberOfShipsToBuild(planet);
						planet.dShips += shipsToBuild;
					}
				}
			}
		}
	}
	
}
