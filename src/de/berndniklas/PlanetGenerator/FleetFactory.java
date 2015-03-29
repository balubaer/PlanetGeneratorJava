package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

public class FleetFactory {

	private Dice dice;
	int fleetCount;

	public FleetFactory(int aFleetCount) {
		fleetCount = aFleetCount;
	}

	public void createWithPlanetArray(ArrayList<Planet> planetArray) {
		dice.setSites(planetArray.size());

		//Create Fleets

		for (int index = 1; index <= fleetCount; index++) {
			Planet planet = Planet.planetWithNumber(planetArray, dice.roll());

			if (planet != null) {
				Fleet fleet = new Fleet();
				fleet.number = index;
				planet.fleets.add(fleet);
			}
		}		
	}
}
