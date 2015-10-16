package de.berndniklas.PlanetGenerator;

import java.util.HashMap;

public class AddTeammate extends Command implements ExecuteCommand {

	HashMap<String, Player> allPlayerDict;
	
	public AddTeammate(HashMap<String, Player> playerDict, String aString, Player aPlayer) {
		super(aString, aPlayer, TurnPhase.Initial);
		allPlayerDict = playerDict;
	}

	@Override
	public void executeCommand() {
		String keyString = string.substring(2);
		Player playerFromAll = allPlayerDict.get(keyString);
		if (playerFromAll != null) {
			player.teammates.add(playerFromAll);
		}
	}
}
