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
	
	static public Integer str2Int(String str) {
	    Integer result = null;
	    if (null == str || 0 == str.length()) {
	        return null;
	    }
	    try {
	        result = Integer.parseInt(str);
	    } 
	    catch (NumberFormatException e) {
	        String negativeMode = "";
	        if(str.indexOf('-') != -1)
	            negativeMode = "-";
	        str = str.replaceAll("-", "" );
	        if (str.indexOf('.') != -1) {
	            str = str.substring(0, str.indexOf('.'));
	            if (str.length() == 0) {
	                return (Integer)0;
	            }
	        }
	        String strNum = str.replaceAll("[^\\d]", "" );
	        if (0 == strNum.length()) {
	            return null;
	        }
	        result = Integer.parseInt(negativeMode + strNum);
	    }
	    return result;
	}
	
	public static String extractNumberString(String aString)  {
		Integer integer =  str2Int(aString);
	    return integer.toString();
	}

}
