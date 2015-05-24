package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class FinalPhaseCoreGame {

	ArrayList<Planet> planets;
	HashMap<String, Player> allPlayerDict;
	public FinalPhaseCoreGame(ArrayList<Planet> aPlanetArray,
			HashMap<String, Player> aAllPlayerDict) {
		 planets = aPlanetArray;
		 allPlayerDict = aAllPlayerDict;
	}

	private boolean isSomeBodyOnPlanet(Planet planet) {
		boolean result = false;
		Collection<String> playerKeys = allPlayerDict.keySet();

		for (Iterator<String> iterator = playerKeys.iterator(); iterator.hasNext();) {
			String playerName = iterator.next();
			Player player = allPlayerDict.get(playerName);
			if (Player.isPlayOnPlanetWithPlayer(player, planet)) {
				result = true;
			}
		}
		return result;
	}

	private void checkFireResults(Planet planet) {
        //DShips
        planet.dShips -= planet.hitedShotsDShips/2;
        if (planet.dShips < 0) {
            planet.dShips = 0;
        }
        planet.hitedShotsDShips = 0;
        //Fleets
        for (Fleet fleet : planet.fleets) {
        	 if (fleet.fleetMovements.size() > 0) {
                 fleet.ships -= fleet.hitedShots/4;
             } else {
                 fleet.ships -= fleet.hitedShots/2;
             }
             fleet.hitedShots = 0;
             if (fleet.ships < 0) {
                 fleet.ships = 0;
             }
		}
	}
	
	private boolean isAmbushFromMovementCount(int movementCount, int movementHoleCount) {
		boolean result = false;

		switch (movementHoleCount) {
		case 2:
			switch (movementCount) {
			case 1:
				result = true;
				break;
			default:
				result = false;
				break;
			}
			break;
		case 3:
			switch (movementCount) {
			case 1:
			case 2:
				result = true;
				break;
			default:
				result = false;
				break;
			}
		default:
			result = false;
			break;
		}

		return result;
	}

	private boolean isAmbushPlanet(Planet planet, Fleet passingFleet, int movementCount) {
		boolean result = false;
		if (planet.ambushOff == false) {
			if (isAmbushFromMovementCount(movementCount, passingFleet.fleetMovements.size())) {
				if (planet != null) {
					Player planetPlayer = planet.player;
					Player fleetPlayer = passingFleet.player;

					if (planetPlayer != null) {
						if (fleetPlayer != null) {
							if (!planetPlayer.equals(fleetPlayer)) {
								result = true;
							}
						}
					}
				}
			}
		}

		return result;
	}
	
	
	private int getFirePowerForAmbushPlanet(Planet planet) {
		int firePower = 0;

		if (planet != null) {
			if (planet.dShipsFired == false) {
				firePower += planet.dShips;
				planet.dShipsAmbush = true;
			}
			for (Fleet fleet : planet.fleets) {
				if (fleet.fired == false) {
					if (fleet.moved == false) {
						firePower += fleet.ships;
						fleet.ambush = true;
					}
				}
			} 
		}
		return firePower;
	}
	
	private void checkFleetMovement(Planet planet) {
		ArrayList<Fleet> fleets = new ArrayList<Fleet>();
		for (Fleet fleet : planet.fleets) {
			fleets.add(fleet);
		}
		
		for (Fleet fleet : fleets) {
			int fleetMovementCount = fleet.fleetMovements.size();

			if (fleetMovementCount > 0) {
				if (fleet.ships > 0) {
					int movementCount = 1;
					for (FleetMovement fleetMovement : fleet.fleetMovements) {

						if (fleetMovement.isMovementDone == false) {
							Planet fromPlanet = fleetMovement.fromPlanet;
							Planet toPlanet = fleetMovement.toPlanet;

							fromPlanet.fleets.remove(fleet);
							fromPlanet.fleetMovements.add(fleetMovement);
							toPlanet.fleets.add(fleet);
							fleetMovement.isMovementDone = true;

							if (isAmbushPlanet(toPlanet, fleet,  movementCount)) {
								int firePower = this.getFirePowerForAmbushPlanet(toPlanet);
								fleet.ships -= firePower;
								if (fleet.ships < 0) {
									fleet.ships = 0;
								}
								if (toPlanet != null && fleet.player != null) {
									toPlanet.addHitAmbushPlayer(fleet.player);
								}
							}
							movementCount++;
						}
					}
				}
			}
		}
	}

	private void checkOwnership(Planet planet) {
		ArrayList <Player> players = this.getPlayersFromFleets(planet.fleets);
		
        //planet
        if (planet.player == null) {
            if (players.size() == 1) {
                    planet.player = players.get(0);
            }
        } else {
            if (planet.dShips == 0) {
                Player player = planet.player;
                
                if (players.size() == 1) {
                	if (planet != null) {
                		if (!player.equals(players.get(0))) {
                			planet.player = players.get(0);
                		}
                	}
                }
            }
        }
        //fleets
        if (players.size() == 1) {
            for (Fleet fleet :  planet.fleets) {
                fleet.player = players.get(0);

			}
        } else {
            for (Fleet fleet :  planet.fleets) {
                if (fleet.ships == 0) {
                    fleet.player = null;
                }
            }
        }
	}
	
	private ArrayList<Player> getPlayersFromFleets(ArrayList<Fleet> fleets) {
		ArrayList<Player> players = new ArrayList<Player>();

		for (Fleet fleet : fleets) {
			if (fleet.ships > 0) {
				Player player = fleet.player;

				if (player != null) {
					if (Player.containsPlayer(players, player) == false) {
						players.add(player);
					}
				}
			}
		} 

		return players;
	}

	public void doFinal() {
	    for (Planet planet : planets) {
	    	 if (this.isSomeBodyOnPlanet(planet)){
	    		 this.checkFireResults(planet);
	    		 this.checkFleetMovement(planet);
	            }
		} 
	    for (Planet planet : planets) {
	    	 if (this.isSomeBodyOnPlanet(planet)){
	    		 this.checkOwnership(planet);
            }
        }
	}
	
}
