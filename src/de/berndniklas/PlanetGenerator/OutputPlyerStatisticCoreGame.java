package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

public class OutputPlyerStatisticCoreGame {
	Player player;
	ArrayList<Planet> planets;
	int planetsCount;
	int fleetCount;
	int shipsOnFleetsCount;
	int dShipCount;

	public String toString() {

		StringBuilder sb = new StringBuilder(1000);
		sb.append("Punkte: ");
		sb.append(player.points);
		sb.append(" | ");

		sb.append("Planeten: ");
		sb.append(planetsCount);
		sb.append(" | ");

		sb.append("Flotten: ");
		sb.append(fleetCount);
		sb.append(" | ");

		sb.append("Schiffe auf Flotten: ");
		sb.append(shipsOnFleetsCount);
		sb.append(" | ");

		sb.append("D-Schiffe: ");
		sb.append(dShipCount);
		return sb.toString();
	}

	public OutputPlyerStatisticCoreGame(ArrayList<Planet> aPlanets, Player aPlayer) {
		player = aPlayer;
		planets = aPlanets;
		planetsCount = 0;
		fleetCount = 0;
		shipsOnFleetsCount = 0;
		dShipCount = 0;
	}

	public void calculateStatistic() {
		for (Planet planet : planets) {
			//Test Planet
			if (Player.isPlanetOwnedByPlayer(player, planet)) {
				planetsCount++;
				dShipCount = dShipCount + planet.dShips;
			}

			for (Fleet fleet : planet.fleets) {
				//Test Fleets
				if (Player.isFleetOwnedByPlayer(player, fleet)) {
					fleetCount++;
					shipsOnFleetsCount = shipsOnFleetsCount + fleet.ships;
				}
			} 
		}
	} 
}
