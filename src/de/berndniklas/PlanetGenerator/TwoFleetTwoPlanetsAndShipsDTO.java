package de.berndniklas.PlanetGenerator;

public class TwoFleetTwoPlanetsAndShipsDTO extends TwoFleetTwoPlanetsDTO {

	int shipsToTransfer ;

	public TwoFleetTwoPlanetsAndShipsDTO(Fleet aToFleet,
			Planet aFromHomePlanet, Planet aToHomePlanet) {
		super(aToFleet, aFromHomePlanet, aToHomePlanet);
		shipsToTransfer = 0;
	}

	public TwoFleetTwoPlanetsAndShipsDTO(Fleet aFromFleet, Fleet aToFleet,
			Planet aFromHomePlanet, Planet aToHomePlanet) {
		super(aFromFleet, aToFleet, aFromHomePlanet, aToHomePlanet);
		shipsToTransfer = 0;
	}

	public TwoFleetTwoPlanetsAndShipsDTO(Fleet aFromFleet, Fleet aToFleet,
			Planet aFromHomePlanet, Planet aToHomePlanet, int aShipsToTransfer) {
		super(aFromFleet, aToFleet, aFromHomePlanet, aToHomePlanet);
		shipsToTransfer = aShipsToTransfer;
	}

}
