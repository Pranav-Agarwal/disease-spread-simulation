package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class simulationConfig {
	
	private static Properties prop;
	
	//Person config
	public static int infectionPeriod=3000;
	public static int quarantinePeriod=3000;
	public static int incubationPeriod=1000;
	public static double chanceToGetSymptoms=0.4;
	public static double chanceToKill=0.01;
	public static double chanceToVisitPublic=0.1;
	public static boolean quarantineOnTest = false;
	public static boolean contactTracing = false;
	public static boolean maskEnforcement = false;
	
	//Building config
	public static int lockdownPeriod = 1500;
	public static boolean lockdownOnTest = false;
	
	//Map config
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
	
	//GUI config
	public static String houseColor="#f8fc03";
	public static String officeColor="#ffa600";
	public static String publicColor="#00ff00";
	public static String roadColor="#c8c8c8";
	public static String quarantinedColor="#03adfc";
	public static String infectedColor="#ff0000";
	public static String immuneColor="#14e31f";
	public static String personColor="#ffffff";
	public static String publicEventBuildingColor="#a83281";
	public static String lockdownColor="#fc5a03";
	
	
	
	public simulationConfig(String path) throws IOException {
		prop = readPropertiesFile(path);
		
		infectionPeriod = Integer.parseInt(prop.getProperty("infectionPeriod"));
		quarantinePeriod = Integer.parseInt(prop.getProperty("quarantinePeriod"));
		incubationPeriod = Integer.parseInt(prop.getProperty("incubationPeriod"));
		chanceToGetSymptoms = Double.parseDouble(prop.getProperty("chanceToGetSymptoms"));
		chanceToKill = Double.parseDouble(prop.getProperty("chanceToKill"));
		chanceToVisitPublic = Double.parseDouble(prop.getProperty("chanceToVisitPublic"));
		quarantineOnTest = Boolean.parseBoolean(prop.getProperty("quarantineOnTest"));
		contactTracing = Boolean.parseBoolean(prop.getProperty("contactTracing"));
		maskEnforcement = Boolean.parseBoolean(prop.getProperty("maskEnforcement"));
		lockdownPeriod = Integer.parseInt(prop.getProperty("lockdownPeriod"));
		lockdownOnTest = Boolean.parseBoolean(prop.getProperty("lockdownOnTest"));
		size=Integer.parseInt(prop.getProperty("size"));
		peopleCount=Integer.parseInt(prop.getProperty("peopleCount"));
		virusSeedCount=Integer.parseInt(prop.getProperty("virusSeedCount"));
		closenessFactor=Integer.parseInt(prop.getProperty("closenessFactor"));
		houseCount=Integer.parseInt(prop.getProperty("houseCount"));
		officeCount=Integer.parseInt(prop.getProperty("officeCount"));
		publicCount=Integer.parseInt(prop.getProperty("publicCount"));
		houseSize=Integer.parseInt(prop.getProperty("houseSize"));
		houseSizeVariation=Integer.parseInt(prop.getProperty("houseSizeVariation"));
		officeSize=Integer.parseInt(prop.getProperty("officeSize"));
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
