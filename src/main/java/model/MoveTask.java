package model;

public class MoveTask extends Task {
	
	Location destination;

	public MoveTask(Person person,Location destination) {
		super(person);
		this.destination = destination;
	}
	
	public boolean run() {
		Map.grid[person.x][person.y].persons.remove(person);
		if(person.x != destination.x) {
			person.x+=(person.speed)*Math.signum(destination.x-person.x);
		}
		if(person.y != destination.y) {
			person.y+=(person.speed)*Math.signum(destination.y-person.y);
		}
		Map.grid[person.x][person.y].persons.add(person);
		person.currentLocation = Map.grid[person.x][person.y];
		if(person.x==destination.x && person.y == destination.y) return true;
		else return false;
	}
}
