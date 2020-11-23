package model;

import model.Person.State;

public class MoveTask extends Task {
	
	Location destination;

	public MoveTask(Person person,Location destination) {
		super(person);
		this.destination = destination;
	}
	
	public boolean run() {
		person.state = State.MOVING;
		Location oldLocation = Map.grid[person.x][person.y];
		oldLocation.persons.remove(person);
		if(oldLocation.building!=null) oldLocation.building.persons.remove(person);
		if(person.x != destination.x) {
			person.x+=(person.speed)*Math.signum(destination.x-person.x);
		}
		if(person.y != destination.y) {
			person.y+=(person.speed)*Math.signum(destination.y-person.y);
		}
		Location newLocation = Map.grid[person.x][person.y];
		newLocation.persons.add(person);
		if(newLocation.building!=null) {
			newLocation.building.persons.add(person);
			//System.out.println(newLocation.building.persons.size());
		}
		person.currentLocation = newLocation;
		if(person.x==destination.x && person.y == destination.y) {
			person.state = State.WORKING;
			return true;
		}
		else return false;
	}
}
