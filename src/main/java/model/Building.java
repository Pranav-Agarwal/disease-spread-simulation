package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Building {
	List<Location> locations;
	Set<Person> persons;
	Boolean isLockdown;
	
	public Building(List<Location> locations) {
		this.locations = locations;
		this.persons = new HashSet<>();
		this.isLockdown = false;
	}
}
