package model;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.Timer;

import javax.swing.JFrame;

import model.Location.Type;

public class DemoDriver {
	
	final static int SIZE = 300;
	public static void main(String args[]) {
		Map map = new Map(SIZE);
		map.seedBuilding(Type.HOUSE);
		map.seedBuilding(Type.WORK);
		map.seedBuilding(Type.PUBLIC);
		map.addPeople(1000);
		map.seedVirus(5);
		//map.publicEventBuilding = map.public_places.get(0);
        JFrame frame = new JFrame("Disease");
        GUICanvas canvas = new GUICanvas();
        canvas.setPreferredSize(new Dimension(SIZE*3, SIZE*3));
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
		Simulator sim = new Simulator(map,canvas);
		new Timer().schedule(sim, 100, 10);
		
	}

}
