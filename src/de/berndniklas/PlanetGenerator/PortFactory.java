package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;

public class PortFactory {
	ArrayList<Planet> planetsWithEnoughConnections = new ArrayList<Planet>();
	ArrayList<Planet> workingPlanets;
	int planetsCount;
	Dice dice;

	public PortFactory() {
		this.planetsCount = 0;
		dice = new Dice();
		workingPlanets = new ArrayList<Planet>();
	}
	
	private boolean hasPlanetMaxConnetion(Planet aPlanet) {
		boolean result = false;
		if (aPlanet.port != null) {
			int  connectionCount = aPlanet.port.planets.size();
			if (connectionCount == 5) {
				result = true;
			}
		}
		return result;
	}
	
	private boolean hasPlanetEnoughConnection(Planet aPlanet) {
		boolean result = false;
		if (aPlanet.port != null) {
			int  connectionCount = aPlanet.port.planets.size();
            if (connectionCount >= 2 && connectionCount <= 5) {
				result = true;
			}
		}
		return result;
	}
	
	private void addPlanetWithEnoughConnectionTest(Planet aPlanet) {
		if (this.hasPlanetEnoughConnection(aPlanet)) {
			if (planetsWithEnoughConnections.contains(aPlanet) == false) {
				planetsWithEnoughConnections.add(aPlanet);
			}
		}
	}
	
	private void removePlanetFromWorkArrayWithMaxConnectionTest(Planet aPlanet) {
		if (this.hasPlanetMaxConnetion(aPlanet)) {
			workingPlanets.remove(aPlanet);
			System.out.print("Planet [");
			System.out.print(aPlanet.number);
			System.out.println("] ist fertig.");

			System.out.print("WorkingPlaneten: Anzahl [");
			System.out.print(workingPlanets.size());
			System.out.println("]");

			System.out.print("Planeten mit ausreichend Verbindungen: Anzahl [");
			System.out.print(planetsWithEnoughConnections.size());
			System.out.println("]");
		}

	}
	
	private boolean isAllConnectionCreated() {
		boolean result = false;
		if (planetsCount == planetsWithEnoughConnections.size()) {
			result = true;
		}
		return result;
	}
	
	private boolean isPlanetForNewConnectionOK(Planet aPlanet) {
		boolean result = false;

		if (this.hasPlanetMaxConnetion(aPlanet) == false) {
			result = true;
		}
		return result;
	}
	
	private Planet getStartPlanetWithDiceAndPlanetArray() {
		Planet result = null;

		dice.setSites(workingPlanets.size());

		int indexNumber = dice.roll();
		int realIndex = indexNumber - 1;
		result = workingPlanets.get(realIndex);

		if (result != null) {
			boolean found = this.isPlanetForNewConnectionOK(result);

			while (!found) {
				indexNumber = dice.roll();
				realIndex = indexNumber - 1;
				result = workingPlanets. get(realIndex);

				found = this.isPlanetForNewConnectionOK(result);
			}
		}

		return result;
	}
	
	private boolean isEndPlanetForNewConnectionOK(Planet aEndPlanet, Planet aStartPlanet) {
		boolean result = false;

		if (aEndPlanet.number != aStartPlanet.number) {
			if (this.hasPlanetMaxConnetion(aEndPlanet) == false) {
				if (aEndPlanet.hasConnectionToPlanet(aStartPlanet) == false ){
					result = true;
				}
			}
		}
		return result;
	}
	
	private Planet getEndPlanetWithDiceAndStartPlanet(Planet aStartPlanet) {
		Planet result = null;
		dice.setSites(workingPlanets.size());

		int indexNumber = dice.roll();
		int realIndex = indexNumber - 1;
		result = workingPlanets.get(realIndex);

		if (result != null) {
			boolean found = this.isEndPlanetForNewConnectionOK(result, aStartPlanet);

			while (!found) {
				indexNumber = dice.roll();
				realIndex = indexNumber - 1;
				result = workingPlanets.get(realIndex);

				found = this.isEndPlanetForNewConnectionOK(result, aStartPlanet);
			}
		}

		return result;
	}
	
	private void generatePlanetConnection() {
		Planet startPlanet = null;
		Planet endPlanet = null;

		while (!isAllConnectionCreated()) {
			startPlanet = this.getStartPlanetWithDiceAndPlanetArray();
			if (startPlanet != null) {
				endPlanet = this.getEndPlanetWithDiceAndStartPlanet(startPlanet);
				if (endPlanet != null) {
					startPlanet.port.planets.add(endPlanet);
					endPlanet.port.planets.add(startPlanet);
					addPlanetWithEnoughConnectionTest(startPlanet);
					this.addPlanetWithEnoughConnectionTest(endPlanet);
					this.removePlanetFromWorkArrayWithMaxConnectionTest(startPlanet);
					this.removePlanetFromWorkArrayWithMaxConnectionTest(endPlanet);

				}
			}
		}
	}
	
	public void createWithPlanetArray(ArrayList<Planet> planetArray) {
		planetsCount = planetArray.size();

		//All Planets get Ports
		for (Planet planet : planetArray) {
			workingPlanets.add(planet);

			Port port = new Port();
			port.planet = planet;
			port.planet.port = port;
		}
		this.generatePlanetConnection();		
	}
}
