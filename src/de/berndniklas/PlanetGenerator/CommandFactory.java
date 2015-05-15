package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
				int fleetNumber = Utils.extractIntFromcommantElementString(commantElement);
				if (fleetNumber != 0) {
					FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
					if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
						fleet = aFleetAndHomePlanet.fleet;
						homePlanet = aFleetAndHomePlanet.planet;
					}
				}
			}else {
				int planetNumber = Utils.extractIntFromcommantElementString(commantElement);
				if (planetNumber != 0) {
					Planet planet = Planet.planetWithNumber(planets, planetNumber);
					if (planet != null) {
						planetArray.add(planet);
					}
				}
			}
			counter++;
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
						//Character.isLetter(command.charAt(i));

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

				}
				commandElements.add(commandElement.toString());

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
		if (coreGame == true) {
			//TODO Build D-Ships
			/*  for (playerName, player) in allPlayerDict {
                var buildDShips = BuildDShips(aPlanetArray: planets, aPlayer: player)
                commandArray.append(buildDShips as Command)
            }*/
		}

		Collections.sort(commandArray);

		for (Command aCommand : commandArray) {
			ExecuteCommand executeCommand = (ExecuteCommand)aCommand;
			executeCommand.executeCommand();
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
					break;
				case 'Z':
					result = createAmbushOffForPlanet();
					break;
				default:
					result = null;
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
				int planetNumber = Utils.extractIntFromcommantElementString(commantElement);
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
		 int counter = 0;
		 int shipsToTransfer = 0;
		 Fleet toFleet = new Fleet();
		 Planet fromHomePlanet = new Planet();
		 Planet toHomePlanet= new Planet();
		 for (String commantElement : commandElements) {
			 if (counter == 0) {
				 int planetNumber = Utils.extractIntFromcommantElementString(commantElement);
				 if (planetNumber != 0) {
	                    fromHomePlanet = Planet.planetWithNumber(planets, planetNumber);
				 }
				 
			 } else if (counter == 1) {
				 int aShipsToTransfer = Utils.extractIntFromcommantElementString(commantElement);
				 if (aShipsToTransfer != 0) {
	                    shipsToTransfer = aShipsToTransfer;
				 }

			 } else {
				 int fleetNumber = Utils.extractIntFromcommantElementString(commantElement);
				 if (fleetNumber != 0) {
					 FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
					 if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
	                        toFleet = aFleetAndHomePlanet.fleet;
	                        toHomePlanet = aFleetAndHomePlanet.planet;
	                    }
				 }
			 }
	            counter++;
		} 
		 return new FleetTwoPlanetsAndShipsDTO(toFleet, fromHomePlanet, toHomePlanet, shipsToTransfer);
	}

	private Object createTransferDShipsToFleetCommand() {
		FleetTwoPlanetsAndShipsDTO transferDShipsToFleetAndPlanets = findTransferDShipsToFleetAndPlanets();
		return new TransferDShipsToFleet(transferDShipsToFleetAndPlanets.toFleet, transferDShipsToFleetAndPlanets.fromHomePlanet, transferDShipsToFleetAndPlanets.toHomePlanet, transferDShipsToFleetAndPlanets.shipsToTransfer, processCommand, commandPlayer);
	}

	FleetTwoPlanetsDTO findFromDShipsFireToFleetAndPlanets() {
        int counter = 0;
        Fleet toFleet = new Fleet();
        Planet fromHomePlanet = new Planet();
        Planet toHomePlanet = new Planet();
        
        for (String commantElement : commandElements) {
            if (counter == 0) {
                int planetNumber = Utils.extractIntFromcommantElementString(commantElement);
                if (planetNumber != 0) {
                    fromHomePlanet = Planet.planetWithNumber(planets, planetNumber); 
                }
            } else if (counter == 1) {
                //Nichts zu tun
            } else {
                int fleetNumber = Utils.extractIntFromcommantElementString(commantElement);
                if (fleetNumber != 0) {
                    FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
                    if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
                        toFleet = aFleetAndHomePlanet.fleet;
                        toHomePlanet = aFleetAndHomePlanet.planet;
                    }
                }
            }
            counter++;
		} 
        return new FleetTwoPlanetsDTO(toFleet, fromHomePlanet, toHomePlanet);
    }

	private Object createFireDShipsToFleetCommand() {
		FleetTwoPlanetsDTO fromDShipsFireToFleetAndPlanets = findFromDShipsFireToFleetAndPlanets();
		return new FireDShipsToFleet(fromDShipsFireToFleetAndPlanets.toFleet, fromDShipsFireToFleetAndPlanets.fromHomePlanet, fromDShipsFireToFleetAndPlanets.toHomePlanet, processCommand, commandPlayer);
	}

	/*func findFromFleetFireToDShipsAndPlanets() -> (fromFleet: Fleet, fromHomePlanet:Planet) {
        var counter = 0
        var fromFleet: Fleet = Fleet()
        var fromHomePlanet: Planet = Planet()
        
        for commantElement in commandElements {
            if counter == 0 {
                var fleetNumber = extractNumberString(commantElement).toInt()
                if fleetNumber != nil {
                    var aFleetAndHomePlanet = fleetAndHomePlanetWithNumber(planets, fleetNumber!)
                    if aFleetAndHomePlanet.fleet != nil && aFleetAndHomePlanet.homePlanet != nil {
                        fromFleet = aFleetAndHomePlanet.fleet!
                        fromHomePlanet = aFleetAndHomePlanet.homePlanet!
                    }
                }
            }
            counter++
        }
        return (fromFleet, fromHomePlanet)
    }

  */

	private FleetAndPlanetDTO findFromFleetFireToDShipsAndPlanets() {
		int counter = 0;
		Fleet fromFleet = new Fleet();
		Planet fromHomePlanet = new Planet();

		for (String commantElement : commandElements) {
			if (counter == 0) {
				int fleetNumber = Utils.extractIntFromcommantElementString(commantElement);
				if (fleetNumber != 0) {
					FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
					if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
						fromFleet = aFleetAndHomePlanet.fleet;
						fromHomePlanet = aFleetAndHomePlanet.planet;
					}
				}
			}
			counter++;
		} 

		return new FleetAndPlanetDTO(fromFleet, fromHomePlanet);
	}
	
	private Object createFireFleetToDShipsCommand() {
		FleetAndPlanetDTO fromFleetFireToDShipsAndPlanets = findFromFleetFireToDShipsAndPlanets();
		return new FireFleetToDShips(fromFleetFireToDShipsAndPlanets.fleet, fromFleetFireToDShipsAndPlanets.planet, processCommand, commandPlayer);
	}

	private TwoFleetTwoPlanetsDTO findFromFleetFireToFleetAndPlanets() {
		int counter = 0;
		Fleet fromFleet = new Fleet();
		Fleet toFleet = new Fleet();
		Planet fromHomePlanet = new Planet();
		Planet toHomePlanet = new Planet();

		for (String commantElement : commandElements) {
			if (counter == 0) {
				int fleetNumber = Utils.extractIntFromcommantElementString(commantElement);
				if (fleetNumber != 0) {
					FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
					if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
						fromFleet = aFleetAndHomePlanet.fleet;
						fromHomePlanet = aFleetAndHomePlanet.planet;
					}
				}
			} else if (counter == 1) {
				//Nichts zu tun
			} else {
				int fleetNumber = Utils.extractIntFromcommantElementString(commantElement);
				if (fleetNumber != 0) {
					FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
					if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
						toFleet = aFleetAndHomePlanet.fleet;
						toHomePlanet = aFleetAndHomePlanet.planet;
					}
				}
			}
			counter++;
		} 

		return new TwoFleetTwoPlanetsDTO(fromFleet, toFleet, fromHomePlanet, toHomePlanet);
	}

	private Object createFireFleetToFleetCommand() {
		TwoFleetTwoPlanetsDTO fromFleetFireToFleetAndPlanets = findFromFleetFireToFleetAndPlanets();
        return new FireFleetToFleet(fromFleetFireToFleetAndPlanets.fromFleet, fromFleetFireToFleetAndPlanets.toFleet, fromFleetFireToFleetAndPlanets.fromHomePlanet, fromFleetFireToFleetAndPlanets.toHomePlanet, processCommand, commandPlayer);
	}
/*
 *  func findFromFleetToDShipsAndPlanet() -> (fromFleet: Fleet, fromHomePlanet:Planet, shipsToTransfer: Int) {
        var counter = 0
        var fromFleet: Fleet = Fleet()
        var fromHomePlanet: Planet = Planet()
        var shipsToTransfer = 0

        for commantElement in commandElements {
            if counter == 0 {
                var fleetNumber = extractNumberString(commantElement).toInt()
                if fleetNumber != nil {
                    var aFleetAndHomePlanet = fleetAndHomePlanetWithNumber(planets, fleetNumber!)
                    if aFleetAndHomePlanet.fleet != nil && aFleetAndHomePlanet.homePlanet != nil {
                        fromFleet = aFleetAndHomePlanet.fleet!
                        fromHomePlanet = aFleetAndHomePlanet.homePlanet!
                    }
                }
            } else if counter == 1 {
                var aShipsToTransfer = extractNumberString(commantElement).toInt()
                if aShipsToTransfer != nil {
                    shipsToTransfer = aShipsToTransfer!
                }
            }
            
            counter++
        }
        return (fromFleet, fromHomePlanet, shipsToTransfer)
    }

  

 */

	private FleetPlanetAndShipsDTO findFromFleetToDShipsAndPlanet() {
		int counter = 0;
		Fleet fromFleet = new Fleet();
		Planet fromHomePlanet = new Planet();
		int shipsToTransfer = 0;

		for (String commantElement : commandElements) {
			if (counter == 0) {
				int fleetNumber = Utils.extractIntFromcommantElementString(commantElement);
				if (fleetNumber != 0) {
					FleetAndPlanetDTO aFleetAndHomePlanet = Fleet.fleetAndHomePlanetWithNumber(planets, fleetNumber);
					if (aFleetAndHomePlanet.fleet != null && aFleetAndHomePlanet.planet != null) {
						fromFleet = aFleetAndHomePlanet.fleet;
						fromHomePlanet = aFleetAndHomePlanet.planet;
					}
				}
			} else if (counter == 1) {
				int aShipsToTransfer = Utils.extractIntFromcommantElementString(commantElement);
				if (aShipsToTransfer != 0) {
					shipsToTransfer = aShipsToTransfer;
				}
			}

			counter++;
		} 
		return new FleetPlanetAndShipsDTO(fromFleet, fromHomePlanet, shipsToTransfer);
	}
	
	private Object createTransferShipsFleetToDShipsCommand() {
		FleetPlanetAndShipsDTO fromFleetToDShipsAndPlanet = findFromFleetToDShipsAndPlanet();
		return new TransferShipsFleetToDShips(fromFleetToDShipsAndPlanet.fleet, fromFleetToDShipsAndPlanet.planet, fromFleetToDShipsAndPlanet.shipsToTransfer, processCommand, commandPlayer);
	}

	private Object createTransferShipsFleetToFleetCommand() {
		// TODO Auto-generated method stub
		return null;
	}
}
