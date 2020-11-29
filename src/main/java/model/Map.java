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
	static List<Building> houses = new ArrayList<>();
	static List<Building> offices = new ArrayList<>();
	static List<Building> public_places = new ArrayList<>();
	static Building publicEventBuilding;
	static Boolean contactTracing = false;
	static public Map instance;
	//properties
	static int size;
	
	
	public Map() {
		if(instance==null) instance = this;
		Map.size = simulationConfig.size;
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
			Building house = houses.get(random.nextInt(houses.size()));
			Building office = pickRandomOffice(house);
			Person newPerson = new Person(house.locations.get(random.nextInt(house.locations.size())),office.locations.get(random.nextInt(office.locations.size())));
			persons.add(newPerson);
			((HouseBuilding)house).residents.add(newPerson);
			((OfficeBuilding)office).workers.add(newPerson);
		}
	}
	
	private Building pickRandomOffice(Building b){
		Random random = new Random();
		Building ans = offices.get(0);
		int minScore = Integer.MAX_VALUE;
		for(Building o : offices) {
			int score = random.nextInt(simulationConfig.closenessFactor) + Utils.getDistance(b,o);
			if(score<minScore) {
				minScore = score;
				ans = o;
			}
		}
		return ans;
		
	}
	
	public void seedVirus(int count) {
		int counter=0;
		for(Person p : persons) {		
			counter++;
			if(counter>=persons.size() || counter==count) break;
			p.isInfected = true;		
		}
	}
	
	public void seedBuilding(Type type, int count, double minSize, double maxSize) {
		int tries = 0;
		Random random = new Random();
		int placed = 0;
		while(placed<=count && tries<=count*5) {
			tries++;
			int randX = random.nextInt(size);
			int randY = random.nextInt(size);
			if(grid[randX][randY].building!=null) continue;
			List<Location> locations = new ArrayList<>();
			if(type==Type.PUBLIC) {
				Building b = new PublicBuilding(locations);
				spreadBuilding(b,locations,100,randX,randY,type,minSize, maxSize);
				if(locations.size()>0) {
					public_places.add(b);
					placed++;
				}
			}
		}
	}
	
	public void seedBuilding(Type type, int count, int buildingSize, int sizeVariation) {
		int tries = 0;
		Random random = new Random();
		int placed = 0;
		while(placed<=count && tries<=count*5) {
			tries++;
			int randX = random.nextInt(size);
			int randY = random.nextInt(size);
			if(grid[randX][randY].building!=null) continue;
			List<Location> locations = new ArrayList<>();
			if(type==Type.HOUSE) {
				Building b = new HouseBuilding(locations);
				spreadBuilding(b, locations,buildingSize,sizeVariation,randX,randY,type);
				if(locations.size()>0) {
					houses.add(b);
					placed++;
				}
			}
			if(type==Type.WORK) {
				Building b = new OfficeBuilding(locations);
				spreadBuilding(b, locations,buildingSize,sizeVariation,randX,randY,type);
				if(locations.size()>0) {
					offices.add(b);
					placed++;
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
	
	public void createPublicEvent() {
		publicEventBuilding = public_places.get(new Random().nextInt(public_places.size()));
	}
	
	public void stopPublicEvent() {
		publicEventBuilding = null;
	}
	
	public void update() {
		for(Person p:persons) {
			p.update();
		}
		for(Building b : offices) {
			b.update();
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
			List<Person> people_in_building = new ArrayList<>(b.persons);
			//System.out.println(temp.size());
			for(int spreader=0;spreader<people_in_building.size();spreader++) {
				if (!people_in_building.get(spreader).isInfected || people_in_building.get(spreader).state==State.MOVING) continue;
				for(int victim=0;victim<people_in_building.size();victim++) {
					if(spreader==victim || people_in_building.get(victim).isInfected || people_in_building.get(victim).state==State.MOVING) continue;
					people_in_building.get(victim).tryToInfect(people_in_building.get(spreader));
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
			if (p.isTestedInfected && !p.isImmune) {
				p.isQuarantined=true;
			}
		}
	}
	
}
