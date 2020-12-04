
package model;

import java.awt.Canvas;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.swing.JFrame;
import model.Location.Type;

public class DemoDriver {
	public static void main(String args[]) {
		new GUIConfig();
		while(!simulationConfig.hasStarted)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		
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
		Map.realTimeChart= new Charts();
		//map.publicEventBuilding = map.public_places.get(0);
        JFrame frame = new JFrame("Disease");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUICanvas canvas = new GUICanvas();
        frame.add(canvas);
        frame.pack();
        if (simulationConfig.showGUI) frame.setVisible(true);     
        new OutputWriter();
		Simulator sim = new Simulator(canvas);
		new Timer().schedule(sim, 100, 15);
	}
	
	//TODO - edges on building painting
	//TODO - closeness heuristic for offices and parks
	//TODO - scale map down
	//TODO - unwell -> testing -> self quarantine
	//TODO - probabilistic chance of most hardcoded things

}
