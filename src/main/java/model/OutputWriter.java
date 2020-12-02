package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputWriter {
	
	public static FileWriter simWriter;
	public static FileWriter infectionWriter;
	public static FileWriter deathWriter;
	private String path;
	
	public OutputWriter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy HH-mm-ss");
		path = "output/Sim_data "+LocalDateTime.now().format(formatter)+"/";
		new File(path).mkdir();

		try {
			simWriter = new FileWriter(path+"sim_data.csv",true);
			simWriter.write("tick,total infections,active infections,total dead,total immune,total quarantined,total tests,total positive tests\n");
			infectionWriter = new FileWriter(path+"infection_data.csv",true);
			infectionWriter.write("tick,victim_age,victim_immunity,infection_location,was_symptomatic,was_tested_positive\n");
			deathWriter = new FileWriter(path+"death_data.csv",true);
			deathWriter.write("tick,victim_age,victim_chance_to_die,was_tested_positive\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeSimData() {
		try {
			simWriter.write(Simulator.simTicks+","+Map.totalInfected+","+Map.totalActiveInfected+","+Map.totalDead+","+Map.totalImmune+","+Map.totalQuarantined+","+Map.totalTests+","+Map.totalPositiveTests+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeInfectionData(int age, double immunity,String location,boolean wasSymptomatic, boolean wasTestedPositive) {
		try {
			infectionWriter.write(Simulator.simTicks+","+age+","+immunity+","+location+","+wasSymptomatic+","+wasTestedPositive+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeDeathData(int age,double chanceToDie, boolean wasTestedPositive) {
		try {
			deathWriter.write(Simulator.simTicks+","+age+","+chanceToDie+","+wasTestedPositive+"\n");
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
