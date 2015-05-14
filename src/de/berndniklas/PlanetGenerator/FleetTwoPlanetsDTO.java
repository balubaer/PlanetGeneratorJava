package de.berndniklas.PlanetGenerator;

public class FleetTwoPlanetsDTO {
	Fleet toFleet;
	Planet fromHomePlanet;
	Planet toHomePlanet;
	
	public FleetTwoPlanetsDTO(Fleet aToFleet, Planet aFromHomePlanet,
			Planet aToHomePlanet) {
		toFleet = aToFleet;
		fromHomePlanet = aFromHomePlanet;
		toHomePlanet = aToHomePlanet;
	}
}
