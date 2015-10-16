package de.berndniklas.PlanetGenerator;

import java.util.HashMap;

public class RemoveTeammate extends Command implements ExecuteCommand {

	HashMap<String, Player> allPlayerDict;

	public RemoveTeammate(HashMap<String, Player> playerDict, String aString, Player aPlayer) {
		super(aString, aPlayer, TurnPhase.Initial);
		allPlayerDict = playerDict;
	}

	@Override
	public void executeCommand() {
		String keyString = string.substring(2);
		Player playerFromAll = allPlayerDict.get(keyString);
		if (playerFromAll != null) {
			player.teammates.remove(playerFromAll);
		}
	}
}