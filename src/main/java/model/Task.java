package model;

public abstract class Task {
	
	private static long ctr = 0;
	Person person;
	long id;
	
	public Task(Person person) {
		this.person = person;
		this.id = ctr++;
	}
	
	public abstract boolean run();
	
}
