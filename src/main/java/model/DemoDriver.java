
package model;

import java.io.IOException;
import java.util.Timer;
import javax.swing.JFrame;
import model.Location.Type;

//Main entry point for the program. Initializes and drives the simulation
public class DemoDriver {
	public static void main(String args[]) {
		
		// 1. Create and show the configuration box, and do not proceed until initialization
		new GUIConfig();
		while(!simulationConfig.hasStarted)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		
		
		//2. Read the map properties from the config file
		try {
			new simulationConfig("config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//3. Initialize the map, buildings, people and infect a few people 
		new Map();
		Map.instance.seedBuilding(Type.PUBLIC,simulationConfig.publicCount,simulationConfig.publicMinSize,simulationConfig.publicMaxSize);
		Map.instance.seedBuilding(Type.WORK,simulationConfig.officeCount,simulationConfig.officeSize,simulationConfig.officeSizeVariation);
		Map.instance.seedBuilding(Type.HOUSE,simulationConfig.houseCount,simulationConfig.houseSize,simulationConfig.houseSizeVariation);
		Map.instance.addPeople(simulationConfig.peopleCount);
		Map.instance.seedVirus(simulationConfig.virusSeedCount);
		
		//4. Initialize the visualization and the real time graph
		Map.realTimeChart= new Charts();
        JFrame frame = new JFrame("Disease Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUICanvas canvas = new GUICanvas();
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        if (simulationConfig.showGUI) frame.setVisible(true);
        
        //5. Initialze the output writer
        new OutputWriter();
        
        //6. Initialze the timer and the timer task
		Simulator sim = new Simulator(canvas);
		new Timer().schedule(sim, 10, simulationConfig.baseUpdateRate);
	}


}
