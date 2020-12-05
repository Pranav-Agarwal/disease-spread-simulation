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
	public static int simTicks;
    private int cutoff = 0;
    
	
	public Simulator(GUICanvas canvas) {
		this.canvas = canvas;
		this.actualTicks=0;
		this.simTicks = 0;
		this.cutoff = (int) (Map.persons.size()*0.8);    
	}
	
	@Override
	public void run() {
		
		//map.refreshAndPrintMap();
		//if(Map.totalActiveInfected==0 || Map.totalInfected>=cutoff) stopSim();
		if(playing) { 
			actualTicks++;
			if(actualTicks%speed==0) {
				simTicks++;
				Map.instance.update();
				if(simulationConfig.showGUI) canvas.repaint();
				OutputWriter.writeSimData();
				if(actualTicks%10==0) {
					Map.updateChart();
					Map.instance.spreadDisease(Map.offices);
					Map.instance.spreadDisease(Map.public_places);
				}
				//if(simTicks>500) map.lockdownBuildings(Map.offices);
				//if(simTicks>700) map.lockdownBuildings(Map.public_places);
				//if(simTicks>500) map.enforceQuarantine();
				
			}
		}
	}
	
	
	public static void stopSim() {
		playing = false;
		OutputWriter.closeAndSave();
		Map.realTimeChart.saveBitmapImage();
		System.exit(0);
	}

}
