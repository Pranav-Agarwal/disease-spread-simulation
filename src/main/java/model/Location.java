package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Location {
	int x;
	int y;
	Set<Person> persons;
	
	public Location(int x,int y) {
		this.x = x;
		this.y = y;
		persons = new HashSet<>();
	}
}
