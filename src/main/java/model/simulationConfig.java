package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class simulationConfig {
	
	private static Properties prop;
	
	//Person config
	public static double chanceToVisitPublic=0.1;
	public static int timeSpentInPublic = 100;  //TO ADD
	public static int timeSpentInOffice = 200;  //TO ADD
	public static int timeSpentInHome = 300;  //TO ADD

	//Simulation config
	public static int baseUpdateRate = 15;
	public static int lockdownPeriod = 1500;
	public static double limitedOccupancyPercentage=0.5;
	public static double socialDistancing_radius=2.0;
	public static int testCooldown = 500;
	
	//Simulation state - not taken from config, changed at runtime
	public static Boolean hasStarted = false;
	public static String mapType = "urban";  // take from dropdown
	public static String virusType = "influenza";  // take from dropdown
	public static Boolean socialDistancing = false;		
	public static Boolean limitedReOpening=false;
	public static Building publicEventBuilding=null;
	public static boolean quarantineOnTest = false;
	public static boolean contactTracing = false;
	public static boolean maskEnforcement = false;
	public static boolean lockdownOnTest = false;
	public static boolean officeLockdown = false; 
	public static boolean publicLockdown = false; 
	
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

	//Virus config
	public static int infectionPeriod = 1500;
	public static int quarantinePeriod = 1500;
	public static int incubationPeriod = 500;
	public static double lethality = 0.1;
	public static double chanceToGetSymptoms=0.4;
	public static double infectivity= 0.5;
	
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
		
		//Person config
		chanceToVisitPublic = Double.parseDouble(prop.getProperty("chanceToVisitPublic"));
		
		//Simulation config
		baseUpdateRate = Integer.parseInt(prop.getProperty("baseUpdateRate"));
		lockdownPeriod = Integer.parseInt(prop.getProperty("lockdownPeriod"));
		limitedOccupancyPercentage=Double.parseDouble(prop.getProperty("limitedOccupancyPercentage"));
		testCooldown = Integer.parseInt(prop.getProperty("testCooldown"));;
		socialDistancing_radius=Double.parseDouble(prop.getProperty("socialDistancing_radius"));
		
		//Virus config
		if (virusType.equals("influenza"))
			{infectionPeriod = Integer.parseInt(prop.getProperty("INFinfectionPeriod"));
			quarantinePeriod = Integer.parseInt(prop.getProperty("INFquarantinePeriod"));
			incubationPeriod = Integer.parseInt(prop.getProperty("INFincubationPeriod"));
			chanceToGetSymptoms = Double.parseDouble(prop.getProperty("INFchanceToGetSymptoms"));
			lethality = Double.parseDouble(prop.getProperty("INFlethality"));
			infectivity=Double.parseDouble(prop.getProperty("INFinfectivity"));}
		else if (virusType.equals("covid1")) {
			infectionPeriod = Integer.parseInt(prop.getProperty("COV1infectionPeriod"));
			quarantinePeriod = Integer.parseInt(prop.getProperty("COV1quarantinePeriod"));
			incubationPeriod = Integer.parseInt(prop.getProperty("COV1incubationPeriod"));
			chanceToGetSymptoms = Double.parseDouble(prop.getProperty("COV1chanceToGetSymptoms"));
			lethality = Double.parseDouble(prop.getProperty("COV1lethality"));
			infectivity=Double.parseDouble(prop.getProperty("COV1infectivity"));
		}
		else {
			infectionPeriod = Integer.parseInt(prop.getProperty("COV2infectionPeriod"));
			quarantinePeriod = Integer.parseInt(prop.getProperty("COV2quarantinePeriod"));
			incubationPeriod = Integer.parseInt(prop.getProperty("COV2incubationPeriod"));
			chanceToGetSymptoms = Double.parseDouble(prop.getProperty("COV2chanceToGetSymptoms"));
			lethality = Double.parseDouble(prop.getProperty("COV2lethality"));
			infectivity=Double.parseDouble(prop.getProperty("COV2infectivity"));
		}
		
		//Map config
		if (mapType.equals("rural")) {
			showGUI = Boolean.parseBoolean(prop.getProperty("r_showGUI"));
			size=Integer.parseInt(prop.getProperty("r_size"));
			peopleCount=Integer.parseInt(prop.getProperty("r_peopleCount"));
			virusSeedCount=Integer.parseInt(prop.getProperty("r_virusSeedCount"));
			closenessFactor=Integer.parseInt(prop.getProperty("r_closenessFactor"));
			houseCount=Integer.parseInt(prop.getProperty("r_houseCount"));
			officeCount=Integer.parseInt(prop.getProperty("r_officeCount"));
			publicCount=Integer.parseInt(prop.getProperty("r_publicCount"));
			houseSize=Integer.parseInt(prop.getProperty("r_houseSize"));
			houseSizeVariation=Integer.parseInt(prop.getProperty("r_houseSizeVariation"));
			officeSize = Integer.parseInt(prop.getProperty("r_officeSize"));
			officeSizeVariation=Integer.parseInt(prop.getProperty("r_officeSizeVariation"));
			publicMinSize=Double.parseDouble(prop.getProperty("r_publicMinSize"));
			publicMaxSize=Double.parseDouble(prop.getProperty("r_publicMaxSize"));
		}
		else if (mapType.equals("urban")) {
			showGUI = Boolean.parseBoolean(prop.getProperty("u_showGUI"));
			size=Integer.parseInt(prop.getProperty("u_size"));
			peopleCount=Integer.parseInt(prop.getProperty("u_peopleCount"));
			virusSeedCount=Integer.parseInt(prop.getProperty("u_virusSeedCount"));
			closenessFactor=Integer.parseInt(prop.getProperty("u_closenessFactor"));
			houseCount=Integer.parseInt(prop.getProperty("u_houseCount"));
			officeCount=Integer.parseInt(prop.getProperty("u_officeCount"));
			publicCount=Integer.parseInt(prop.getProperty("u_publicCount"));
			houseSize=Integer.parseInt(prop.getProperty("u_houseSize"));
			houseSizeVariation=Integer.parseInt(prop.getProperty("u_houseSizeVariation"));
			officeSize = Integer.parseInt(prop.getProperty("u_officeSize"));
			officeSizeVariation=Integer.parseInt(prop.getProperty("u_officeSizeVariation"));
			publicMinSize=Double.parseDouble(prop.getProperty("u_publicMinSize"));
			publicMaxSize=Double.parseDouble(prop.getProperty("u_publicMaxSize"));
		}
		else {
			showGUI = Boolean.parseBoolean(prop.getProperty("s_showGUI"));
			size=Integer.parseInt(prop.getProperty("s_size"));
			peopleCount=Integer.parseInt(prop.getProperty("s_peopleCount"));
			virusSeedCount=Integer.parseInt(prop.getProperty("s_virusSeedCount"));
			closenessFactor=Integer.parseInt(prop.getProperty("s_closenessFactor"));
			houseCount=Integer.parseInt(prop.getProperty("s_houseCount"));
			officeCount=Integer.parseInt(prop.getProperty("s_officeCount"));
			publicCount=Integer.parseInt(prop.getProperty("s_publicCount"));
			houseSize=Integer.parseInt(prop.getProperty("s_houseSize"));
			houseSizeVariation=Integer.parseInt(prop.getProperty("s_houseSizeVariation"));
			officeSize = Integer.parseInt(prop.getProperty("s_officeSize"));
			officeSizeVariation=Integer.parseInt(prop.getProperty("s_officeSizeVariation"));
			publicMinSize=Double.parseDouble(prop.getProperty("s_publicMinSize"));
			publicMaxSize=Double.parseDouble(prop.getProperty("s_publicMaxSize"));
		}
		
		//GUI props
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
