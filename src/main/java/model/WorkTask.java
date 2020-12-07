package model;

//represents a 'work task' . It will keep a person at a spot until the time period defined in the task runs out
public class WorkTask extends Task {

	private int timePassed;
	private double timeOut;

	public WorkTask(Person person,double d) {
		super(person);
		timeOut = d;
		timePassed=0;
	}
	
	public boolean run() {
		if(++timePassed>=timeOut) return true;
		else return false;
	}
}
