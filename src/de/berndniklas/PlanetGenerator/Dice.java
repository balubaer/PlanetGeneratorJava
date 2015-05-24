package de.berndniklas.PlanetGenerator;

import java.util.Random;

public class Dice {
	 int sides;
	    
	 public Dice() {
	        this.sides = 0;
	    }
	    
	 public Dice(int sideCount) {
	        this.sides = sideCount;
	    }
	    
	    public void setSites(int aSides) {
	        this.sides = aSides;
	    }
	    
	    public int roll() {
    	    Random r = new Random();

	    	int num = 1 + Math.abs(r.nextInt()) % this.sides;
	    	return num;
	    }

}
