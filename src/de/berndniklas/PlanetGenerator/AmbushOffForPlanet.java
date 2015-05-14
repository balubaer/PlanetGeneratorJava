package de.berndniklas.PlanetGenerator;

public class AmbushOffForPlanet extends Command implements ExecuteCommand {

	Planet planet = null;

	public AmbushOffForPlanet(Planet aPlanet, String processCommand, Player commandPlayer) {
		super(processCommand, commandPlayer, TurnPhase.Initial);
        planet = aPlanet;

	}
	
	@Override
	public void executeCommand() {
		if (planet.player.name.equals(player.name)) {
			Player planetPlayer = planet.player;
					if (planetPlayer != null) {
						if (planetPlayer.equals(player)) {
							planet.ambushOff = true;
						} else {
							//TODO: Fehler
						}
					}
		} else {
			//TODO: Fehler Welt ist nicht vom Spieler
		}		
	}
}
