package de.berndniklas.PlanetGenerator;

//DaaTxxFbb
public class TransferDShipsToFleet extends Command implements ExecuteCommand {

	Fleet toFleet;
	Planet fromHomePlanet;
	Planet toHomePlanet;
	int shipsToTransfer;
	    
	public TransferDShipsToFleet(Fleet aToFleet, Planet aFromHomePlanet,
			Planet aToHomePlanet, int aShipsToTransfer, String aProcessCommand,
			Player aCommandPlayer) {
		super(aProcessCommand, aCommandPlayer,TurnPhase.Transfer);
		toFleet = aToFleet;
		fromHomePlanet = aFromHomePlanet;
		toHomePlanet = aToHomePlanet;
		shipsToTransfer = aShipsToTransfer;
		
	}

	@Override
	public void executeCommand() {
		if (fromHomePlanet.player.name.equals(player.name)) {
            boolean isError = false;
            
            if (isError == false) {
                if (!fromHomePlanet.equals(toHomePlanet))  {
                    //TODO: Fehler art zufügen
                    isError = true;
                }
                if (isError == false) {
                    if (fromHomePlanet.dShips < shipsToTransfer) {
                        //TODO: Fehler art zufügen
                        isError = true;
                    }
                }
                //TODO: Check Owner Man kann einer Neutralen Flotte keine Schiffe Transverieren
            }
            
            //TODO: Weiter Tests implementieren
            
            if (isError == false) {
                fromHomePlanet.dShips -= shipsToTransfer;
                toFleet.ships += shipsToTransfer;
            }
        } else {
            //TODO: Fehler Welt ist nicht vom Spieler
        }
	}

}
