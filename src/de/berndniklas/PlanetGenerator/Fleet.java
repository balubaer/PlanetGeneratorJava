package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Fleet {
	public int number;
	public int ships;
	public int cargo;
	public boolean ambush;
	public int hitedShots;
	public ArrayList<FleetMovement> fleetMovements;
	public boolean fired;
	public String firesTo;
	public String firesToCommand;
	public boolean moved;
		

	//TODO: niklas Kunstwerke ... V70:Plastik Mondstein
	//TODO: niklas schenken

	public Player player;

	public Fleet() {
		fleetMovements = new ArrayList<FleetMovement>();
		moved = false;
		fired = false;
	}

	public String name() {
		StringBuilder sb = new StringBuilder(50);
		sb.append("F");
		sb.append(number);
		if (player != null) {
			sb.append(player.toString());

		} else {
			sb.append("[---]");
		}

		return sb.toString();
	}

	public boolean moved() {
		boolean moved = (fleetMovements.size() > 0);
		return moved;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(256);
		sb.append(this.name());
		sb.append(" = ");
		sb.append(ships);

		String resouceString = this.createInfoString();

		if (resouceString.length() != 0) {
			sb.append(" ");
			sb.append(resouceString);
		}
		return sb.toString();
	}	 


	private String createInfoString(){
		StringBuilder sb = new StringBuilder(50);

		ArrayList<String> infoArray = new ArrayList<String>();
		String result = "";

		if (this.moved() == true) {
			infoArray.add("bewegt");
		}
		if (ambush == true) {
			infoArray.add("Ambush");
		}
		if (fired == true) {
			sb.append("feuert auf ");
			sb.append(firesTo);
			infoArray.add(sb.toString());
			sb.setLength(0);
		}
		if (cargo != 0) {
			sb.append("Fracht=");
			sb.append(cargo);
			infoArray.add(sb.toString());
			sb.setLength(0);
		}

		if (infoArray.size() != 0) {

			result = Utils.createBracketAndCommarStringWithStringArray(infoArray);
		}

		return result;
	}

	public static FleetAndPlanetDTO fleetAndHomePlanetWithNumber(ArrayList<Planet> planetArray, int number){
		FleetAndPlanetDTO result = new FleetAndPlanetDTO();
		for (Planet planet : planetArray) {
			ArrayList<Fleet> fleets = planet.fleets;
			for (Fleet aFleet : fleets) {
				if (aFleet.number == number) {
					result.fleet = aFleet;
					result.planet = planet;
					break;
				}
			}
		}

		return result;
	}

	public void addXMLFleetOnParent(Document doc, Element parent) {
		 Element childElementFleet = doc.createElement("fleet");

		 Attr attr = doc.createAttribute("completeInfo");
         attr.setValue("True");
         childElementFleet.setAttributeNode(attr);
         
         if (firesToCommand != null && firesToCommand.equals("") == false)  {
    		 attr = doc.createAttribute("fired");
             attr.setValue(firesToCommand);
             childElementFleet.setAttributeNode(attr);
         }

         attr = doc.createAttribute("index");
         attr.setValue(Integer.toString(number));
         childElementFleet.setAttributeNode(attr);
         
         String movedString = "False";
         if (moved) {
        	 movedString = "True";
         }

		 attr = doc.createAttribute("moved");
         attr.setValue(movedString);
         childElementFleet.setAttributeNode(attr);
         
         if (player != null) {
        	 attr = doc.createAttribute("owner");
             attr.setValue(player.name);
             childElementFleet.setAttributeNode(attr);
             
             attr = doc.createAttribute("prevOwner");
             attr.setValue(player.name);
             childElementFleet.setAttributeNode(attr);
         }
         
         attr = doc.createAttribute("ships");
         attr.setValue(Integer.toString(ships));
         childElementFleet.setAttributeNode(attr);
         
         parent.appendChild(childElementFleet);
	}
}
