package de.berndniklas.PlanetGenerator;


public class FleetTwoPlanetsAndShipsDTO extends FleetTwoPlanetsDTO {

	int shipsToTransfer ;

	public FleetTwoPlanetsAndShipsDTO(Fleet aToFleet, Planet aFromHomePlanet,
			Planet aToHomePlanet, int aShipsToTransfer) {
		super(aToFleet, aFromHomePlanet, aToHomePlanet);
		shipsToTransfer = aShipsToTransfer;
	}
}
