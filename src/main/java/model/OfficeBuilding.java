package model;

import java.util.ArrayList;
import java.util.List;

public class OfficeBuilding extends Building{
	List<Person> workers;
	
	public OfficeBuilding(List<Location> locations) {
		super(locations);
		workers = new ArrayList<>();
	}
}
