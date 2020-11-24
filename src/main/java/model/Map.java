package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.Location.Type;
import model.Person.State;

public class Map {
	static Location[][] grid;
	static Set<Person> persons;
	static int size;
	static char[][] map;
	static List<Building> houses = new ArrayList<>();
	static List<Building> offices = new ArrayList<>();
	static List<Building> public_places = new ArrayList<>();
	static Building publicEventBuilding;
	static Boolean contactTracing = true;
	
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
	
	public void seedVirus(int count) {
		int counter=0;
		for(Person p : persons) {		
			counter++;
			if(counter>=persons.size() || counter==count) break;
			p.isInfected = true;		
		}
	}
	
	public void seedBuilding(Type type) {
		for(int i=1;i<size-1;i++) {
			for(int j=1;j<size-1;j++) {
				double t = Math.random();
				if(grid[i][j].building!=null) continue;
				if(type==Type.HOUSE && t<0.005) {
					List<Location> locations = new ArrayList<>();
					Building b = new HouseBuilding(locations);
					spreadBuilding(b, locations,100,i,j,type,0.3,0.3);
					if(locations.size()>0) {
						houses.add(b);
					}
				}
				else if(type==Type.WORK && t<0.001) {
					List<Location> locations = new ArrayList<>();
					Building b = new OfficeBuilding(locations);
					spreadBuilding(b, locations,10,3,i,j,type);
					if(locations.size()>0) {
						offices.add(b);
					}
				}
				else if(type==Type.PUBLIC && t<0.001) {
					List<Location> locations = new ArrayList<>();
					Building b = new PublicBuilding(locations);
					spreadBuilding(b, locations,100,i,j,type,0.8,0.9);
					if(locations.size()>0) {
						public_places.add(b);
					}
				}
			}	
		}
	}
	
	/**
	 * recursively spread office space from a 'seed' cell to create a larger, more realistic space
	 */
	private void spreadBuilding(Building building, List<Location> locations,double spreadChance,int row,int col,Type type,double spreadMin, double spreadMax ) {
		/* checks if cell already has land, or is at edges, or spread chance is too low */
		Location tempLocation = grid[row][col];
		if(tempLocation.type!=Type.EMPTY || spreadChance<=10 || row<=1 || row>=size-2 || col<=1 || col >=size-2) return;
		tempLocation.type=type;
		locations.add(tempLocation);
		tempLocation.building = building;
		/* calls for spreading to all neighbors with a random, but diminishing chance to spread */
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row+1,col,type,spreadMin,spreadMax);
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row+1,col-1,type,spreadMin,spreadMax);
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row+1,col+1,type,spreadMin,spreadMax);
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row-1,col,type,spreadMin,spreadMax);
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row-1,col+1,type,spreadMin,spreadMax);
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row-1,col-1,type,spreadMin,spreadMax);
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row,col+1,type,spreadMin,spreadMax);
		spreadBuilding(building, locations, spreadChance*(Math.random()*(spreadMax-spreadMin)+spreadMin),row,col-1,type,spreadMin,spreadMax);
	}
	
	private void spreadBuilding(Building building, List<Location> locations, int buildingSize, int sizeVariation, int row, int col, Type type) {
		double width = Math.random()*(sizeVariation*2)+(buildingSize-sizeVariation);
		double height = Math.random()*(sizeVariation*2)+(buildingSize-sizeVariation);
		for(int i=0;i<height;i++) {
			if((i+row)>=size-2) break;
			for (int j=0;j<width;j++) {
				if((j+col)>=size-2) break;
				Location tempLocation = grid[row+i][col+j];
				if(tempLocation.building!=null) continue;
				tempLocation.type=type;
				locations.add(tempLocation);
				tempLocation.building = building;
			}
		}
	}
	
	public void update() {
		for(Person p:persons) {
			p.update();
		}
	}
	
	public void lockdownBuildings(List<Building> buildings) {
		for(Building b : buildings) {
			b.isLockdown=true;
		}
	}
	
	public void liftLockdownBuildings(List<Building> buildings) {
		for(Building b : buildings) {
			b.isLockdown=false;
		}
	}
	
	
	public void spreadDisease() {
		for (Building b : offices) {
			List<Person> temp = new ArrayList<>(b.persons);
			//System.out.println(temp.size());
			for(int i=0;i<temp.size();i++) {
				if (!temp.get(i).isInfected || temp.get(i).state==State.MOVING) continue;
				for(int j=0;j<temp.size();j++) {
					if(i==j || temp.get(j).isInfected || temp.get(j).state==State.MOVING) continue;
					temp.get(j).tryToInfect(temp.get(i));
				}
			}
		}
		for (Building b : public_places) {
			List<Person> temp = new ArrayList<>(b.persons);
			//System.out.println(temp.size());
			for(int i=0;i<temp.size();i++) {
				if (!temp.get(i).isInfected || temp.get(i).state==State.MOVING) continue;
				for(int j=0;j<temp.size();j++) {
					if(i==j || temp.get(j).isInfected || temp.get(j).state==State.MOVING) continue;
					temp.get(j).tryToInfect(temp.get(i));
				}
			}
		}
	}
	
	public void enforceQuarantine() {
		for(Person p:persons) {
			if (p.isInfected) {
				if(contactTracing) {
					for(Person h : ((HouseBuilding)(p.home.building)).residents) {
							h.isQuarantined=true;				
					}
					for(Person h : ((OfficeBuilding)(p.workplace.building)).workers) {
							h.isQuarantined=true;
					}
				}
				p.isQuarantined=true;
			}
		}
	}
	
}
