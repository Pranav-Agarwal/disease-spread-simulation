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
	int ticksSinceTested = simulationConfig.testCooldown;
	int peopleInfected = 0;
	Location currentLocation;
	int x;
	int y;
	Location home;
	Location workplace;
	State state=State.IDLE;
	Queue<Task> tasks = new LinkedList<>();
	
	//config parameters
	int infectionPeriod; //virus
	int quarantinePeriod; //virus
	int incubationPeriod; //virus
	double chanceToGetSymptoms; //combo
	double chanceToKill; //combo
	double chanceToVisitPublic = 0.1;
	
	public Person(Location home, Location workplace) {
		this.home = home;
		this.workplace = workplace;
		if (Math.random()<0.5) this.currentLocation = home;
		else this.currentLocation = workplace;
		this.x = currentLocation.x;
		this.y = currentLocation.y;
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
		ticksSinceTested++;
		if(isInfected) {
			ticksSinceInfected++;
			if (ticksSinceInfected>infectionPeriod) {
				Map.totalActiveInfected--;
				if(Math.random()<chanceToKill) killPerson();
				else {
					isInfected=false;
					isImmune=true;
					isSymptomatic = false;				
					Map.totalImmune++;
				}
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
			if (ticksSinceQuarantined>quarantinePeriod && isQuarantined) {
				isQuarantined=false;
				Map.totalQuarantined--;
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
		if(Math.random()<0.004) {
			isInfected=true; 
			Map.totalInfected++;
			Map.totalActiveInfected++;
			spreader.peopleInfected++;
		}
	}
	
	private void infectInTransit() {
		for(Person p : currentLocation.persons) {
			if(!p.isInfected) p.tryToInfect(this);
		}
	}
	
	private Location pickPublicLocation() {
		Random random = new Random();
		Location randomPublicLocation;
		if(simulationConfig.publicEventBuilding==null || random.nextDouble()<0.001) {
			Building randomPublicBuilding = Map.public_places.get(random.nextInt(Map.public_places.size()));
			randomPublicLocation = randomPublicBuilding.locations.get(random.nextInt(randomPublicBuilding.locations.size()));
		}
		else {
			randomPublicLocation = simulationConfig.publicEventBuilding.locations.get(random.nextInt(simulationConfig.publicEventBuilding.locations.size()));
		}
		return randomPublicLocation;
	}
	
	private void takeTest() {
		ticksSinceTested = 0;
		Map.totalTests++;
		if(isInfected && Math.random()<0.8) {
			isTestedInfected = true;
			Map.totalPositiveTests++;
			if(simulationConfig.lockdownOnTest) workplace.building.isLockdown=true;
			if(simulationConfig.quarantineOnTest) {
				isQuarantined= true;
				Map.totalQuarantined++;
			}
			if(simulationConfig.contactTracing) {
				for(Person p : workplace.building.persons) {
					if(p.ticksSinceTested>simulationConfig.testCooldown) p.takeTest();
				}
				for(Person p : home.building.persons) {
					if(p.ticksSinceTested>simulationConfig.testCooldown) p.takeTest();
				}
			}
		}
	}
	
	private void killPerson() {
		currentLocation.persons.remove(this);
		if (currentLocation.building!=null) currentLocation.building.persons.remove(this);
		((OfficeBuilding)workplace.building).workers.remove(this);
		((HouseBuilding)home.building).residents.remove(this);
		Map.totalDead++;
		isDead=true;
	}
}
