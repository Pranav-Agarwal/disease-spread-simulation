package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class Building {
	List<Location> locations;
	Set<Person> persons;
	Boolean isLockdown;
	int timeSinceLockdown = 0;
	
	public Building(List<Location> locations) {
		this.locations = locations;
		this.persons = new HashSet<>();
		this.isLockdown = false;
	}
	
	public int getCurrentOccupancy()
	{
		return persons.size();
	}
	
	public void update() {
		if(isLockdown) timeSinceLockdown++;
		if(simulationConfig.officeLockdown==false && timeSinceLockdown>simulationConfig.lockdownPeriod) {
		timeSinceLockdown=0;
		isLockdown=false;}
	}
	
	public Location getRandomLocation() {
		return locations.get(new Random().nextInt(locations.size()));
	}
	
	
}
