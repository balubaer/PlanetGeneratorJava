package de.berndniklas.PlanetGenerator;

public class TransferShipsFleetToDShips extends Command implements
ExecuteCommand {

	Fleet fromFleet;
	Planet fromHomePlanet;
	int shipsToTransfer;

	public TransferShipsFleetToDShips(String aString, Player aPlayer,
			TurnPhase aTurnPhase) {
		super(aString, aPlayer, aTurnPhase);
		shipsToTransfer = 0;
	}

	public TransferShipsFleetToDShips(Fleet aFromFleet, Planet aFromHomePlanet,
			int aShipsToTransfer, String processCommand, Player commandPlayer) {
		super(processCommand, commandPlayer, TurnPhase.Transfer);
		fromFleet = aFromFleet;
		fromHomePlanet = aFromHomePlanet;
		shipsToTransfer = aShipsToTransfer;
	}

	@Override
	public void executeCommand() {
		if (player.name.equals(fromFleet.player.name)) {
			boolean isError = false;

			if (isError == false) {
				if (fromFleet.ships < shipsToTransfer) {
					//TODO: Fehler art zufŸgen
					isError = true;
				}
			}

			//TODO: Weiter Tests implementieren

			if (isError == false) {
				fromFleet.ships -= shipsToTransfer;
				fromHomePlanet.dShips += shipsToTransfer;
			}
		} else {
			//TODO: Fehler Flotte ist nicht vom Spieler
		}
	}

}
