package model;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class simulationConfigTest {

	
	//Setup varibales
	simulationConfig sc;
	
	
	//Person config
		double chanceToVisitPublic=0.1;


	//Simulation config
		int baseUpdateRate = 10;
		int lockdownPeriod = 1500;
		double limitedOccupancyPercentage=0.01;
		int testCooldown = 500;
		boolean testMode = false;
	
	//Influenza Virus config
		int INFinfectionPeriod = 1400;
		int INFquarantinePeriod = 1400;
		int INFincubationPeriod = 300;
		double INFchanceToGetSymptoms=0.8;	
		double INFlethality = 1.01;
		double INFspreadRange = 5.0;
		double INFinfectivity= 1.6;


	// COV1 Virus config
		int COV1infectionPeriod=1500;
		int COV1quarantinePeriod = 1500;
		int COV1incubationPeriod=500;
		double COV1chanceToGetSymptoms=0.6;		
		double COV1lethality=1.15;
		double COV1spreadRange=4.0;
		double COV1infectivity=1.5;

	// COV2 Virus config
		int COV2infectionPeriod=2100;
		int COV2quarantinePeriod = 2100;
		int COV2incubationPeriod=700;
		double COV2chanceToGetSymptoms=0.4;
		double COV2lethality=1.05;
		double COV2spreadRange=3.0;
		double COV2infectivity=1.7;
	
	
	// rural Map config
		boolean r_showGUI=true;
		int	r_size=300;
		int	r_peopleCount=1500;
		int	r_virusSeedCount=5;
		int	r_closenessFactor=200;
		int	r_houseCount=90;
		int	r_officeCount=10;
		int	r_publicCount=20;
		int	r_houseSize=7;
		int	r_houseSizeVariation=1;
		int	r_officeSize=17;
		int	r_officeSizeVariation=3;
		double	r_publicMinSize=0.92;
		double	r_publicMaxSize=0.96;
		
	// Suburban Map config
		boolean s_showGUI=true;
		int s_size=300;
		int s_peopleCount=3000;
		int s_virusSeedCount=5;
		int s_closenessFactor=200;
		int s_houseCount=100;
		int s_officeCount=50;
		int s_publicCount=20;
		int s_houseSize=5;
		int s_houseSizeVariation=1;
		int s_officeSize=15;
		int s_officeSizeVariation=3;
		double s_publicMinSize=0.9;
		double s_publicMaxSize=0.95;

	// Urban Map config
		boolean u_showGUI=true;
		int u_size=300;
		int u_peopleCount=7000;
		int u_virusSeedCount=1;
		int u_closenessFactor=50;
		int u_houseCount=300;
		int u_officeCount=200;
		int u_publicCount=50;
		int u_houseSize=3;
		int u_houseSizeVariation=1;
		int u_officeSize=10;
		int u_officeSizeVariation=2;
		double u_publicMinSize=0.85;
		double u_publicMaxSize=0.90;

	@Test
	void TestINFVirusConfig()
	{
		try {
			sc.virusType="Influenza";
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		Assertions.assertEquals(INFinfectionPeriod,simulationConfig.infectionPeriod);
		Assertions.assertEquals(INFquarantinePeriod,simulationConfig.quarantinePeriod);
		Assertions.assertEquals(INFincubationPeriod,simulationConfig.incubationPeriod);
		Assertions.assertEquals(INFchanceToGetSymptoms,simulationConfig.chanceToGetSymptoms);
		Assertions.assertEquals(INFlethality,simulationConfig.lethality);
		Assertions.assertEquals(INFspreadRange,simulationConfig.spreadRange);
		Assertions.assertEquals(INFinfectivity,simulationConfig.infectivity);
	}
	
	@Test
	void TestCOV1VirusConfig()
	{
		try {
			sc.virusType="COV1";
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		Assertions.assertEquals(COV1infectionPeriod,simulationConfig.infectionPeriod);
		Assertions.assertEquals(COV1quarantinePeriod,simulationConfig.quarantinePeriod);
		Assertions.assertEquals(COV1incubationPeriod,simulationConfig.incubationPeriod);
		Assertions.assertEquals(COV1chanceToGetSymptoms,simulationConfig.chanceToGetSymptoms);
		Assertions.assertEquals(COV1lethality,simulationConfig.lethality);
		Assertions.assertEquals(COV1spreadRange,simulationConfig.spreadRange);
		Assertions.assertEquals(COV1infectivity,simulationConfig.infectivity);
	}
	

	@Test
	void TestCOV2VirusConfig()
	{
		try {
			sc.virusType="COV2";
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		Assertions.assertEquals(COV2infectionPeriod,simulationConfig.infectionPeriod);
		Assertions.assertEquals(COV2quarantinePeriod,simulationConfig.quarantinePeriod);
		Assertions.assertEquals(COV2incubationPeriod,simulationConfig.incubationPeriod);
		Assertions.assertEquals(COV2chanceToGetSymptoms,simulationConfig.chanceToGetSymptoms);
		Assertions.assertEquals(COV2lethality,simulationConfig.lethality);
		Assertions.assertEquals(COV2spreadRange,simulationConfig.spreadRange);
		Assertions.assertEquals(COV2infectivity,simulationConfig.infectivity);
	}
	
	
	@Test
	void TestRuralMapConfig()
	{
		try {
			sc.mapType="rural";
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		Assertions.assertEquals(r_showGUI,simulationConfig.showGUI);
		Assertions.assertEquals(r_size,simulationConfig.size);
		Assertions.assertEquals(r_peopleCount,simulationConfig.peopleCount);
		Assertions.assertEquals(r_virusSeedCount,simulationConfig.virusSeedCount);
		Assertions.assertEquals(r_closenessFactor,simulationConfig.closenessFactor);
		Assertions.assertEquals(r_houseCount,simulationConfig.houseCount);
		Assertions.assertEquals(r_officeCount,simulationConfig.officeCount);
		Assertions.assertEquals(r_publicCount,simulationConfig.publicCount);
		Assertions.assertEquals(r_houseSize,simulationConfig.houseSize);
		Assertions.assertEquals(r_houseSizeVariation,simulationConfig.houseSizeVariation);
		Assertions.assertEquals(r_officeSize,simulationConfig.officeSize);
		Assertions.assertEquals(r_officeSizeVariation,simulationConfig.officeSizeVariation);
		Assertions.assertEquals(r_publicMinSize,simulationConfig.publicMinSize);
		Assertions.assertEquals(r_publicMaxSize,simulationConfig.publicMaxSize);
	}
	
	
	@Test
	void TestUrbanMapConfig()
	{
		try {
			sc.mapType="urban";
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		Assertions.assertEquals(u_showGUI,simulationConfig.showGUI);
		Assertions.assertEquals(u_size,simulationConfig.size);
		Assertions.assertEquals(u_peopleCount,simulationConfig.peopleCount);
		Assertions.assertEquals(u_virusSeedCount,simulationConfig.virusSeedCount);
		Assertions.assertEquals(u_closenessFactor,simulationConfig.closenessFactor);
		Assertions.assertEquals(u_houseCount,simulationConfig.houseCount);
		Assertions.assertEquals(u_officeCount,simulationConfig.officeCount);
		Assertions.assertEquals(u_publicCount,simulationConfig.publicCount);
		Assertions.assertEquals(u_houseSize,simulationConfig.houseSize);
		Assertions.assertEquals(u_houseSizeVariation,simulationConfig.houseSizeVariation);
		Assertions.assertEquals(u_officeSize,simulationConfig.officeSize);
		Assertions.assertEquals(u_officeSizeVariation,simulationConfig.officeSizeVariation);
		Assertions.assertEquals(u_publicMinSize,simulationConfig.publicMinSize);
		Assertions.assertEquals(u_publicMaxSize,simulationConfig.publicMaxSize);
	}
	
	
	@Test
	void TestSuburbanMapConfig()
	{
		try {
			sc.mapType="suburban";
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		Assertions.assertEquals(s_showGUI,simulationConfig.showGUI);
		Assertions.assertEquals(s_size,simulationConfig.size);
		Assertions.assertEquals(s_peopleCount,simulationConfig.peopleCount);
		Assertions.assertEquals(s_virusSeedCount,simulationConfig.virusSeedCount);
		Assertions.assertEquals(s_closenessFactor,simulationConfig.closenessFactor);
		Assertions.assertEquals(s_houseCount,simulationConfig.houseCount);
		Assertions.assertEquals(s_officeCount,simulationConfig.officeCount);
		Assertions.assertEquals(s_publicCount,simulationConfig.publicCount);
		Assertions.assertEquals(s_houseSize,simulationConfig.houseSize);
		Assertions.assertEquals(s_houseSizeVariation,simulationConfig.houseSizeVariation);
		Assertions.assertEquals(s_officeSize,simulationConfig.officeSize);
		Assertions.assertEquals(s_officeSizeVariation,simulationConfig.officeSizeVariation);
		Assertions.assertEquals(s_publicMinSize,simulationConfig.publicMinSize);
		Assertions.assertEquals(s_publicMaxSize,simulationConfig.publicMaxSize);
	}
	
	
	@Test
	void TestSimulationConfig()
	{
		try {
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		Assertions.assertEquals(baseUpdateRate,simulationConfig.baseUpdateRate);
		Assertions.assertEquals(lockdownPeriod,simulationConfig.lockdownPeriod);
		Assertions.assertEquals(limitedOccupancyPercentage,simulationConfig.limitedOccupancyPercentage);
		Assertions.assertEquals(testCooldown,simulationConfig.testCooldown);
		Assertions.assertEquals(testMode,simulationConfig.testMode);
	}
	
	@Test
	void TestPersonConfig()
	{
		try {
			sc=	new simulationConfig("src/test/resources/configTest.properties");
			} catch (IOException e) {e.printStackTrace();}
		
		Assertions.assertEquals(chanceToVisitPublic,simulationConfig.chanceToVisitPublic);
		
	}
		
}