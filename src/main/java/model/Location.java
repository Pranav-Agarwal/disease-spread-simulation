package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Location {
	
	public static enum Type {HOUSE,WORK,PUBLIC,EMPTY};
	
	int x;
	int y;
	Type type;
	
	Set<Person> persons;
	
	public Location(int x,int y) {
		this.x = x;
		this.y = y;
		persons = new HashSet<>();
		type = Type.EMPTY;
	}
}
