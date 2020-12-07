package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HouseBuilding extends Building{
	public Set<Person> residents;  //people that live in this building
	
	public HouseBuilding(List<Location> locations) {
		super(locations);
		residents = new HashSet<>();
	}
}
