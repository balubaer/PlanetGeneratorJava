package de.berndniklas.PlanetGenerator;

public class MoveCommand extends Command implements ExecuteCommand {

	public MoveCommand(String aString, Player aPlayer, TurnPhase aTurnPhase) {
		super(aString, aPlayer, aTurnPhase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * //FnnnWmmm FnnnWmmmWooo FnnnWmmmWoooWrrr
class MoveCommand: Command, ExecuteCommand{
    var fleet: Fleet
    var planets: Array <Planet>
    var homePlanet: Planet
    var count = 0
    
    init(aFleet: Fleet, aHomePlanet: Planet, aPlanetArray: Array <Planet>, aString: String, aPlayer: Player) {
        fleet = aFleet
        homePlanet = aHomePlanet
        planets = aPlanetArray

        super.init(aString: aString, aPlayer: aPlayer, aTurnPhase: TurnPhase.Movement)
    }
    
    func executeCommand() {
        if player.name == fleet.player?.name {
            var fromPlanet: Planet = homePlanet
            var toPlanet: Planet
            var isError = false
            for planet in planets {
                toPlanet = planet
                if fromPlanet.hasConnectionToPlanet(toPlanet) {
                    fromPlanet = planet
                } else {
                    //TODO: Fehler
                    isError = true
                    break
                }
                
                if fleet.ships == 0 {
                    isError = true
                }
                
                if isError == false {
                    if fleet.fired {
                        isError = true
                    }
                }
            }
            
            if isError == false {
                fromPlanet = homePlanet
                
                for planet in planets {
                    toPlanet = planet
                    var fleetMovement = FleetMovement()
                    var fleetCopy = Fleet()
                    fleetCopy.player = fleet.player
                    fleetCopy.number = fleet.number
                    fleetMovement.fleet = fleetCopy
                    fleetMovement.toPlanet = toPlanet
                    fleetMovement.fromPlanet = fromPlanet
                    
                    fleet.fleetMovements.append(fleetMovement)
                    
                    fromPlanet = toPlanet
                }
            }
        } else {
            //TODO: Fehler Flotte ist nicht vom Spieler
        }
    }
}

	 */
}
