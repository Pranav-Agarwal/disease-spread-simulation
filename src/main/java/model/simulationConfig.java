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
	public static String quarantinedColor="#f003fc";
	public static String infectedColor="#ff0000";
	public static String immuneColor="#03adfc";
	public static String personColor="#ffffff";
	
	
	
	public simulationConfig(String path) throws IOException {
		prop = readPropertiesFile(path);
		
		infectionPeriod = Integer.parseInt(prop.getProperty("infectionPeriod"));
		quarantinePeriod = Integer.parseInt(prop.getProperty("quarantinePeriod"));
		incubationPeriod = Integer.parseInt(prop.getProperty("incubationPeriod"));
		chanceToGetSymptoms = Double.parseDouble(prop.getProperty("chanceToGetSymptoms"));
		chanceToKill = Double.parseDouble(prop.getProperty("chanceToKill"));
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
