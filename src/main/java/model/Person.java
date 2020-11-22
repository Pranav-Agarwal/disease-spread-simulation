package model;

import java.util.LinkedList;
import java.util.Queue;

public class Person {

	public static enum State {WORKING, MOVING, IDLE};
	
	Location home;
	Location workplace;
	Location currentLocation;
	int x;
	int y;
	int speed;
	State state;
	Queue<Task> tasks = new LinkedList<>();
	
	public Person(Location home, Location workplace) {
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
		if(tasks.isEmpty()) {
			if(currentLocation == home) {
				tasks.add(new MoveTask(this,workplace));
				tasks.add(new WorkTask(this,5));
			}
			if(currentLocation == workplace) {
				tasks.add(new MoveTask(this,home));
				tasks.add(new WorkTask(this,5));
			}
		}
		else if(tasks.peek().run()) tasks.remove();
	}
}
