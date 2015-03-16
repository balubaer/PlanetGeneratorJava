package de.berndniklas.PlanetGenerator;

import java.util.ArrayList;
import java.util.Iterator;

public class Utils {
	public static String createBracketAndCommarStringWithStringArray(ArrayList<String> aStringArray) {
		int counter = 0;
		int maxCounter = aStringArray.size() - 1;
		StringBuilder sb = new StringBuilder(1000);
		Iterator<String> it = aStringArray.iterator();

		sb.append("(");

			while (it.hasNext()) {
				String aString = it.next();
				sb.append(aString);

				if (counter < maxCounter) {
					sb.append(",");
    	        }
    	        counter++;
			}
			sb.append(")");
		return sb.toString();
	}
}
