package model;

import java.util.TimerTask;

public class Simulator extends TimerTask{

	public Map map;
	public GUICanvas canvas;
	
	public Simulator(Map map,GUICanvas canvas) {
		this.map = map;
		this.canvas = canvas;
	}
	
	@Override
	public void run() {
		//map.refreshAndPrintMap();
		map.update();
		map.spreadDisease();
		canvas.repaint();
	}

}
