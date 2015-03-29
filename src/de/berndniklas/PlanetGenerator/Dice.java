package de.berndniklas.PlanetGenerator;

import java.util.BitSet;
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
	    	 BitSet b = new BitSet();
	    	    Random r = new Random();

	    	    System.out.print("Mein Lottotip: ");
	    	    int cnt = 0;
	    	    while (cnt < 6) {
	    	      int num = 1 + Math.abs(r.nextInt()) % 49;
	    	      if (!b.get(num)) {
	    	        b.set(num);
	    	        ++cnt;
	    	      }
	    	    }
	    	    for (int i = 1; i <= 49; ++i) {
	    	      if (b.get(i)) {
	    	        System.out.print(i + " ");
	    	      }
	    	    }
	    	    System.out.println("");
	    	
	    	/*
	        int resultRandom = Int(arc4random_uniform(UInt32(sides)))
	            
	        var result = resultRandom + 1

	        return result*/
	    }

}
