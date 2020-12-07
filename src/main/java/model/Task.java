package model;

//represents a job that a person will work upon till it is finished
public abstract class Task {
	
	public Person person; //person this task is assigned to
	
	public Task(Person person) {
		this.person = person;
	}
	
	//what is comprised as part of this task, will be called every update tick till this task is completed
	public abstract boolean run();
	
}
