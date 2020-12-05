package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Person {
	private static int counter=0;
	public static enum State {WORKING, MOVING, IDLE};
	Virus v1 =new Virus(simulationConfig.virusType);
	Boolean isInfected=false;
	Boolean isTestedInfected=false;
	Boolean isImmune=false;
	Boolean isDead=false;
	Boolean isQuarantined=false;
	Boolean isSymptomatic=false;
	String infectionBuildingType;
	public int infectionBuildingId;
	public int id;
	
	int age=0;
	double immunityStrength=0.0;
	int ticksSinceInfected=0;
	int ticksSinceQuarantined=0;
	int ticksSinceTested = simulationConfig.testCooldown;
	int peopleInfected = 0;
	Location currentLocation;
	int spreaderId=-1;
	int x;
	int y;
	Location home;
	Location workplace;
	State state=State.IDLE;
	Queue<Task> tasks = new LinkedList<>();
	
	//config parameters

	
	double chanceToKill;
	double chanceToVisitPublic;
	
	public Person(Location home, Location workplace) {
		this.id = counter++;
		this.home = home;
		this.workplace = workplace;

		this.age= getAge();				// allotting age based on normal distribution pattern
		this.immunityStrength=getImmunityStrength();
		if (Math.random()<0.5) this.currentLocation = home;
		else this.currentLocation = workplace;
		this.x = currentLocation.x;
		this.y = currentLocation.y;
		this.chanceToKill= chanceToKill();
		chanceToVisitPublic=simulationConfig.chanceToVisitPublic;
	}
	
	public void update() {
		ticksSinceTested++;
		if(isInfected) {
			ticksSinceInfected++;
			if (ticksSinceInfected>v1.infectionPeriod) {
				Map.totalActiveInfected--;
				if(chanceToKill>0.5 && isSymptomatic) killPerson();
				else {
					isInfected=false;
					isImmune=true;
					isSymptomatic = false;				
					Map.totalImmune++;
				}
			}
			else if(ticksSinceInfected==v1.incubationPeriod) {
				if(Math.random()<v1.chanceToGetSymptoms) {
					isSymptomatic = true;
					if(!isTestedInfected) takeTest();
				}
				OutputWriter.writeInfectionData(id,age, immunityStrength,spreaderId,infectionBuildingId, infectionBuildingType,isSymptomatic,isTestedInfected);
			}
		}
		if(isQuarantined) {
			ticksSinceQuarantined++;
			if(currentLocation!=home && tasks.isEmpty()) {
				tasks.add(new MoveTask(this,home));
			}
			if (ticksSinceQuarantined>v1.quarantinePeriod) {
				isQuarantined=false;
				Map.totalQuarantined--;
			}
		}
		else if(tasks.isEmpty()) {
			if(Math.random()<chanceToVisitPublic) {
				tasks.clear();
				Location publicLocation = pickPublicLocation();
				if(!publicLocation.building.isLockdown) {
					tasks.add(new MoveTask(this,publicLocation));
					tasks.add(new WorkTask(this,simulationConfig.timeSpentInPublic*(0.8+Math.random()*0.4)));  //Random +- 20%
				}			
				
			}
			else if(currentLocation == home && !workplace.building.isLockdown && (!simulationConfig.limitedReOpening || ((double)workplace.building.getCurrentOccupancy())/((OfficeBuilding)workplace.building).workers.size()<simulationConfig.limitedOccupancyPercentage)){  
				tasks.add(new MoveTask(this,workplace));
				tasks.add(new WorkTask(this,simulationConfig.timeSpentInOffice*(0.8+Math.random()*0.4))); //Random +- 20%
			}	
			else{
				tasks.add(new MoveTask(this,home));
				tasks.add(new WorkTask(this,simulationConfig.timeSpentInHome*(0.8+Math.random()*0.4))); //Random +- 20%
			}
		}
		if(!tasks.isEmpty() && tasks.peek().run()) tasks.remove();		
		
	}
	
	public void tryToInfect(Person spreader) {	// modify values based on efficacy
		if (this.isImmune) return;
		
		if (simulationConfig.socialDistancing==true || simulationConfig.maskEnforcement ==true)
		{
			double dist = Utils.getDistance(this,spreader);
			if (dist <simulationConfig.socialDistancing_radius)
				{if (simulationConfig.maskEnforcement==false||simulationConfig.maskEnforcement==false)
					{if (Math.random()<0.06) {this.isInfected=true;}}			
				else {if (Math.random()<0.04) {this.isInfected=true;}}
				}
			else if (Math.random()<0.01) {this.isInfected=true;}
		}
		else {if (Math.random()<0.09) this.isInfected=true;} 
		
		if(this.isInfected==true){
			Map.totalInfected++;
			Map.totalActiveInfected++;
			spreader.peopleInfected++;
			if(infectionBuildingType=="seed") {
				infectionBuildingType=currentLocation.building.getClass().getSimpleName();
				infectionBuildingId = currentLocation.building.id;
			}
			if(spreaderId==-1)spreaderId = spreader.id;
		}
		
	}
	
	private Location pickPublicLocation() {
		Random random = new Random();
		Location randomPublicLocation;
		if(simulationConfig.publicEventBuilding==null || random.nextDouble()<0.5) {
			Building randomPublicBuilding = Map.public_places.get(random.nextInt(Map.public_places.size()));
			randomPublicLocation = randomPublicBuilding.getRandomLocation();
		}
		else {
			randomPublicLocation = simulationConfig.publicEventBuilding.getRandomLocation();
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
			if(simulationConfig.quarantineOnTest && !isQuarantined) {
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
		if(currentLocation!=null) {
			currentLocation.persons.remove(this);
			currentLocation.building.persons.remove(this);
		}
		((OfficeBuilding)workplace.building).workers.remove(this);
		((HouseBuilding)home.building).residents.remove(this);
		Map.totalDead++;
		isDead=true;
		if(isQuarantined) {
			isQuarantined=false;
			Map.totalQuarantined--;
		}
		OutputWriter.writeDeathData(id,age, chanceToKill, isTestedInfected);
	}

	private int getAge() {
		Random random = new Random();
		int age =Math.abs((int)(random.nextGaussian()*15+35));   // mean=35 and std deviation of 15
		return age;
	}
	
	private double getImmunityStrength() {
		Random random = new Random();
		return random.nextDouble();
	}
	
	private double chanceToKill()
	{
		if (this.age<15 || this.age> 60) {
			return this.immunityStrength-2*v1.lethality ;
		}
		else 
			return this.immunityStrength- v1.lethality;
	}

}
