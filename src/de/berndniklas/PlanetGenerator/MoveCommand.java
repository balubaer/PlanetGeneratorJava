package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

public class MoveCommand extends Command implements ExecuteCommand {

	Fleet fleet;
	ArrayList<Planet> planets;
	Planet homePlanet;
	int count = 0;

	public MoveCommand(String aString, Player aPlayer, TurnPhase aTurnPhase) {
		super(aString, aPlayer, aTurnPhase);
	}

	public MoveCommand(Fleet aFleet, Planet aHomePlanet,
			ArrayList<Planet> aPlanetArray, String processCommand,
			Player commandPlayer) {
		super(processCommand, commandPlayer, TurnPhase.Movement);
		fleet = aFleet;
		homePlanet = aHomePlanet;
		planets = aPlanetArray;


		// TODO Auto-generated constructor stub
	}

	@Override
	//FnnnWmmm FnnnWmmmWooo FnnnWmmmWoooWrrr
	public void executeCommand() {
		if (player.name == fleet.player.name) {
			Planet fromPlanet = homePlanet;
			Planet toPlanet;
			boolean isError = false;
			for (Planet planet : planets) {
				toPlanet = planet;
				if (fromPlanet.hasConnectionToPlanet(toPlanet)) {
					fromPlanet = planet;
				} else {
					//TODO: Fehler
					isError = true;
				break;
				}
			} 

			if (fleet.ships == 0) {
				isError = true;
			}

			if (isError == false) {
				if (fleet.fired) {
					isError = true;
				}
			}

			if (isError == false) {
				fromPlanet = homePlanet;

				for (Planet planet : planets) {
					toPlanet = planet;
					FleetMovement fleetMovement = new FleetMovement();
					Fleet fleetCopy = new Fleet();
					fleetCopy.player = fleet.player;
					fleetCopy.number = fleet.number;
					fleetMovement.fleet = fleetCopy;
					fleetMovement.toPlanet = toPlanet;
					fleetMovement.fromPlanet = fromPlanet;
					fleet.fleetMovements.add(fleetMovement);

					fromPlanet = toPlanet;
				} 
			}
		} else {
			//TODO: Fehler Flotte ist nicht vom Spieler
		}
	}
}