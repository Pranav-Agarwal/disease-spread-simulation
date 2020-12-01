package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class Simulator extends TimerTask{

	static int speed = 2;
	static boolean playing = false;
	public GUICanvas canvas;
	public int actualTicks;
	private int simTicks;
    private FileWriter writer;
    private int cutoff = 0;
	
	public Simulator(GUICanvas canvas) {
		this.canvas = canvas;
		this.actualTicks=0;
		this.simTicks = 0;
		this.cutoff = (int) (Map.persons.size()*0.8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
		String timestamp = LocalDateTime.now().format(formatter);
        try {
			writer = new FileWriter("output/data_"+timestamp+".csv",true);
			writer.write("tick,total infections,active infections,total dead,total immune,total quarantined,total tests,total positive tests\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	
	@Override
	public void run() {
		//map.refreshAndPrintMap();
		//if(Map.totalActiveInfected==0 || Map.totalInfected>=cutoff) stopSim();
		if(simulationConfig.showGUI) canvas.repaint();
		if(playing) { 
			actualTicks++;
			if(actualTicks%speed==0) {
				simTicks++;
				Map.instance.update();
				saveMapState();
				if(actualTicks%10==0) {
					Map.instance.spreadDisease();
				}
				//if(simTicks>500) map.lockdownBuildings(Map.offices);
				//if(simTicks>700) map.lockdownBuildings(Map.public_places);
				//if(simTicks>500) map.enforceQuarantine();
				
			}
		}
	}
	
	private void saveMapState() {
      try {
    	  writer.write(simTicks+","+Map.totalInfected+","+Map.totalActiveInfected+","+Map.totalDead+","+Map.totalImmune+","+Map.totalQuarantined+","+Map.totalTests+","+Map.totalPositiveTests+"\n");
       } catch (IOException i) {
          i.printStackTrace();
       }
	}
	
	private void stopSim() {
		playing = false;
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
