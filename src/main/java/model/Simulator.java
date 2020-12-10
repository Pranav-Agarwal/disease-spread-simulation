package model;

import java.util.TimerTask;

//This is the timer task that will run at regular intervals and update the map state
public class Simulator extends TimerTask{

	static int speed = 2;
	static boolean playing = false;
	public GUICanvas canvas;  //GUI canvas to be repainted
	public int actualTicks;
	public static int simTicks;
    
	
	public Simulator(GUICanvas canvas) {
		this.canvas = canvas;
		actualTicks=0;
		simTicks = 0;
	}
	
	@Override
	public void run() {
		if(playing) { 
			actualTicks++;
			if(actualTicks%speed==0) {
				simTicks++;
				canvas.repaint();
				if(simTicks>18000) stopSim();
				Map.instance.update();
				if(simTicks%100==0) {
					if(Map.totalActiveInfected<=5) 
					{Map.instance.seedVirusToHold(simulationConfig.virusSeedCount);}
					Map.updateChart();
					OutputWriter.writeSimData();
					Map.instance.spreadDisease(Map.offices);
					Map.instance.spreadDisease(Map.public_places);

				}
			}
		}
	}
	
	
	public static void stopSim() {
		playing = false;
		OutputWriter.closeAndSave();
		Map.realTimeChart.saveBitmapImage();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.exit(0);
	}

}
