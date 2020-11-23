package model;

import java.util.ArrayList;
import java.util.List;

public class HouseBuilding extends Building{
	List<Person> residents;
	
	public HouseBuilding(List<Location> locations) {
		super(locations);
		residents = new ArrayList<>();
	}
}
