package de.berndniklas.PlanetGenerator;

public class FleetPlanetAndShipsDTO extends FleetAndPlanetDTO {
	public int shipsToTransfer;

	public FleetPlanetAndShipsDTO(Fleet aFleet, Planet aPlanet) {
		super(aFleet, aPlanet);
		shipsToTransfer = 0;
	}

	public FleetPlanetAndShipsDTO() {
		super(null, null);
		shipsToTransfer = 0;
	}

	public FleetPlanetAndShipsDTO(Fleet aFleet, Planet aPlanet,
			int aShipsToTransfer) {
		super(aFleet, aPlanet);
		shipsToTransfer = aShipsToTransfer;
	}

}
