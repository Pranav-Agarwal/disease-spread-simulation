package model;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.Timer;

import javax.swing.JFrame;

import model.Location.Type;

public class DemoDriver {
	
	public static void main(String args[]) {
		Map map = new Map();
		map.seedBuilding(Type.PUBLIC,simulationConfig.publicCount,simulationConfig.publicMinSize,simulationConfig.publicMaxSize);
		map.seedBuilding(Type.WORK,simulationConfig.officeCount,simulationConfig.officeSize,simulationConfig.officeSizeVariation);
		map.seedBuilding(Type.HOUSE,simulationConfig.houseCount,simulationConfig.houseSize,simulationConfig.houseSizeVariation);
		map.addPeople(simulationConfig.peopleCount);
		map.seedVirus(simulationConfig.virusSeedCount);
		//map.publicEventBuilding = map.public_places.get(0);
        JFrame frame = new JFrame("Disease");
        GUICanvas canvas = new GUICanvas();
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
		Simulator sim = new Simulator(map,canvas);
		new Timer().schedule(sim, 100, 10);
		
	}
	
	//TODO - edges on building painting
	//TODO - closeness heuristic for offices and parks
	//TODO - scale map down
	//TODO - unwell -> testing -> self quarantine
	//TODO - probabilistic chance of most hardcoded things

}
