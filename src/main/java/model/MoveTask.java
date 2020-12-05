package model;

import model.Person.State;

public class MoveTask extends Task {
	
	Location destination;
	int xDir=1;
	int yDir=1;

	public MoveTask(Person person,Location destination) {
		super(person);
		this.destination = destination;
		xDir = (int)Math.signum(destination.x-person.x);
		yDir = (int)Math.signum(destination.y-person.y);
		person.state = State.MOVING;
		Location oldLocation = Map.grid[person.x][person.y];
		oldLocation.persons.remove(person);
		oldLocation.building.persons.remove(person);
		person.currentLocation=null;
	}
	
	public boolean run() {
		if(person.x != destination.x) {
			person.x+=xDir;
		}
		if(person.y != destination.y) {
			person.y+=yDir;
		}
		if(person.x==destination.x && person.y == destination.y) {
			person.state = State.WORKING;
			Location newLocation = Map.grid[person.x][person.y];
			newLocation.persons.add(person);
			newLocation.building.persons.add(person);
			person.currentLocation = newLocation;
			return true;
		}
		else return false;
	}
}
