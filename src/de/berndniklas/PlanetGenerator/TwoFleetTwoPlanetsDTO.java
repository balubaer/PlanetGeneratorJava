package de.berndniklas.PlanetGenerator;

public class TwoFleetTwoPlanetsDTO extends FleetTwoPlanetsDTO {

	public Fleet fromFleet;
	
	public TwoFleetTwoPlanetsDTO(Fleet aToFleet, Planet aFromHomePlanet,
			Planet aToHomePlanet) {
		super(aToFleet, aFromHomePlanet, aToHomePlanet);
		// TODO Auto-generated constructor stub
	}

	public TwoFleetTwoPlanetsDTO(Fleet aFromFleet, Fleet aToFleet,
			Planet aFromHomePlanet, Planet aToHomePlanet) {
		super(aToFleet, aFromHomePlanet, aToHomePlanet);
		fromFleet = aFromFleet;
		// TODO Auto-generated constructor stub
	}

}
