package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import model.Location.Type;
import model.Person.State;

//Represents the 2D grid containing people and buildings on which the virus will be released.
public class Map {
	
	static Location[][] grid;
	static Set<Person> persons;
	static List<Building> houses;
	static List<Building> offices;
	static List<Building> public_places;
	static public Map instance;
	static public Charts realTimeChart;
	//properties
	static int size;
	
	//simulation state
	static int totalInfected = 0;
	static int totalActiveInfected = 0;
	static int totalDead = 0;
	static int totalImmune = 0;
	static int totalQuarantined = 0;
	static double rNought = 0;
	static int totalTests = 0;
	static int totalPositiveTests = 0;
	
	//Chart properties
	public static LinkedList<Integer> xData = new LinkedList<>();
	public static LinkedList<Integer> yData_totalInfected = new LinkedList<>();
	public static LinkedList<Integer> yData_totalActiveInfected = new LinkedList<>();
	public static LinkedList<Integer> yData_totalDied = new LinkedList<>();
	
	public static void addChartValue(LinkedList<Integer> Data, Integer value)
	{
		Data.add(value);
	}
	
	public static void updateChart() {
		addChartValue(xData,(Simulator.simTicks/100));
		addChartValue(yData_totalInfected, Map.totalInfected);
		addChartValue(yData_totalActiveInfected, Map.totalActiveInfected);
		addChartValue(yData_totalDied, Map.totalDead);
		realTimeChart.realTimeupdates();
	}
	
	public Map() {
		if(instance==null) instance = this;
		Map.size = simulationConfig.size;
		persons = new HashSet<>();
		houses = new ArrayList<>();
		offices = new ArrayList<>();
		public_places = new ArrayList<>();
		grid = new Location[size][size];
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				grid[i][j] = new Location(i,j);
			}
		}
		
	}
	
	public void addPeople(int count) {
		Random random = new Random();
		if(simulationConfig.testMode) random.setSeed(420);
			
		for(int i=0;i<count;i++) {
			Building house = houses.get(random.nextInt(houses.size()));
			Building office = pickCloseBuilding(offices,house);
			Person newPerson = new Person(house,office);
			persons.add(newPerson);
			((HouseBuilding)house).residents.add(newPerson);
			((OfficeBuilding)office).workers.add(newPerson);
		}
	}
	
	public static Building pickCloseBuilding(List<Building> choices,Building closeTo){
		Random random = new Random();
		if (simulationConfig.testMode) random.setSeed(420);
		Building ans = choices.get(0);
		int minScore = Integer.MAX_VALUE;
		for(Building b : choices) {
			int score = random.nextInt(simulationConfig.closenessFactor) + Utils.getDistance(b,closeTo);
			if(score<minScore) {
				minScore = score;
				ans = b;
			}
		}
		return ans;		
	}
	
	//infects a few random people
	public void seedVirus(int count) {
		int counter=0;
		for(Person p : persons) {		
			if(counter>=persons.size() || counter==count) break;
			p.isInfected = true;
			counter++;
		}
		Map.totalActiveInfected = count;
		Map.totalInfected = count;
	}
	
	//seeds public spaces
	public void seedBuilding(Type type, int count, double minSize, double maxSize) {
		int tries = 0;
		Random random = new Random();
		int placed = 0;
		while(placed<count && tries<=count*5) {
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
	
	//seeds offices and houses
	public void seedBuilding(Type type, int count, int buildingSize, int sizeVariation) {
		int tries = 0;
		Random random = new Random();
		int placed = 0;
		while(placed<count && tries<=count*5) {
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
	
	//recursively spread a building from a 'seed' cell to create a larger, more realistic and jagged space
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
	
	//spreads a rectangular building
	public double spreadBuilding(Building building, List<Location> locations, int buildingSize, int sizeVariation, int row, int col, Type type) {
		Random random = new Random();
		if (simulationConfig.testMode) random.setSeed(420);
		double width = random.nextDouble()*(sizeVariation*2)+(buildingSize-sizeVariation);
		double height = random.nextDouble()*(sizeVariation*2)+(buildingSize-sizeVariation);
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
		return width*height;
	}
	
	public void createPublicEvent() {
		simulationConfig.publicEventBuilding = public_places.get(new Random().nextInt(public_places.size()));
	}
	
	public void stopPublicEvent() {
		simulationConfig.publicEventBuilding = null;
	}
	
	public void update() {
		for(Person p:persons) {
			if(!p.isDead) p.update();
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
	
	
	public void spreadDisease(List<Building> buildings) {
		for (Building b : buildings) {
			List<Person> people_in_building = new ArrayList<>(b.persons);
			for(int spreader=0;spreader<people_in_building.size();spreader++) {
				if (!people_in_building.get(spreader).isInfected || people_in_building.get(spreader).state==State.MOVING) continue;
				for(int victim=0;victim<people_in_building.size();victim++) {
					if(spreader==victim || people_in_building.get(victim).isInfected || people_in_building.get(victim).state==State.MOVING) continue;
					people_in_building.get(victim).tryToInfect(people_in_building.get(spreader));
				}
			}
		}
	}
	
	
	public void enforceQuarantine() {
		for(Person p:persons) {
			if (!p.isDead && p.isTestedInfected && !p.isImmune && !p.isQuarantined) {
				Map.totalQuarantined++;
				p.isQuarantined=true;
			}
		}
	}
	
}
