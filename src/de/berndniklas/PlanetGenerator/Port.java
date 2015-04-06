package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Port {
	public ArrayList<Planet> planets;
	public Planet planet;
	public Port() {
		planets = new ArrayList<Planet>();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder(1000);
		if (planet != null) {
			sb.append(this.planet.toString());
		} else {
			sb.append("W?");
		}
		int connectionCount = planets.size();
		if (connectionCount > 0) {
			int counter = 0;
			sb.append("(");
			Collections.sort(planets);
			Iterator<Planet> it = planets.iterator();
			while (it.hasNext()) {
				Planet aPlanet = it.next();
				int number = aPlanet.number;
				sb.append(number);

				if (counter < (connectionCount - 1)) {
					sb.append(",");
				}
				counter++;
			}
			sb.append(")");
		}
		return sb.toString();
	}
	
	public boolean hasConnectionToPlanet(Planet aPlanet){
		return Planet.containsPlanet(planets, aPlanet);
	}
	
	public boolean equals(Port o) {
		boolean result = this.planets.size() == o.planets.size();
		
		if (result) {
			ArrayList<Planet> otherPlanets = o.planets;
			ArrayList<Planet> thisPlanets = this.planets;
			for (Planet planet : thisPlanets) {
				if (Planet.containsPlanet(otherPlanets, planet) == false) {
					result = false;
					break;
				}
			}
		}
		
		if (result) {
			result = this.planet.equals(o.planet);
		}

		return result;
	}
}
