package model;

import model.Person.State;

//represents a task to move a person from current location to a defined destination. moves one square per tick
public class MoveTask extends Task {
	
	private Location destination;
	private int[] dir;

	public MoveTask(Person person,Location destination) {
		super(person);
		this.destination = destination;
		dir = findMovementDirection();
		person.state = State.MOVING;
		Location oldLocation = Map.grid[person.x][person.y];
		oldLocation.persons.remove(person);
		if(oldLocation.building!=null)oldLocation.building.persons.remove(person);
		person.currentLocation=null;
	}
	
	public int[] findMovementDirection() {
		return new int[] {(int)Math.signum(destination.x-person.x),(int)Math.signum(destination.y-person.y)};
	}
	
	//this task will keep executing until destination is reached
	public boolean run() {
		if(person.x != destination.x) {
			person.x+=dir[0];
		}
		if(person.y != destination.y) {
			person.y+=dir[1];
		}
		if(person.x==destination.x && person.y == destination.y) {
			person.state = State.WORKING;
			Location newLocation = Map.grid[person.x][person.y];
			newLocation.persons.add(person);
			if(newLocation.building!=null)newLocation.building.persons.add(person);
			person.currentLocation = newLocation;
			return true;
		}
		else return false;
	}
	
	public int[] getDir() {
		return dir;
	}
}
