package model;

import java.util.TimerTask;

public class Simulator extends TimerTask{

	static int speed = 2;
	static boolean playing = false;
	public Map map;
	public GUICanvas canvas;
	public int simTicks;
	
	public Simulator(Map map,GUICanvas canvas) {
		this.map = map;
		this.canvas = canvas;
		this.simTicks=0;
	}
	
	@Override
	public void run() {
		//map.refreshAndPrintMap();
		canvas.repaint();
		if(playing) { 
			simTicks++;
			if(simTicks%speed==0) {
				map.update();
				if(simTicks%10==0) map.spreadDisease();	
				//if(simTicks>500) map.lockdownBuildings(Map.offices);
				//if(simTicks>700) map.lockdownBuildings(Map.public_places);
				//if(simTicks>500) map.enforceQuarantine();
				
			}
		}
	}

}
