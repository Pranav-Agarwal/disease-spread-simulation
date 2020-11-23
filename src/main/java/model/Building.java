package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Building {
	List<Location> locations;
	Set<Person> persons;
	
	public Building(List<Location> locations) {
		this.locations = locations;
		this.persons = new HashSet<>();
	}
}
