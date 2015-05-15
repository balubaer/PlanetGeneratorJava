package de.berndniklas.PlanetGenerator;

public class FleetAndPlanetDTO {

	public Fleet fleet = null;
	public Planet planet = null;

	public FleetAndPlanetDTO(Fleet aFleet, Planet aPlanet) {
		fleet = aFleet;
		planet = aPlanet;	
	}

	public FleetAndPlanetDTO() {
	}
}
