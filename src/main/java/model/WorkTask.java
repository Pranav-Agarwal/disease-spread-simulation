package model;

public class WorkTask extends Task {

	int timePassed;
	int timeOut;

	public WorkTask(Person person,int timeOut) {
		super(person);
		this.timeOut = timeOut;
		this.timePassed=0;
	}
	
	public boolean run() {
		if(++timePassed>=timeOut) return true;
		else return false;
	}
}
