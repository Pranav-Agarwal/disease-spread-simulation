package model;

import java.util.TimerTask;

public class Simulator extends TimerTask{

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
		simTicks++;
		map.update();
		if(simTicks%10==0) map.spreadDisease();	
		//if(simTicks>500) map.lockdownBuildings(Map.offices);
		//if(simTicks>700) map.lockdownBuildings(Map.public_places);
		canvas.repaint();
	}

}
