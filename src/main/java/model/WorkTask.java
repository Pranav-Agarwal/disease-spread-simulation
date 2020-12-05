package model;

public class WorkTask extends Task {

	int timePassed;
	double timeOut;

	public WorkTask(Person person,double d) {
		super(person);
		this.timeOut = d;
		this.timePassed=0;
	}
	
	public boolean run() {
		if(++timePassed>=timeOut) return true;
		else return false;
	}
}
