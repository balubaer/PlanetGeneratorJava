package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class CommandFactory {

	ArrayList<Planet> planets;
	HashMap<String, String[]> commandStringsDict;
	String processCommand;
	String commandChars;
	Player commandPlayer;
	ArrayList <String> commandElements;
	HashMap<String, Player> allPlayerDict;

	public boolean coreGame;
    //var commandStringsDict: Dictionary <String, Array <String>>

	public CommandFactory(ArrayList<Planet> aPlanets,
			HashMap<String, Player> aAllPlayerDict) {
		planets = aPlanets;
		allPlayerDict = aAllPlayerDict;
		commandStringsDict = new HashMap<String, String[]>();
		commandElements = new ArrayList <String>();
	}

	public void setCommandStringsWithLongString(String playerName,
			String commandsString) {
		String[] array = commandsString.split("\\s+");
		if (array != null) {
			commandStringsDict.put(playerName, array);
		}

	}

	public void executeCommands() {
		Collection<String> keys = commandStringsDict.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String player = iterator.next();
			String[] strings = commandStringsDict.get(player);
			
			for (String string : strings) {
				System.out.println("Command: " + string);
			}
		}
		
		//myString.split("\\s+");
		// TODO Auto-generated method stub
		
	}

}
