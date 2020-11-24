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
    			if(cell.type==Type.HOUSE) g.setColor(new Color(248, 252, 3));
    			else if(cell.type==Type.WORK) g.setColor(new Color(255,165,0));
    			else if(cell.type==Type.PUBLIC) g.setColor(new Color(0,255,0));
    			else g.setColor(new Color(200,200,200));
    			drawLocation(g, new Rectangle(cell.x*3,cell.y*3,3,3));
    		}
    	}
    	
    	for(Person p : Map.persons) {
    		if(p.isDead) continue;
    		else if(p.isQuarantined) g.setColor(new Color(240, 3, 252));
    		else if(p.isInfected) g.setColor(new Color(255,0,0));
    		else if(p.isImmune) g.setColor(new Color(3, 173, 252));
    		else g.setColor(new Color(255,255,255));
    		drawPerson(g, new Rectangle(p.x*3,p.y*3,5,5));
    	}
    }
    

	public void drawPerson(Graphics g, Rectangle bb) {
	    g.fillOval(bb.x, bb.y, bb.width, bb.height);
	    g.setColor(new Color(0,0,0));
	    g.drawOval(bb.x, bb.y, bb.width, bb.height);
	}
	
	public void drawLocation(Graphics g, Rectangle bb) {
		g.fillRect(bb.x, bb.y, bb.width, bb.height);
	}

	
}
