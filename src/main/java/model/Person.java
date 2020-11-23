package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Person {

	public static enum State {WORKING, MOVING, IDLE};
	
	Location home;
	Location workplace;
	Location currentLocation;
	Boolean isInfected;
	int x;
	int y;
	int speed;
	State state;
	Queue<Task> tasks = new LinkedList<>();
	
	public Person(Location home, Location workplace) {
		this.isInfected=false;
		this.home = home;
		this.workplace = workplace;
		this.x = home.x;
		this.y = home.y;
		this.speed = 1;
		this.currentLocation = home;
		this.state = State.IDLE;
	}
	
	public void update() {
		//System.out.println(x+" "+y+" "+home.x+" "+home.y+" "+workplace.x+" "+workplace.y+" "+tasks.size()+" "+currentLocation.x+" "+currentLocation.y);
		//if(isInfected && state==State.MOVING) infectInTransit();
		if(tasks.size()<2 && Math.random()<0.001) {
			tasks.clear();
			tasks.add(new MoveTask(this,pickPublicLocation()));
			tasks.add(new WorkTask(this,300));
		}
		if(tasks.isEmpty()) {
			if(currentLocation == home) {
				tasks.add(new MoveTask(this,workplace));
				tasks.add(new WorkTask(this,500));
			}
			else{
				tasks.add(new MoveTask(this,home));
				tasks.add(new WorkTask(this,200));
			}
		}
		else if(tasks.peek().run()) tasks.remove();
	}
	
	public void tryToInfect(Person spreader) {
		if(Math.random()<1) isInfected=true; 
	}
	
	private void infectInTransit() {
		for(Person p : currentLocation.persons) {
			if(!p.isInfected) p.tryToInfect(this);
		}
	}
	
	private Location pickPublicLocation() {
		Random random = new Random();
		Location randomPublicLocation;
		if(Map.publicEventBuilding==null || random.nextDouble()<0.2) {
			Building randomPublicBuilding = Map.public_places.get(random.nextInt(Map.public_places.size()));
			randomPublicLocation = randomPublicBuilding.locations.get(random.nextInt(randomPublicBuilding.locations.size()));
		}
		else {
			randomPublicLocation = Map.publicEventBuilding.locations.get(random.nextInt(Map.publicEventBuilding.locations.size()));
		}
		return randomPublicLocation;
	}
}
