package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class simulationConfig {
	
	private static Properties prop;
	
	//Person config
	public static double chanceToKill=0.01;
	public static double chanceToVisitPublic=0.1;
	public static boolean quarantineOnTest = false;
	public static boolean contactTracing = false;
	public static boolean maskEnforcement = false;
	public static Building publicEventBuilding=null;
	public static int testCooldown = 500;

	//Building config
	public static int lockdownPeriod = 1500;
	public static boolean lockdownOnTest = false;
	public static double limitedOccupancyPercentage=0.5;
	
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
	public static int officeSize = 13;
	public static int officeSizeVariation=3;
	public static double publicMinSize=0.9;
	public static double publicMaxSize=0.95;
	public static double socialDistancing_radius=2.0;
	public static Boolean socialDistancing = false;		
	public static Boolean limitedReOpening=false;		

	
	//Virus config
	public static String virusType = "INF";  // take from dropdown
	public static int infectionPeriod = 1500;
	public static int quarantinePeriod = 1500;
	public static int incubationPeriod = 500;
	public static double lethality = 0.1;
	public static double chanceToGetSymptoms=0.4;
	
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
		virusType = prop.getProperty("virusType");
		if (virusType=="INF")
			{infectionPeriod = Integer.parseInt(prop.getProperty("INFinfectionPeriod"));
			quarantinePeriod = Integer.parseInt(prop.getProperty("INFquarantinePeriod"));
			incubationPeriod = Integer.parseInt(prop.getProperty("INFincubationPeriod"));
			chanceToGetSymptoms = Double.parseDouble(prop.getProperty("INFchanceToGetSymptoms"));
			lethality = Double.parseDouble(prop.getProperty("INFlethality"));}
		else if (virusType=="COV1") {
			infectionPeriod = Integer.parseInt(prop.getProperty("COV1infectionPeriod"));
			quarantinePeriod = Integer.parseInt(prop.getProperty("COV1quarantinePeriod"));
			incubationPeriod = Integer.parseInt(prop.getProperty("COV1incubationPeriod"));
			chanceToGetSymptoms = Double.parseDouble(prop.getProperty("COV1chanceToGetSymptoms"));
			lethality = Double.parseDouble(prop.getProperty("COV1lethality"));
		}
		else {
			infectionPeriod = Integer.parseInt(prop.getProperty("COV2infectionPeriod"));
			quarantinePeriod = Integer.parseInt(prop.getProperty("COV2quarantinePeriod"));
			incubationPeriod = Integer.parseInt(prop.getProperty("COV2incubationPeriod"));
			chanceToGetSymptoms = Double.parseDouble(prop.getProperty("COV2chanceToGetSymptoms"));
			lethality = Double.parseDouble(prop.getProperty("COV2lethality"));
		}
		
		chanceToKill = Double.parseDouble(prop.getProperty("chanceToKill"));
		chanceToVisitPublic = Double.parseDouble(prop.getProperty("chanceToVisitPublic"));
		quarantineOnTest = Boolean.parseBoolean(prop.getProperty("quarantineOnTest"));
		contactTracing = Boolean.parseBoolean(prop.getProperty("contactTracing"));
		maskEnforcement = Boolean.parseBoolean(prop.getProperty("maskEnforcement"));
		lockdownPeriod = Integer.parseInt(prop.getProperty("lockdownPeriod"));
		lockdownOnTest = Boolean.parseBoolean(prop.getProperty("lockdownOnTest"));
		limitedOccupancyPercentage=Double.parseDouble(prop.getProperty("limitedOccupancyPercentage"));
		showGUI = Boolean.parseBoolean(prop.getProperty("showGUI"));
		size=Integer.parseInt(prop.getProperty("size"));
		peopleCount=Integer.parseInt(prop.getProperty("peopleCount"));
		virusSeedCount=Integer.parseInt(prop.getProperty("virusSeedCount"));
		closenessFactor=Integer.parseInt(prop.getProperty("closenessFactor"));
		houseCount=Integer.parseInt(prop.getProperty("houseCount"));
		officeCount=Integer.parseInt(prop.getProperty("officeCount"));
		publicCount=Integer.parseInt(prop.getProperty("publicCount"));
		houseSize=Integer.parseInt(prop.getProperty("houseSize"));
		houseSizeVariation=Integer.parseInt(prop.getProperty("houseSizeVariation"));
		officeSize = Integer.parseInt(prop.getProperty("officeSize"));
		officeSizeVariation=Integer.parseInt(prop.getProperty("officeSizeVariation"));
		publicMinSize=Double.parseDouble(prop.getProperty("publicMinSize"));
		publicMaxSize=Double.parseDouble(prop.getProperty("publicMaxSize"));
		houseColor=prop.getProperty("houseColor");
		officeColor=prop.getProperty("officeColor");
		publicColor=prop.getProperty("publicColor");
		roadColor=prop.getProperty("roadColor");
		quarantinedColor=prop.getProperty("quarantinedColor");
		infectedColor=prop.getProperty("infectedColor");
		immuneColor=prop.getProperty("immuneColor");
		personColor=prop.getProperty("personColor");	
		publicEventBuildingColor=prop.getProperty("publicEventBuildingColor");
		lockdownColor=prop.getProperty("lockdownColor");
		
		
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
