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
	Boolean isImmune;
	Boolean isDead;
	Boolean isQuarantined;
	Boolean hasBeenQuarantined;
	int x;
	int y;
	int speed;
	State state;
	int ticksSinceInfected;
	int ticksSinceQuarantined;
	Queue<Task> tasks = new LinkedList<>();
	
	public Person(Location home, Location workplace) {
		this.isInfected=false;
		this.isImmune=false;
		this.isDead=false;
		this.isQuarantined = false;
		this.home = home;
		this.workplace = workplace;
		this.x = home.x;
		this.y = home.y;
		this.speed = 1;
		if (Math.random()<0.5) this.currentLocation = home;
		else this.currentLocation = workplace;
		this.state = State.IDLE;
	}
	
	public void update() {
		//System.out.println(x+" "+y+" "+home.x+" "+home.y+" "+workplace.x+" "+workplace.y+" "+tasks.size()+" "+currentLocation.x+" "+currentLocation.y);
		//if(isInfected && state==State.MOVING) infectInTransit();
		if(isInfected) {
			ticksSinceInfected++;
			if (ticksSinceInfected>3000) {
				if(Math.random()<0.1) killPerson();
				isInfected=false;
				isImmune=true;
			}
		}
		if(isQuarantined) {
			ticksSinceQuarantined++;
			if(tasks.isEmpty() && currentLocation!=home) {
				tasks.add(new MoveTask(this,home));
			}
			if (ticksSinceQuarantined>3000) {
				isQuarantined=false;
			}
		}
		else if(tasks.isEmpty()) {
			Random random = new Random();
			if(Math.random()<0.3) {
				tasks.clear();
				Location publicLocation = pickPublicLocation();
				if(!publicLocation.building.isLockdown) {
					tasks.add(new MoveTask(this,publicLocation));
					tasks.add(new WorkTask(this,100+random.nextInt(200)));
				}		
			}
			else if(currentLocation == home && !workplace.building.isLockdown) {
				tasks.add(new MoveTask(this,workplace));
				tasks.add(new WorkTask(this,200+random.nextInt(100)));
			}
			else{
				tasks.add(new MoveTask(this,home));
				tasks.add(new WorkTask(this,200+random.nextInt(200)));
			}
		}
		if(!tasks.isEmpty() && tasks.peek().run()) tasks.remove();
	}
	
	public void tryToInfect(Person spreader) {
		if (isImmune) return;
		if(Math.random()<0.01) isInfected=true; 
	}
	
	private void infectInTransit() {
		for(Person p : currentLocation.persons) {
			if(!p.isInfected) p.tryToInfect(this);
		}
	}
	
	private Location pickPublicLocation() {
		Random random = new Random();
		Location randomPublicLocation;
		if(Map.publicEventBuilding==null || random.nextDouble()<0.001) {
			Building randomPublicBuilding = Map.public_places.get(random.nextInt(Map.public_places.size()));
			randomPublicLocation = randomPublicBuilding.locations.get(random.nextInt(randomPublicBuilding.locations.size()));
		}
		else {
			randomPublicLocation = Map.publicEventBuilding.locations.get(random.nextInt(Map.publicEventBuilding.locations.size()));
		}
		return randomPublicLocation;
	}
	
	private void killPerson() {
		currentLocation.persons.remove(this);
		if (currentLocation.building!=null) currentLocation.building.persons.remove(this);
		isDead=true;
	}
}
