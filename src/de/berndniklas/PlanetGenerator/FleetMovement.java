package de.berndniklas.PlanetGenerator;

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

}
