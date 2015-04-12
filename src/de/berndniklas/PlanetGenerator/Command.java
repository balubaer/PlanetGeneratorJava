package de.berndniklas.PlanetGenerator;


public class Command implements Comparable<Command>{
	String string;
	Player player;
	   // var errors: Array <(Int, String)> = Array()
	TurnPhase turnPhase;
	    
	public Command (String aString, Player aPlayer, TurnPhase aTurnPhase) {
	        string = aString;
	        player = aPlayer;
	        turnPhase = aTurnPhase;
	    }

	@Override
	public int compareTo(Command com) {
		int result = 0;
		if (this.turnPhase.ordinal() < com.turnPhase.ordinal()) {
			result = -1;
		}
		if (this.turnPhase.ordinal() > com.turnPhase.ordinal()) {
			result = 1;
		}
		if (this.turnPhase == com.turnPhase) {
			result = 0;
		}
		return result;
	}

}
