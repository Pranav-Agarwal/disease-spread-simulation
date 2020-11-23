package model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Location.Type;

public class GUICanvas extends JPanel{
	
	
	public GUICanvas() {
	}
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	for(Location[] row : Map.grid) {
    		for(Location cell:row) {
    			if(cell.type==Type.HOUSE) g.setColor(new Color(255,242,72));
    			else if(cell.type==Type.WORK) g.setColor(new Color(255,165,0));
    			else if(cell.type==Type.PUBLIC) g.setColor(new Color(0,255,0));
    			else g.setColor(new Color(100,100,100));
    			drawLocation(g, new Rectangle(cell.x*10,cell.y*10,10,10));
    		}
    	}
    	
    	for(Person p : Map.persons) {
    		if(p.isInfected) g.setColor(new Color(255,0,0));
    		else g.setColor(new Color(0,0,0));
    		drawPerson(g, new Rectangle(p.x*10,p.y*10,10,10));
    	}
    }
    

	public void drawPerson(Graphics g, Rectangle bb) {
	    g.fillOval(bb.x, bb.y, bb.width, bb.height);
	}
	
	public void drawLocation(Graphics g, Rectangle bb) {
		g.fillRect(bb.x, bb.y, bb.width, bb.height);
	}

	
}
