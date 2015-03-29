package de.berndniklas.PlanetGenerator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

public class PlanetGenerator {
	
	public static String loadTextResource(String pkgname, String fname)
			throws IOException
			{
			  String ret = null;
			  InputStream is = getResourceStream(pkgname, fname);
			  if (is != null) {
			    StringBuffer sb = new StringBuffer();
			    while (true) {
			      int c = is.read();
			      if (c == -1) {
			        break;
			      }
			      sb.append((char)c);
			    }
			    is.close();
			    ret = sb.toString();
			  }
			  return ret;
			}

	public static String getResourcePathForFile(String fname) {
		 Properties systemProperties = System.getProperties();
		 String path = systemProperties.getProperty("user.dir");
		 path = path + "/resources/" + fname;
		 return path;
	}
	
	private static InputStream getResourceStream(String pkgname, String fname) {
		/*String resname = "/" + pkgname.replace('.', '/') + "/" + fname;
		Class clazz = getClass();
		InputStream is = clazz.getResourceAsStream(resname);
		*/
		 Properties systemProperties = System.getProperties();
		 String path = systemProperties.getProperty("user.dir");
		 path = path + "/resources/" + fname;
		InputStream is = null;
		try {
			is = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

	
	public static void main(String[] args)
	{
		/*		 
		 try {
			String ausDatei = PlanetGenerator.loadTextResource(null, "hello.txt");
			 System.out.println(ausDatei);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */

		try {
			String plistPath = PlanetGenerator.getResourcePathForFile("PlanetGenerator.plist");
			File file = new File(plistPath);
			NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(file);
			NSNumber num = (NSNumber)rootDict.objectForKey("planetCount");
			int planetCount = num.intValue();
			System.out.print("planetCount:");
			System.out.println(planetCount);

			NSObject[] playerNamesFromPlist = ((NSArray)rootDict.objectForKey("player")).getArray();
			ArrayList<String> playerNames = new ArrayList<String>();
			for(NSObject playerName:playerNamesFromPlist) {
				playerNames.add(playerName.toString());
			}
			num = (NSNumber)rootDict.objectForKey("fleetCount");
			int fleetCount = num.intValue();
			num = (NSNumber)rootDict.objectForKey("fleetsOnHomePlanet");

			int fleetsOnHomePlanet = num.intValue();
			num = (NSNumber)rootDict.objectForKey("startShipsCount");
			int startShipsCount = num.intValue();
			num = (NSNumber)rootDict.objectForKey("distanceLevelHomes");
			int distanceLevelHomes = num.intValue();

			String playPath = rootDict.objectForKey("playPath").toString();
			String playName = rootDict.objectForKey("playName").toString();

			ArrayList<Planet> planets = new ArrayList<Planet>();

			//Create Planets
			for (int index = 1; index <= planetCount; index++) {
				Planet planet = new Planet();
				planet.number = index;
				planets.add(planet);

			}

			//Create Ports with PortFactory

			PortFactory portFactory = new PortFactory();

			portFactory.createWithPlanetArray(planets);

			FleetFactory fleetFactory = new FleetFactory(fleetCount);

			fleetFactory.createWithPlanetArray(planets);

			/*  NSObject[] parameters = ((NSArray)rootDict.objectForKey("Parameters")).getArray();
			  for(NSObject param:parameters) {
			    if(param.getClass().equals(NSNumber.class)) {
			      NSNumber num = (NSNumber)param;
			      switch(num.type()) {
			        case NSNumber.BOOLEAN : {
			          boolean bool = num.boolValue();
			          //...
			          break;
			        }
			        case NSNumber.INTEGER : {
			          long l = num.longValue();
			          //or int i = num.intValue();
			          //...
			          break;
			        }
			        case NSNumber.REAL : {
			          double d = num.doubleValue();
			          //...
			          break;
			        }
			    }
			    // else...
			  }*/
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		/*	 Properties systemProperties = System.getProperties();
			Enumeration enuProp = systemProperties.propertyNames();
			while (enuProp.hasMoreElements()) {
				String propertyName = (String) enuProp.nextElement();
				String propertyValue = systemProperties.getProperty(propertyName);
				System.out.println(propertyName + ": " + propertyValue);
			}*/
	}
}
