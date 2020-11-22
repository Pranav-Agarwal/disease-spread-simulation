package model;

import java.util.Timer;

public class DemoDriver {
	
	public static void main(String args[]) {
		Map map = new Map(30);
		map.addPeople(10);
		Simulator sim = new Simulator(map);
		new Timer().schedule(sim, 100, 100);
	}

}
