package de.berndniklas.PlanetGenerator;

//FaaAD
public class FireFleetToDShips extends Command implements ExecuteCommand {

	Fleet fromFleet;
	Planet fromHomePlanet;

	public FireFleetToDShips(Fleet aFromFleet, Planet aFromHomePlanet, String aString, Player aPlayer) {
		super(aString, aPlayer, TurnPhase.Combat);
		fromFleet = aFromFleet;
		fromHomePlanet = aFromHomePlanet;
	}

	@Override
	public void executeCommand() {
		if (player.name.equals(fromFleet.player.name)) {
			boolean isError = false;

			//TODO: Weiter Tests implementieren

			if (isError == false) {
				fromHomePlanet.hitedShotsDShips += fromFleet.ships;
				fromFleet.fired = true;
				fromFleet.firesTo = "D-Schiffe";
			}
		} else {
			//TODO: Fehler Flotte ist nicht vom Spieler
		}
	}
}

