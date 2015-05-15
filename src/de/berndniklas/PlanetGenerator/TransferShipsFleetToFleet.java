package de.berndniklas.PlanetGenerator;

//FnnnTqqqFmmm
public class TransferShipsFleetToFleet extends Command implements
ExecuteCommand {
	Fleet fromFleet;
	Fleet toFleet;
	Planet fromHomePlanet;
	Planet toHomePlanet;
	int shipsToTransfer;

	public TransferShipsFleetToFleet(Fleet aFromFleet, Fleet aToFleet,
			Planet aFromHomePlanet, Planet aToHomePlanet, int aShipsToTransfer,
			String aString, Player aPlayer) {
		super(aString, aPlayer, TurnPhase.Transfer);

		fromFleet = aFromFleet;
		toFleet = aToFleet;
		fromHomePlanet = aFromHomePlanet;
		toHomePlanet = aToHomePlanet;
		shipsToTransfer = aShipsToTransfer;
	}

	@Override
	public void executeCommand() {
		if (player.name.equals(fromFleet.player.name)) {
			boolean isError = false;

			if (isError == false) {
				if (!fromHomePlanet.equals(toHomePlanet))  {
					//TODO: Fehler art zuf�gen
					isError = true;
				}
				if (isError == false) {
					if (fromFleet.ships < shipsToTransfer) {
						//TODO: Fehler art zuf�gen
						isError = true;
					}
				}
				//TODO: Check Owner Man kann einer Neutralen Flotte keine Schiffe Transverieren
			}

			//TODO: Weiter Tests implementieren

			if (isError == false) {
				fromFleet.ships -= shipsToTransfer;
				toFleet.ships += shipsToTransfer;
			}
		} else {
			//TODO: Fehler Flotte ist nicht vom Spieler

		}
	}

}
