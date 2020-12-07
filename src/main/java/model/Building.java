package model;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Building {
	private static int counter = 0;
	public int id;
	public List<Location> locations;  //grid squares that are a part of this building
	public Set<Person> persons;  //people currently in this building. Set is used for constant time add and remove of people
	public Boolean isLockdown;
	public int timeSinceLockdown = 0;
	
	//represents a set of locations squares with a boundary, to simulate a building
	public Building(List<Location> locations) {
		id = counter++;  //assign a unique ID on initialization
		this.locations = locations;
		persons = new HashSet<>();
		isLockdown = false;
	}
	
	public int getCurrentOccupancy()
	{
		return persons.size();
	}
	
	//used to support the auto lockdown feature
	public void update() {
		if(isLockdown) timeSinceLockdown++;
		if(simulationConfig.officeLockdown==false && timeSinceLockdown>simulationConfig.lockdownPeriod) {
		timeSinceLockdown=0;
		isLockdown=false;}
	}
	
	//picks a random square in this building
	public Location getRandomLocation() {
		return locations.get(new Random().nextInt(locations.size()));
	}
	
	
}
