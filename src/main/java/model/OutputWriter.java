package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class OutputWriter {
	
	public static FileWriter simWriter;
	public static FileWriter infectionWriter;
	public static FileWriter deathWriter;
	public static String path;
	
	public OutputWriter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy HH-mm-ss");
		path = "output/Sim_data "+LocalDateTime.now().format(formatter)+"/";
		try {
			new File("output").mkdir();
			new File(path).mkdir();
		}
		catch(RuntimeException e) {
		}
		try {
			simWriter = new FileWriter(path+"sim_data.csv",true);
			simWriter.write("tick,total infections,active infections,total dead,total immune,total quarantined,total tests,total positive tests,maskEnforcement,lockdownOnTest,officeLockdown,publicLockdown,publicEvent,quarantine,contactTrace,socialDistancing\n");
			infectionWriter = new FileWriter(path+"infection_data.csv",true);
			infectionWriter.write("tick,victim_id,victim_age,victim_immunity,spreader_id,infection_building_id,infection_building,was_symptomatic,was_tested_positive\n");
			deathWriter = new FileWriter(path+"death_data.csv",true);
			deathWriter.write("tick,victim_id,victim_age,victim_chance_to_die,was_tested_positive\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeSimData() {
		try {
			simWriter.write(
					Simulator.simTicks
					+","+Map.totalInfected
					+","+Map.totalActiveInfected
					+","+Map.totalDead
					+","+Map.totalImmune
					+","+Map.totalQuarantined
					+","+Map.totalTests
					+","+Map.totalPositiveTests
					+","+simulationConfig.maskEnforcement
					+","+simulationConfig.lockdownOnTest
					+","+simulationConfig.officeLockdown
					+","+simulationConfig.publicLockdown
					+","+(simulationConfig.publicEventBuilding==null?"false":"true")
					+","+simulationConfig.quarantineOnTest
					+","+simulationConfig.contactTracing
					+","+simulationConfig.socialDistancing
					+"\n");
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeInfectionData(int victimId,int age, double immunity,int spreaderId,int buildingId,String buildingType,boolean wasSymptomatic, boolean wasTestedPositive) {
		try {
			infectionWriter.write(Simulator.simTicks
					+","+victimId
					+","+age
					+","+immunity
					+","+spreaderId
					+","+buildingId
					+","+buildingType
					+","+wasSymptomatic
					+","+wasTestedPositive
					+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeDeathData(int id,int age,double chanceToDie, boolean wasTestedPositive) {
		try {
			deathWriter.write(Simulator.simTicks
					+","+id
					+","+age
					+","+chanceToDie
					+","+wasTestedPositive
					+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeAndSave() {
		try {
			simWriter.close();
			infectionWriter.close();
			deathWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
