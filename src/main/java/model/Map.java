package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.Location.Type;

public class Map {
	static Location[][] grid;
	static Set<Person> persons;
	static int size;
	static char[][] map;
	static List<Building> houses = new ArrayList<>();
	static List<Building> offices = new ArrayList<>();
	static List<Building> public_places = new ArrayList<>();
	static Building publicEventBuilding;
	
	public Map(int size) {
		Map.size = size;
		persons = new HashSet<>();
		grid = new Location[size][size];
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				grid[i][j] = new Location(i,j);
			}
		}
	}
	
	public void addPeople(int count) {
		Random random = new Random();
		for(int i=0;i<count;i++) {
			HouseBuilding house =   (HouseBuilding) houses.get(random.nextInt(houses.size()));
			Location houseLocation = house.locations.get(random.nextInt(house.locations.size()));
			OfficeBuilding office =   (OfficeBuilding) offices.get(random.nextInt(offices.size()));
			Location officeLocation = office.locations.get(random.nextInt(office.locations.size()));
			Person newPerson = new Person(houseLocation,officeLocation);
			persons.add(newPerson);
			house.residents.add(newPerson);
			office.workers.add(newPerson);
		}
	}
	
	public void seedBuilding(Type type) {
		for(int i=1;i<size-1;i++) {
			for(int j=1;j<size-1;j++) {
				double t = Math.random();
				
				if(type==Type.HOUSE && t<0.005) {
					List<Location> locations = new ArrayList<>();
					spreadBuilding(locations,100,i,j,type,0.1,0.1);
					if(locations.size()>0) {
						houses.add(new HouseBuilding(locations));
					}
				}
				else if(type==Type.WORK && t<0.002) {
					List<Location> locations = new ArrayList<>();
					spreadBuilding(locations,100,i,j,type,0.45,0.6);
					if(locations.size()>0) {
						offices.add(new OfficeBuilding(locations));
					}
				}
				else if(type==Type.PUBLIC && t<0.001) {
					List<Location> locations = new ArrayList<>();
					spreadBuilding(locations,100,i,j,type,0.7,0.8);
					if(locations.size()>0) {
						public_places.add(new PublicBuilding(locations));
					}
				}
			}	
		}
	}
	
	/**
	 * recursively spread office space from a 'seed' cell to create a larger, more realistic space
	 */
	private void spreadBuilding(List<Location> locations,double spreadChance,int row,int col,Type type,double spreadMin, double spreadMax ) {
		/* checks if cell already has land, or is at edges, or spread chance is too low */
		if(grid[row][col].type!=Type.EMPTY || spreadChance<=10 || row<=1 || row>=size-2 || col<=1 || col >=size-2) return;
		grid[row][col].type=type;
		locations.add(grid[row][col]);
		/* calls for spreading to all neighbors with a random, but diminishing chance to spread */
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row+1,col,type,spreadMin,spreadMax);
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row+1,col-1,type,spreadMin,spreadMax);
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row+1,col+1,type,spreadMin,spreadMax);
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row-1,col,type,spreadMin,spreadMax);
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row-1,col+1,type,spreadMin,spreadMax);
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row-1,col-1,type,spreadMin,spreadMax);
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row,col+1,type,spreadMin,spreadMax);
		spreadBuilding(locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row,col-1,type,spreadMin,spreadMax);
	}
	
	public void update() {
		for(Person p:persons) {
			p.update();
		}
	}
	
}
