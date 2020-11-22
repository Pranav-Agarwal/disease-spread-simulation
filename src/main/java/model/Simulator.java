package model;

import java.util.TimerTask;

public class Simulator extends TimerTask{

	public Map map;
	
	public Simulator(Map map) {
		this.map = map;
	}
	
	@Override
	public void run() {
		map.refreshAndPrintMap();
		map.update();
	}

}
