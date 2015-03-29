package de.berndniklas.PlanetGeneratorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.berndniklas.PlanetGenerator.Dice;

public class DiceTest {

	@Test
	public void test() {
		Dice dice = new Dice(6);

		for (int i = 0; i < 100000; i++) {
			int roll = dice.roll();
			System.out.println(roll);
			if (roll <= 0 && roll > 6) {
				fail("Falsche Zahlen");

			}
		}
		
		dice = new Dice(50);

		for (int i = 0; i < 100000; i++) {
			int roll = dice.roll();
			System.out.println(roll);
			if (roll <= 0 && roll > 50) {
				fail("Falsche Zahlen");

			}
		}
	}

}
