package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

public class FleetHomplanetPlanetArrayDTO {
	Fleet fleet;
	Planet homePlanet;
	ArrayList<Planet> planetArray;
	
	public FleetHomplanetPlanetArrayDTO(Fleet aFleet, Planet aHomePlanet, ArrayList<Planet> aPlanetArray) {
		fleet = aFleet;
		homePlanet = aHomePlanet;
		planetArray = aPlanetArray;
	}
}
