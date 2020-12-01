package model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Location.Type;

public class GUICanvas extends JPanel{
	
	private static int size;
	
	public GUICanvas() {
		size = simulationConfig.size;
		this.setPreferredSize(new Dimension(size*3, size*3));
	}
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	for(int i=0;i<Map.size;i++) {
    		for(int j=0;j<Map.size;j++) {
    			Location cell = Map.grid[i][j];
    			int[] borders = new int[4];
    			if(cell.building!=null) {
	    			if(i>0) borders[0]=((Map.grid[i-1][j].building!=cell.building)?1:0);
	    			if(j>0) borders[1]=(Map.grid[i][j-1].building!=cell.building?1:0);
	    			if(i<Map.size-1) borders[2]=(Map.grid[i+1][j].building==null?1:0);
	    			if(j<Map.size-1) borders[3]=(Map.grid[i][j+1].building==null?1:0);
    			}
    			if(cell.building!=null && cell.building.isLockdown) g.setColor(Color.decode(simulationConfig.lockdownColor));
    			else if(cell.type==Type.HOUSE) g.setColor(Color.decode(simulationConfig.houseColor));
    			else if(cell.type==Type.WORK) g.setColor(Color.decode(simulationConfig.officeColor));
    			else if(cell.type==Type.PUBLIC) {
    				if(simulationConfig.publicEventBuilding==cell.building) g.setColor(Color.decode(simulationConfig.publicEventBuildingColor));
    				else g.setColor(Color.decode(simulationConfig.publicColor));
    			}
    			else g.setColor(Color.decode(simulationConfig.roadColor));
    			drawLocation(g,cell.x*3,cell.y*3,3,3, borders);
    		}
    	}
    	
    	for(Person p : Map.persons) {
    		if(p.isDead) continue;
    		else if(p.isQuarantined) g.setColor(Color.decode(simulationConfig.quarantinedColor));
    		else if(p.isInfected) g.setColor(Color.decode(simulationConfig.infectedColor));
    		else if(p.isImmune) g.setColor(Color.decode(simulationConfig.immuneColor));
    		else g.setColor(Color.decode(simulationConfig.personColor));
    		drawPerson(g,p.x*3,p.y*3,5,5);
    	}
    }
    

	public void drawPerson(Graphics g,int x,int y,int width,int height) {
	    g.fillOval(x, y, width, height);
	    g.setColor(new Color(0,0,0));
	    g.drawOval(x, y, width, height);
	}
	
	public void drawLocation(Graphics g,int x,int y,int width,int height, int[] borders) {
		g.fillRect(x, y, width, height);
		g.setColor(new Color(0,0,0));
		if(borders[0]==1) g.fillRect(x, y, 2, height);
		if(borders[1]==1) g.fillRect(x, y, width, 2);
		if(borders[2]==1) g.fillRect(x+width-2, y, 2,height);
		if(borders[3]==1) g.fillRect(x, y+width-2,width, 2);
	}

	
}
