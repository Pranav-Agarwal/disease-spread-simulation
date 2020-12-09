package model;


import java.util.HashSet;
import java.util.Set;

//represents a grid square on the map
public class Location {
	
	public static enum Type {HOUSE,WORK,PUBLIC,EMPTY};
	
	public int x;
	public int y;
	public Type type;
	public Building building; //which building is this location a part of
	
	public Set<Person> persons; //which people are currently in this location
	
	public Location(int x,int y) {
		this.x = x;
		this.y = y;
		persons = new HashSet<>();
		type = Type.EMPTY;
	}
}
