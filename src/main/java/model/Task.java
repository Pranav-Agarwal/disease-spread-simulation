package model;

public abstract class Task {
	
	Person person;
	
	public Task(Person person) {
		this.person = person;
	}
	
	public abstract boolean run();
	
}
