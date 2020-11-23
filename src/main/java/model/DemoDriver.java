package model;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.Timer;

import javax.swing.JFrame;

import model.Location.Type;

public class DemoDriver {
	
	final static int SIZE = 100;
	public static void main(String args[]) {
		Map map = new Map(SIZE);
		map.seedBuilding(Type.HOUSE);
		map.seedBuilding(Type.WORK);
		map.seedBuilding(Type.PUBLIC);
		map.addPeople(100);
		map.publicEventBuilding = map.public_places.get(0);
        JFrame frame = new JFrame("My Drawing");
        GUICanvas canvas = new GUICanvas();
        canvas.setPreferredSize(new Dimension(SIZE*10, SIZE*10));
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
		Simulator sim = new Simulator(map,canvas);
		new Timer().schedule(sim, 100, 10);
		
	}

}
