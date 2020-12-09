package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OutputWriterTest {

	OutputWriter ow;
	
	@BeforeEach
	void setupFilePath() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy HH-mm-ss");
		ow.path = "src/test/output/Sim_data "+LocalDateTime.now().format(formatter)+"-"+simulationConfig.virusType+"/";
		try {
			new File(ow.path).mkdirs();
			
		}
		catch(RuntimeException e) {
			e.printStackTrace();
		}	
		
		try {
			ow.simWriter = new FileWriter(ow.path+"sim_data.csv",true);
			ow.simWriter.write("day,total infections,active infections,total dead,total immune,total quarantined,total tests,total positive tests,maskEnforcement,lockdownOnTest,officeLockdown,publicLockdown,publicEvent,quarantine,contactTrace,socialDistancing\n");

			ow.infectionWriter = new FileWriter(OutputWriter.path+"infection_data.csv",true);
			ow.infectionWriter.write("tick,victim_id,victim_age,victim_immunity,spreader_id,infection_building_id,infection_building,was_symptomatic,was_tested_positive\n");
		
			ow.deathWriter = new FileWriter(OutputWriter.path+"death_data.csv",true);
			ow.deathWriter.write("tick,victim_id,victim_age,victim_immunity,was_tested_positive\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	@Test
	void TestAllWriters()
	{

		try {
			
		ow.simWriter.write("0,0,0,0,0,0,0,0,false,false,false,false,false,false,false,false");
		ow.simWriter.close();	
		List<String> testfileSimData = Files.readAllLines(Paths.get(ow.path+"sim_data.csv"));
		
		ow.infectionWriter.write("0,1,35,0.8,112,4,PUBLIC,true,true");
		ow.infectionWriter.close();	
		List<String> testfileInfectionData = Files.readAllLines(Paths.get(ow.path+"infection_data.csv"));
		
		
		ow.deathWriter.write("0,1,35,0.5,false");
		ow.deathWriter.close();	
		List<String> testfileDeathData = Files.readAllLines(Paths.get(ow.path+"death_data.csv"));
		
		
		OutputWriter actualoutput= new OutputWriter();
		
		actualoutput.writeSimData();
		actualoutput.writeInfectionData(1, 35, 0.8, 112, 4, "PUBLIC", true, true);
		actualoutput.writeDeathData(1, 35, 0.5, false);
		actualoutput.closeAndSave();
		
		List<String> actualfileSimData = Files.readAllLines(Paths.get(actualoutput.path+"sim_data.csv"));
		  
		List<String> actualfileInfectionData = Files.readAllLines(Paths.get(actualoutput.path+"infection_data.csv"));
		  
		List<String> actualfileDeathData = Files.readAllLines(Paths.get(actualoutput.path+"death_data.csv"));
  
		Assertions.assertLinesMatch(testfileSimData, actualfileSimData);
		Assertions.assertLinesMatch(testfileInfectionData, actualfileInfectionData);
		Assertions.assertLinesMatch(testfileDeathData, actualfileDeathData);
   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}