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

	 // FnnnWmmm FnnnWmmmWooo FnnnWmmmWoooWrrr
    private FleetHomplanetPlanetArrayDTO findFleetAndPlanets() {
    	//-> (fleet: Fleet, homePlanet:Planet, planetArray: Array <Planet>)
    	Fleet fleet = new Fleet();
    	Planet homePlanet = new Planet();
    	ArrayList <Planet> planetArray = new ArrayList <Planet>();
        int counter = 0;
        
        for (String commantElement : commandElements) {
            if (counter == 0) {
            	int fleetNumber = Integer.parseInt(Utils.extractNumberString(commantElement));
            	if (fleetNumber != 0) {
            		FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
            		if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
                        fleet = aFleetAndHomePlanet.fleet;
                        homePlanet = aFleetAndHomePlanet.planet;
            		}
            	} else {
                	int planetNumber = Integer.parseInt(Utils.extractNumberString(commantElement));
                	if (planetNumber != 0) {
                        Planet planet = Planet.planetWithNumber(planets, planetNumber);
                        if (planet != null) {
                            planetArray.add(planet);
                        }
                	}
            	}
            	counter++;
            }
		} 
        return new FleetHomplanetPlanetArrayDTO	(fleet, homePlanet, planetArray);
    }

    public MoveCommand createMoveCommand() {
    	FleetHomplanetPlanetArrayDTO fleetAndPlanets = findFleetAndPlanets();
        return new MoveCommand(fleetAndPlanets.fleet, fleetAndPlanets.homePlanet, fleetAndPlanets.planetArray, processCommand, commandPlayer);
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
