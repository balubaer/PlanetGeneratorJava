package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalPhaseCoreGame {

	ArrayList<Planet> planets;
	HashMap<String, Player> allPlayerDict;
	public FinalPhaseCoreGame(ArrayList<Planet> aPlanetArray,
			HashMap<String, Player> aAllPlayerDict) {
		 planets = aPlanetArray;
		 allPlayerDict = aAllPlayerDict;
	}

	private boolean isSomeBodyOnPlanet(Planet planet) {
		/*
		 *    var result = false
        
        for (playerName, player) in allPlayerDict {
            if Player.isPlayOnPlanetWithPlayer(player, planet: planet) {
                result = true
                break
            }
        }
        return result

		 */
		// TODO Auto-generated method stub
		return false;
	}

	private void checkFireResults(Planet planet) {
		/*
		 *   //DShips
        planet.dShips -= planet.hitedShotsDShips/2
        if planet.dShips < 0 {
            planet.dShips = 0
        }
        planet.hitedShotsDShips = 0
        //Fleets
        for fleet in planet.fleets {
            if fleet.fleetMovements.count > 0 {
                fleet.ships -= fleet.hitedShots/4
            } else {
                fleet.ships -= fleet.hitedShots/2
            }
            fleet.hitedShots = 0
            if fleet.ships < 0 {
                fleet.ships = 0
            }
        }

		 */
		// TODO Auto-generated method stub
		
	}

	private void checkFleetMovement(Planet planet) {
		/*
		 *  var counter = 0
        for fleet in planet.fleets {
            var fleetMovementCount = fleet.fleetMovements.count
            
            if fleetMovementCount > 0 {
                if fleet.ships > 0 {
                    var movementCount = 1
                    for fleetMovement in fleet.fleetMovements {
                        if fleetMovement.isMovementDone == false {
                            var fromPlanet = fleetMovement.fromPlanet
                            var toPlanet = fleetMovement.toPlanet
                            fromPlanet?.fleets.removeObject(fleet)
                            fromPlanet?.fleetMovements.append(fleetMovement)
                            toPlanet?.fleets.append(fleet)
                            fleetMovement.isMovementDone = true
                            
                            if isAmbushPlanet(toPlanet, passingFleet: fleet, movementCount: movementCount) {
                                var firePower = self.getFirePowerForAmbushPlanet(toPlanet)
                                fleet.ships -= firePower
                                if fleet.ships < 0 {
                                    fleet.ships = 0
                                }
                                if toPlanet != nil && fleet.player != nil {
                                    toPlanet!.addHitAmbushPlayer(fleet.player!)
                                }
                            }
                            movementCount++
                        }
                    }
                }
            }
        }

		 */
		// TODO Auto-generated method stub
		
	}

	
	private void checkOwnership(Planet planet) {
		// TODO Auto-generated method stub
		/*
		 *    var players = self.getPlayersFromFleets(planet.fleets)

        //planet
        if planet.player == nil {
            if players.count == 1 {
                    planet.player = players[0]
            }
        } else {
            if planet.dShips == 0 {
                var player = planet.player!
                
                if players.count == 1 {
                    
                    if player != players[0] {
                        planet.player = players[0]
                    }
                }
            }
        }
        //fleets
        if players.count == 1 {
            for fleet in planet.fleets {
                fleet.player = players[0]
            }
        } else {
            for fleet in planet.fleets {
                if fleet.ships == 0 {
                    fleet.player = nil
                }
            }
        }

		 */
		
		
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
