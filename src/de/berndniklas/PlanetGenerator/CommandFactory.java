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

		//myString.split("\\s+");
		// TODO Auto-generated method stub

	}

	private Object getCommandInstance() {
		Object result = null;
		if (commandChars != null) {
			// var characterArray: Array <Character> = Array(commandChars!)

			if (commandChars.length() >= 2) {
				switch (commandChars.charAt(0)) {
				case 'F':
					switch (commandChars.charAt(1)) {
					case 'W':
						result = createMoveCommand();
						break;

					default:
						break;
					}
					break;

				default:
					break;
				}
			}
		}
		/*
			                switch characterArray[0] {
			                case "F":
			                    switch characterArray[1] {
			                    case "W":
			                        result = createMoveCommand()
			                    case "U":
			                        result = createUnloadingMetalCommand()
			                    case "T":
			                        if characterArray.count == 3 {
			                            switch characterArray[2] {
			                            case "F":
			                                result = createTransferShipsFleetToFleetCommand()
			                            case "D":
			                                result = createTransferShipsFleetToDShipsCommand()
			                            default:
			                                result = nil
			                            }
			                        }
			                    case "A":
			                        if characterArray.count == 3 {
			                            switch characterArray[2] {
			                            case "F":
			                                result = createFireFleetToFleetCommand()
			                            case "D":
			                                result = createFireFleetToDShipsCommand()
			                            default:
			                                result = nil

			                            }
			                        }
			                    default:
			                        result = nil
			                    }
			                case "W":
			                    switch characterArray[1] {
			                    case "B":
			                        if characterArray.count == 3 {
			                            if characterArray[2] == "F" {
			                                result = createBuildFleetShipCommand()
			                            }
			                        }
			                    default:
			                        result = nil
			                    }
			                case "D":
			                    switch characterArray[1] {
			                    case "A":
			                        if characterArray.count == 3 {
			                            if characterArray[2] == "F" {
			                                result = createFireDShipsToFleetCommand()
			                            }
			                        }
			                    case "T":
			                        if characterArray.count == 3 {
			                            if characterArray[2] == "F" {
			                                result = createTransferDShipsToFleetCommand()
			                            }
			                        }
			                    default:
			                        result = nil
			                    }
			                case "Z":
			                    result = createAmbushOffForPlanet()
			                default:
			                    result = nil
			                }
			            } else if characterArray.count == 1 {
			                switch characterArray[0] {
			                    case "Z":
			                    result = createAmbushOffForPlayer()
			                default:
			                    result = nil
			                }
			            }
			        }
			        return result*/
		return result;
	}
}
