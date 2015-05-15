package de.berndniklas.PlanetGenerator;

public class FireFleetToFleet extends Command implements ExecuteCommand{

	Fleet fromFleet;
	Fleet toFleet;
	Planet fromHomePlanet;
	Planet toHomePlanet;

	public FireFleetToFleet(Fleet aFromFleet, Fleet aToFleet, Planet aFromHomePlanet,
			Planet aToHomePlanet, String processCommand, Player commandPlayer) {
		super(processCommand, commandPlayer, TurnPhase.Combat);
		fromFleet = aFromFleet;
		toFleet = aToFleet;
		fromHomePlanet = aFromHomePlanet;
		toHomePlanet = aToHomePlanet;
	}

	@Override
	public void executeCommand() {
		if (player.name.endsWith(fromFleet.player.name)) {
			boolean isError = false;

			if (isError == false) {
				if (!fromHomePlanet.equals(toHomePlanet))  {
					//TODO: Fehler art zufügen
					isError = true;
				}
			}

			//TODO: Weiter Tests implementieren

			if (isError == false) {
				toFleet.hitedShots += fromFleet.ships;
				fromFleet.fired = true;
				fromFleet.firesTo = toFleet.name();
			}
		} else {
			//TODO: Fehler Flotte ist nicht vom Spieler
		}
	}

}
