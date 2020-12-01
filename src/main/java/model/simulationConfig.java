package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class simulationConfig {
	
	private static Properties prop;
	
	//Person config
	public static double chanceToKill=0.01;
	public static double chanceToGetSymptoms=0.4;
	public static double chanceToVisitPublic=0.1;
	public static boolean quarantineOnTest = false;
	public static boolean contactTracing = false;
	public static boolean maskEnforcement = false;
	public static Building publicEventBuilding=null;
	public static int testCooldown = 500;
	
	//Building config
	public static int lockdownPeriod = 1500;
	public static boolean lockdownOnTest = false;
	
	//Map config
	public static boolean showGUI = true;
	public static int size=300;
	public static int peopleCount=1000;
	public static int virusSeedCount=5;
	public static int closenessFactor=200;
	public static int houseCount=50;
	public static int officeCount=20;
	public static int publicCount=10;
	public static int houseSize=5;
	public static int houseSizeVariation=1;
	public static int officeSize=13;
	public static int officeSizeVariation=3;
	public static double publicMinSize=0.9;
	public static double publicMaxSize=0.95;
	public static double socialDistancing_radius=2.0;		// can be changed for experiments
	public static Boolean socialDistancing = false;			// initailize from checkbox/Jtoggle
	public static Boolean maskImposed = false;				// initailize from checkbox/Jtoggle
	public static Boolean imposeLockdown=false;				// drop down selection
	public static Boolean limitedReOpening=false;			// drop down selection
	public static Boolean liftLockdown=true;				// drop down selection
	public static double reopenCapacity = 0.5;				// can be changed for experiments (user input)
	
	
	//GUI config
	public static String houseColor="#f8fc03";
	public static String officeColor="#ffa600";
	public static String publicColor="#00ff00";
	public static String roadColor="#c8c8c8";
	public static String quarantinedColor="#f003fc";
	public static String infectedColor="#ff0000";
	public static String immuneColor="#03adfc";
	public static String personColor="#ffffff";
	public static String publicEventBuildingColor="#a83281";
	public static String lockdownColor="#fc5a03";
	
	
	
	public simulationConfig(String path) throws IOException {
		prop = readPropertiesFile(path);
		
		infectionPeriod = Integer.parseInt(prop.getProperty("infectionPeriod"));
		quarantinePeriod = Integer.parseInt(prop.getProperty("quarantinePeriod"));
		incubationPeriod = Integer.parseInt(prop.getProperty("incubationPeriod"));
		lethality= Double.parseDouble(prop.getProperty("lethality"));
		chanceToGetSymptoms = Double.parseDouble(prop.getProperty("chanceToGetSymptoms"));
	//	chanceToKill = Double.parseDouble(prop.getProperty("chanceToKill"));
		chanceToVisitPublic = Double.parseDouble(prop.getProperty("chanceToVisitPublic"));
		
		
	}
	
   public static Properties readPropertiesFile(String fileName) throws IOException {
	  FileInputStream fis = null;
	  Properties prop = null;
	  try {
	     fis = new FileInputStream(fileName);
	     prop = new Properties();
	     prop.load(fis);
	  } catch(FileNotFoundException fnfe) {
	     fnfe.printStackTrace();
	  } catch(IOException ioe) {
	     ioe.printStackTrace();
	  } finally {
	     fis.close();
	  }
	  return prop;
   }
}
