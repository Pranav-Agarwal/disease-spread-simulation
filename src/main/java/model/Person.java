package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Person {

	public static enum State {WORKING, MOVING, IDLE};
	
	Boolean isInfected=false;
	Boolean isTestedInfected=false;
	Boolean isImmune=false;
	Boolean isDead=false;
	Boolean isQuarantined=false;
	Boolean isSymptomatic=false;
	int ticksSinceInfected=0;
	int ticksSinceQuarantined=0;
	Location currentLocation;
	int x;
	int y;
	Location home;
	Location workplace;
	State state=State.IDLE;
	Queue<Task> tasks = new LinkedList<>();
	
	//config parameters
	int infectionPeriod;
	int quarantinePeriod;
	int incubationPeriod;
	double chanceToGetSymptoms;
	double chanceToKill;
	double chanceToVisitPublic = 0.1;
	
	public Person(Location home, Location workplace) {
		this.home = home;
		this.workplace = workplace;
		this.x = home.x;
		this.y = home.y;
		if (Math.random()<0.5) this.currentLocation = home;
		else this.currentLocation = workplace;
		
		infectionPeriod=simulationConfig.infectionPeriod;
		quarantinePeriod=simulationConfig.quarantinePeriod;
		incubationPeriod=simulationConfig.incubationPeriod;
		chanceToGetSymptoms=simulationConfig.chanceToGetSymptoms;
		chanceToKill=simulationConfig.chanceToKill;
		chanceToVisitPublic=simulationConfig.chanceToVisitPublic;
	}
	
	public void update() {
		//System.out.println(x+" "+y+" "+home.x+" "+home.y+" "+workplace.x+" "+workplace.y+" "+tasks.size()+" "+currentLocation.x+" "+currentLocation.y);
		//if(isInfected && state==State.MOVING) infectInTransit();
		if(isInfected) {
			ticksSinceInfected++;
			if (ticksSinceInfected>infectionPeriod) {
				if(Math.random()<chanceToKill) killPerson();
				isInfected=false;
				isImmune=true;
				isSymptomatic = false;
			}
			else if(ticksSinceInfected==incubationPeriod) {
				if(Math.random()<chanceToGetSymptoms) {
					isSymptomatic = true;
					if(!isTestedInfected) takeTest();
				}
			}
		}
		if(isQuarantined) {
			ticksSinceQuarantined++;
			if(tasks.isEmpty() && currentLocation!=home) {
				tasks.add(new MoveTask(this,home));
			}
			if (ticksSinceQuarantined>quarantinePeriod) {
				isQuarantined=false;
			}
		}
		else if(tasks.isEmpty()) {
			Random random = new Random();
			if(Math.random()<chanceToVisitPublic) {
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
		if(Math.random()<0.004) isInfected=true; 
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
	
	private void takeTest() {
		if(isInfected && Math.random()<0.8) {
			isTestedInfected = true;
			isQuarantined = true;
			if(Map.contactTracing) {
				for(Person p : workplace.building.persons) {
					if(p.isInfected && !p.isTestedInfected) p.takeTest();
				}
				for(Person p : home.building.persons) {
					if(p.isInfected && !p.isTestedInfected) p.takeTest();
				}
			}
		}
	}
	
	private void killPerson() {
		currentLocation.persons.remove(this);
		if (currentLocation.building!=null) currentLocation.building.persons.remove(this);
		isDead=true;
	}
}
