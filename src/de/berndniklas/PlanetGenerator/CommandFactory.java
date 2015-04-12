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
        /*
        for commantElement in commandElements {
            if counter == 0 {
                var fleetNumber: Int? = extractNumberString(commantElement).toInt()
                if fleetNumber != nil {
                    var aFleetAndHomePlanet = fleetAndHomePlanetWithNumber(planets, fleetNumber!)
                    if aFleetAndHomePlanet.fleet != nil && aFleetAndHomePlanet.homePlanet != nil {
                        fleet = aFleetAndHomePlanet.fleet!
                        homePlanet = aFleetAndHomePlanet.homePlanet!
                    }
                }
            } else {
                var planetNumber: Int? = extractNumberString(commantElement).toInt()
                if planetNumber != nil {
                    var planet = planetWithNumber(planets, planetNumber!)
                    
                    if planet != nil {
                        planetArray.append(planet!)
                    }
                }
            }
            counter++
        }*/
        return new FleetHomplanetPlanetArrayDTO	(fleet, homePlanet, planetArray);
    }
    /*
    func createMoveCommand() -> MoveCommand {
        var fleetAndPlanets = findFleetAndPlanets()
        return MoveCommand(aFleet: fleetAndPlanets.fleet, aHomePlanet:fleetAndPlanets.homePlanet, aPlanetArray: fleetAndPlanets.planetArray, aString: processCommand!, aPlayer: commandPlayer!)
    }*/


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
