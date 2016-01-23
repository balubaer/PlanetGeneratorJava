package de.berndniklas.PlanetGenerator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FleetMovement {
	public Fleet fleet;
	public Planet toPlanet;
	public Planet fromPlanet;
	boolean isMovementDone;


	public FleetMovement() {
		isMovementDone = false;
	}

	public String toString() {
		String result = "(---)";
		if (fleet != null && toPlanet != null) {
			StringBuilder sb = new StringBuilder(256);
			sb.append(fleet.name());
			sb.append("-->");
			sb.append(toPlanet.name());
			result = sb.toString();
		}
		return result;
	}
	
	public void addXMLPassingOnParent(Document doc, Element parent) {
		if (fleet != null && toPlanet != null) {
			 Element childElementConnect = doc.createElement("passing");

			 Attr attr = doc.createAttribute("index");
	         attr.setValue(Integer.valueOf(fleet.number).toString());
	         childElementConnect.setAttributeNode(attr);
	         if (fleet.player != null) {
	        	 attr = doc.createAttribute("owner");
		         attr.setValue(fleet.player.name);
		         childElementConnect.setAttributeNode(attr);
	         }
	         attr = doc.createAttribute("toWorld");
	         attr.setValue(Integer.valueOf(toPlanet.number).toString());
	         childElementConnect.setAttributeNode(attr);
		}
	}
}
