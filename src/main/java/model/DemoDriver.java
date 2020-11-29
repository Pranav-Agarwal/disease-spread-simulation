package model;

import java.awt.Canvas;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Timer;

import javax.swing.JFrame;

import model.Location.Type;

public class DemoDriver {
	
	public static void main(String args[]) {
		try {
			new simulationConfig("config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Map();
		Map.instance.seedBuilding(Type.PUBLIC,simulationConfig.publicCount,simulationConfig.publicMinSize,simulationConfig.publicMaxSize);
		Map.instance.seedBuilding(Type.WORK,simulationConfig.officeCount,simulationConfig.officeSize,simulationConfig.officeSizeVariation);
		Map.instance.seedBuilding(Type.HOUSE,simulationConfig.houseCount,simulationConfig.houseSize,simulationConfig.houseSizeVariation);
		Map.instance.addPeople(simulationConfig.peopleCount);
		Map.instance.seedVirus(simulationConfig.virusSeedCount);
		//map.publicEventBuilding = map.public_places.get(0);
        JFrame frame = new JFrame("Disease");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUICanvas canvas = new GUICanvas();
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        new GUIConfig();
		Simulator sim = new Simulator(Map.instance,canvas);
		new Timer().schedule(sim, 100, 15);
		
	}
	
	//TODO - edges on building painting
	//TODO - closeness heuristic for offices and parks
	//TODO - scale map down
	//TODO - unwell -> testing -> self quarantine
	//TODO - probabilistic chance of most hardcoded things

}
