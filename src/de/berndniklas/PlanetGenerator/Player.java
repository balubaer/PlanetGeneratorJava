package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;


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
	 
	public static boolean containsPlayer(ArrayList<Player> players, Player aPlayer) {
		boolean result = false;
		Iterator<Player> it = players.iterator();
		while (it.hasNext()) {
			Player aPlanetFromArrayList = it.next();
			if (aPlanetFromArrayList.equals(aPlayer)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
/*
 *   
    var ambushOff: Bool = false
     
    var description: String {
        var desc = "[\(name)]"
        if ambushOff {
            desc += "(Ambush aus)"
        }
        return desc
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

	public static boolean isPlanetOutPutForPlayer(Player player, Planet planet) {
		   //Test Planet
        boolean result = isPlanetOwnedByPlayer(player, planet);
        
        //Test Fleets
        if (result == false) {
            result = isPlayerInFleetsWithPlayer(player, planet.fleets);
        }
        
        //Test FleetMovement
        if (result == false) {
            result = isPlayerInFleetMovementWithPlayer(player, planet.fleetMovements);
        }
        
        //Test planet.hitAmbuschPlayers
        if (result == false) {
            result = isPlayerInPlanetHitAmbuschPlayersWithPlayer(player, planet.hitAmbuschPlayers);
        }
        return result;
	}

	private static boolean isPlayerInPlanetHitAmbuschPlayersWithPlayer(
			Player aPlayer, ArrayList<Player> hitAmbuschPlayers) {
		boolean result = false;

		for (Player player : hitAmbuschPlayers) {
			if (player.equals(aPlayer)) {
				result = true;
				break;
			}
		}
		return result;
	}

	private static boolean isPlanetOwnedByPlayer(Player player, Planet planet) {
		boolean result = false;

		if (planet.player != null) {
			if (planet.player == player) {
				result = true;
			}
		}
		return result;
	}

	public static boolean isPlayOnPlanetWithPlayer(Player player, Planet planet) {
		//Test Planet
		 boolean result = isPlanetOwnedByPlayer(player, planet);

        //Test Fleets
        if (result == false) {
            result = isPlayerInFleetsWithPlayer(player, planet.fleets);
        }

        return result;
	}

	public Element getXMLElement(Document doc) {
		 Element player = doc.createElement("player");
       
		 Attr attr = doc.createAttribute("accountId");
         attr.setValue("01601386");
         player.setAttributeNode(attr);
         
		 attr = doc.createAttribute("handle");
         attr.setValue(name);
         player.setAttributeNode(attr);
         
		 attr = doc.createAttribute("fullName");
         attr.setValue(name);
         player.setAttributeNode(attr);

         attr = doc.createAttribute("stillInGame");
         attr.setValue("True");
         player.setAttributeNode(attr);

         attr = doc.createAttribute("prevScore");
         attr.setValue(Integer.toString(points));
         player.setAttributeNode(attr);

         attr = doc.createAttribute("score");
         attr.setValue(Integer.toString(points));
         player.setAttributeNode(attr);

		return player;
	}
	
}
