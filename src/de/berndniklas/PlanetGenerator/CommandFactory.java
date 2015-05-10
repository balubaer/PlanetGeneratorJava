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
		ArrayList <Command> commandArray = new ArrayList<Command>();

		Collection<String> keys = commandStringsDict.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String playerName = iterator.next();
			String[] commands = commandStringsDict.get(playerName);

			for (String command : commands) {
				System.out.println("Command: " + command);
				processCommand = command;
				commandElements = new ArrayList<String>();
				int counter = 0	;
				int charCount = command.length();
				boolean foundCommandElementEnd = false;
				StringBuilder commandElement = new StringBuilder();
				StringBuilder commandCharsBuilder = new StringBuilder();

				for (counter = 0; counter < charCount; counter++) {
					char commandChar = command.charAt(counter);
					if (Character.isDigit(commandChar) == false) {
						commandCharsBuilder.append(commandChar);

						if (counter != 0) {
							foundCommandElementEnd = true;
						}
						if (foundCommandElementEnd) {
							commandElements.add(commandElement.toString());
							commandElement = new StringBuilder();
							foundCommandElementEnd = false;
						}
						commandElement.append(commandChar);
					} else {
						commandElement.append(commandChar);
					}
					if (counter == charCount) {
						commandElements.add(commandElement.toString());
					}
					//Character.isLetter(command.charAt(i));

				}
				
				commandChars = commandCharsBuilder.toString();
				
				commandPlayer = allPlayerDict.get(playerName);
				Object commandInstance = this.getCommandInstance();
				if (commandInstance != null) {
					if (commandInstance instanceof Command) {
						commandArray.add((Command)commandInstance);
					}
				}
			}
		}
	}

	private Object getCommandInstance() {
		Object result = null;
		if (commandChars != null) {
			if (commandChars.length() >= 2) {
				switch (commandChars.charAt(0)) {
				case 'F':
					switch (commandChars.charAt(1)) {
					case 'W':
						result = createMoveCommand();
						break;
					case 'T':
                        if (commandChars.length() == 3) {
                            switch (commandChars.charAt(2)) {
                            case 'F':
                                result = createTransferShipsFleetToFleetCommand();
                                break;
                            case 'D':
                                result = createTransferShipsFleetToDShipsCommand();
                                break;
                            default:
                                result = null;
                                break;
                            }
                        }
                        break;
                    case 'A':
                        if (commandChars.length() == 3) {
                            switch (commandChars.charAt(2)) {
                            case 'F':
                                result = createFireFleetToFleetCommand();
                                break;
                            case 'D':
                                result = createFireFleetToDShipsCommand();
                                break;
                            default:
                                result = null;
                                break;
                            }
                        }
                        break;
                    case 'D':
	                    switch (commandChars.charAt(1)) {
	                    case 'A':
	                        if (commandChars.length() == 3) {
	                            if (commandChars.charAt(2) == 'F') {
	                                result = createFireDShipsToFleetCommand();
	                            }
	                        }
	                        break;
	                    case 'T':
	                        if (commandChars.length() == 3) {
	                            if (commandChars.charAt(2) == 'F') {
	                                result = createTransferDShipsToFleetCommand();
	                            }
	                        }
	                        break;
	                    default:
	                        result = null;
	                        break;
	                    }
	                case 'Z':
	                    result = createAmbushOffForPlanet();
	                    break;
	                default:
	                    result = null;
	                    break;
	                }
				default:
					break;
				}
			}
		}
		return result;
	}

	// Znn
	private Planet findPlanet() {
		Planet planet = new Planet();
		int counter = 0;

		for (String commantElement : commandElements) {
			if (counter == 0) {
				// int planetNumber = IntegerextractNumberString(commantElement);
				Integer planetNumberIntger = Integer.parseInt(Utils.extractNumberString(commantElement));
				int planetNumber = planetNumberIntger.intValue();
				if (planetNumber != 0) {
					Planet planetFromNumber = Planet.planetWithNumber(planets, planetNumber);
					if (planetFromNumber != null) {
						planet = planetFromNumber;
					}
				}
			}
			counter++;
		}
		return planet;
	}
	
	private Object createAmbushOffForPlanet() {
		 Planet planet = findPlanet();
		 return new AmbushOffForPlanet(planet, processCommand, commandPlayer);
	}

	private FleetTwoPlanetsAndShipsDTO findTransferDShipsToFleetAndPlanets() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createTransferDShipsToFleetCommand() {
		// TODO Auto-generated method stub
		/*
		 *  func findTransferDShipsToFleetAndPlanets() -> (toFleet: Fleet, fromHomePlanet:Planet, toHomePlanet:Planet, shipsToTransfer: Int) {
        var counter = 0
        var shipsToTransfer = 0
        var toFleet: Fleet = Fleet()
        var fromHomePlanet: Planet = Planet()
        var toHomePlanet: Planet = Planet()
        
        for commantElement in commandElements {
            if counter == 0 {
                var planetNumber = extractNumberString(commantElement).toInt()
                if planetNumber != nil {
                    fromHomePlanet = planetWithNumber(planets, planetNumber!) as Planet!
                }
            } else if counter == 1 {
                var aShipsToTransfer = extractNumberString(commantElement).toInt()
                if aShipsToTransfer != nil {
                    shipsToTransfer = aShipsToTransfer!
                }
            } else {
                var fleetNumber: Int? = extractNumberString(commantElement).toInt()
                if fleetNumber != nil {
                    var aFleetAndHomePlanet = fleetAndHomePlanetWithNumber(planets, fleetNumber!)
                    if aFleetAndHomePlanet.fleet != nil && aFleetAndHomePlanet.homePlanet != nil {
                        toFleet = aFleetAndHomePlanet.fleet!
                        toHomePlanet = aFleetAndHomePlanet.homePlanet!
                    }
                }
            }
            counter++
        }
        return (toFleet, fromHomePlanet, toHomePlanet, shipsToTransfer)
    }

    func createTransferDShipsToFleetCommand() -> TransferDShipsToFleet {
        var transferDShipsToFleetAndPlanets = findTransferDShipsToFleetAndPlanets()
        return TransferDShipsToFleet(aToFleet: transferDShipsToFleetAndPlanets.toFleet, aFromHomePlanet: transferDShipsToFleetAndPlanets.fromHomePlanet, aToHomePlanet: transferDShipsToFleetAndPlanets.toHomePlanet, aShipsToTransfer: transferDShipsToFleetAndPlanets.shipsToTransfer, aString: processCommand!, aPlayer: commandPlayer!)
    }
   
		 */
		FleetTwoPlanetsAndShipsDTO transferDShipsToFleetAndPlanets = findTransferDShipsToFleetAndPlanets();
		return new TransferDShipsToFleet(transferDShipsToFleetAndPlanets.toFleet, transferDShipsToFleetAndPlanets.fromHomePlanet, transferDShipsToFleetAndPlanets.toHomePlanet, transferDShipsToFleetAndPlanets.shipsToTransfer, processCommand, commandPlayer);
	}

	private Object createFireDShipsToFleetCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createFireFleetToDShipsCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createFireFleetToFleetCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createTransferShipsFleetToDShipsCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object createTransferShipsFleetToFleetCommand() {
		// TODO Auto-generated method stub
		return null;
	}
}
