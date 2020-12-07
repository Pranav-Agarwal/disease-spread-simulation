package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfficeBuilding extends Building{
	Set<Person> workers;  //people assigned to this office building
	
	public OfficeBuilding(List<Location> locations) {
		super(locations);
		workers = new HashSet<>();
	}
}
