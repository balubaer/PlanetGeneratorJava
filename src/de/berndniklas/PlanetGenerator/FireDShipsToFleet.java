package de.berndniklas.PlanetGenerator;

public class FireDShipsToFleet extends Command implements ExecuteCommand {

	Fleet toFleet;
	Planet fromHomePlanet;
	Planet toHomePlanet;

	public FireDShipsToFleet(Fleet aToFleet, Planet aFromHomePlanet,
			Planet aToHomePlanet, String processCommand, Player commandPlayer) {
		super(processCommand, commandPlayer, TurnPhase.Combat);

		toFleet = aToFleet;
		fromHomePlanet = aFromHomePlanet;
		toHomePlanet = aToHomePlanet;
	}

	@Override
	public void executeCommand() {
		if (fromHomePlanet.player != null && player != null) {
			if (fromHomePlanet.player.name.equals(player.name)) {
				boolean isError = false;

				if (isError == false) {
					if (!fromHomePlanet.equals(toHomePlanet))  {
						//TODO: Fehler art zufügen
						isError = true;
					}
				}

				//TODO: Weiter Tests implementieren

				if (isError == false) {
					toFleet.hitedShots += fromHomePlanet.dShips;
					fromHomePlanet.dShipsFired = true;
				}
			} else {
				//TODO: Fehler Welt ist nicht vom Spieler
			}
		}
	}
}
