package de.berndniklas.PlanetGenerator;


public class FleetTwoPlanetsAndShipsDTO {

	Fleet toFleet;
	Planet fromHomePlanet;
	Planet toHomePlanet;
	int shipsToTransfer ;

	public FleetTwoPlanetsAndShipsDTO(Fleet aToFleet, Planet aFromHomePlanet,
			Planet aToHomePlanet, int aShipsToTransfer) {
		toFleet = aToFleet;
		fromHomePlanet = aFromHomePlanet;
		toHomePlanet = aToHomePlanet;
		shipsToTransfer = aShipsToTransfer;
	}
}
