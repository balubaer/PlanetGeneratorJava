package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Iterator;

public class Player implements Comparable<Player>{
	public String name;
	public int points;
	public Role role;
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder(1000);
		sb.append("[");
		if (name != null) {
			sb.append(name);
		}
		sb.append("]");

		return sb.toString();
	}
	
	public Player() {
		name = "NO Name";
	}
	
	public final static boolean isPlayerInFleetsWithPlayer(Player aPlayer, ArrayList<Fleet> aFleets) {
		boolean result = false;
		Iterator<Fleet> it = aFleets.iterator();
		while (it.hasNext()) {
			Fleet aFleet = it.next();
			if (aFleet.player != null) {
				if (aFleet.player.compareTo(aPlayer) == 0) {
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	public final static boolean isPlayerInFleetMovementWithPlayer(Player aPlayer, ArrayList<FleetMovement> fleetMovements) {
		boolean result = false;
		Iterator<FleetMovement> it = fleetMovements.iterator();
		while (it.hasNext()) {
			FleetMovement aFleetMovement = it.next();
			if (aFleetMovement.fleet != null) {
				if (aFleetMovement.fleet.player != null) {
					if (aFleetMovement.fleet.player.compareTo(aPlayer) == 0) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}	
	     
/*
 *   
    var points: Int = 0
    var role: Role?
    var ambushOff: Bool = false
     
    var description: String {
        var desc = "[\(name)]"
        if ambushOff {
            desc += "(Ambush aus)"
        }
        return desc
    }
     
     
     
    class func isPlayerInFleetMovementWithPlayer(player: Player, fleetMovements: Array <FleetMovement>) -> Bool {
        var result = false
        for fleetMovement in fleetMovements {
            if fleetMovement.fleet != nil {
                if fleetMovement.fleet!.player != nil {
                    var movementPlayer = fleetMovement.fleet!.player!
                    if movementPlayer == player {
                        result = true
                        break
                    }
                }
            }
        }
        return result
    }
     
    class func isPlanetOwnedByPlayer(player: Player, planet: Planet) -> Bool {
        var result = false
 
        if planet.player != nil {
            if planet.player! == player {
                result = true
            }
        }
        return result
    }
     
    class func isPlayOnPlanetWithPlayer(player: Player, planet: Planet) -> Bool {
        //Test Planet
        var result = self.isPlanetOwnedByPlayer(player, planet: planet)
         
        //Test Fleets
        if result == false {
            result = self.isPlayerInFleetsWithPlayer(player, fleets: planet.fleets)
        }
 
        return result
    }
     
    class func isPlayerInPlanetHitAmbuschPlayersWithPlayer(aPlayer: Player, hitAmbuschPlayers: Array <Player>) -> Bool {
        var result = false
         
        for player in hitAmbuschPlayers {
            if player == aPlayer {
                result = true
                break
            }
        }
        return result;
    }
     
    class func isPlanetOutPutForPlayer(player: Player, planet: Planet) -> Bool {
        //Test Planet
        var result = self.isPlanetOwnedByPlayer(player, planet: planet)
         
        //Test Fleets
        if result == false {
            result = self.isPlayerInFleetsWithPlayer(player, fleets: planet.fleets)
        }
         
        //Test FleetMovement
        if result == false {
            result = self.isPlayerInFleetMovementWithPlayer(player, fleetMovements: planet.fleetMovements)
        }
         
        //Test planet.hitAmbuschPlayers
        if result == false {
            result = self.isPlayerInPlanetHitAmbuschPlayersWithPlayer(player, hitAmbuschPlayers: planet.hitAmbuschPlayers)
        }
        return result
    }
     
}
 
 */
	@Override
	public int compareTo(Player o) {
		int result = this.name.compareTo(o.name);
		return result;
	}
	
	public boolean equals(Player o) {
		boolean result = this.name.equals(o.name);
		return result;
	}
}
