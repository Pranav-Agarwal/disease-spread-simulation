package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Person {

	public static enum State {WORKING, MOVING, IDLE};
	Virus v1 =new Virus();
	Boolean isInfected=false;
	Boolean isTestedInfected=false;
	Boolean isImmune=false;
	Boolean isDead=false;
	Boolean isQuarantined=false;
	Boolean isSymptomatic=false;
	Boolean wearingMask=false;
	int age=0;
	double immunityStrength=0.0;
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
<<<<<<< HEAD

	double chanceToGetSymptoms;
	double chanceToKill;
=======
	int infectionPeriod; //virus
	int quarantinePeriod; //virus
	int incubationPeriod; //virus
	double chanceToGetSymptoms; //combo
	double chanceToKill; //combo
>>>>>>> 4e0c38c23f1c2d492da2145971c90ecc1a0a8541
	double chanceToVisitPublic = 0.1;
	
	
	public Person(Location home, Location workplace) {
		this.home = home;
		this.workplace = workplace;
<<<<<<< HEAD
		this.x = home.x;
		this.y = home.y;
		this.age= getAge();				// allotting age based on normal distribution pattern
		this.immunityStrength=getImmunityStrength();
		if (Math.random()<0.5) this.currentLocation = home;
		else this.currentLocation = workplace;
		
=======
		if (Math.random()<0.5) this.currentLocation = home;
		else this.currentLocation = workplace;
		this.x = currentLocation.x;
		this.y = currentLocation.y;
		infectionPeriod=simulationConfig.infectionPeriod;
		quarantinePeriod=simulationConfig.quarantinePeriod;
		incubationPeriod=simulationConfig.incubationPeriod;
>>>>>>> 4e0c38c23f1c2d492da2145971c90ecc1a0a8541
		chanceToGetSymptoms=simulationConfig.chanceToGetSymptoms;
		this.chanceToKill= chanceToKill();
		chanceToVisitPublic=simulationConfig.chanceToVisitPublic;
		wearingMask=simulationConfig.wearingMask;
	}
	
	public void update() {
		//System.out.println(x+" "+y+" "+home.x+" "+home.y+" "+workplace.x+" "+workplace.y+" "+tasks.size()+" "+currentLocation.x+" "+currentLocation.y);
		//if(isInfected && state==State.MOVING) infectInTransit();
<<<<<<< HEAD

		if(isInfected) {
			ticksSinceInfected++;
			if (ticksSinceInfected>v1.infectionPeriod) {
				if(this.chanceToKill>0.9) killPerson();    
				isInfected=false;
				isImmune=true;
				isSymptomatic = false;
=======
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
>>>>>>> 4e0c38c23f1c2d492da2145971c90ecc1a0a8541
			}
			else if(ticksSinceInfected==v1.incubationPeriod) {
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
<<<<<<< HEAD
			if (ticksSinceQuarantined>v1.quarantinePeriod) {
=======
			if (ticksSinceQuarantined>quarantinePeriod && isQuarantined) {
>>>>>>> 4e0c38c23f1c2d492da2145971c90ecc1a0a8541
				isQuarantined=false;
				Map.totalQuarantined--;
			}
		}
		else if(tasks.isEmpty() && !this.isDead) {
			Random random = new Random();
			if(Math.random()<chanceToVisitPublic) {
				tasks.clear();
				Location publicLocation = pickPublicLocation();
				if(!publicLocation.building.isLockdown) {
					tasks.add(new MoveTask(this,publicLocation));
					tasks.add(new WorkTask(this,100+random.nextInt(200)));
				}			
				
			}
			else if(currentLocation == home && (!workplace.building.isLockdown || (simulationConfig.limitedReOpening && workplace.building.getCurrentSize()<0.5*simulationConfig.officeSize )))
			{  
				tasks.add(new MoveTask(this,workplace));
				tasks.add(new WorkTask(this,200+random.nextInt(100)));}
			
			else{
				tasks.add(new MoveTask(this,home));
				tasks.add(new WorkTask(this,200+random.nextInt(200)));
			}
		}
		if(!tasks.isEmpty() && tasks.peek().run()) tasks.remove();	
	}
	
<<<<<<< HEAD
	public void tryToInfect(Person spreader) {	// modify values based on efficacy
		if (this.isImmune) return;
		
		if (simulationConfig.socialDistancing==true || simulationConfig.maskImposed ==true)
		{
			double dist = Utils.getDistance(this,spreader);
			if (dist <simulationConfig.socialDistancing_radius)
				{if (this.wearingMask==false||spreader.wearingMask==false)
					{if (Math.random()<0.06) {this.isInfected=true;}}			
				else {if (Math.random()<0.04) {this.isInfected=true;}}
				}
			else if (Math.random()<0.01) {this.isInfected=true;}
		}
		else {if (Math.random()<0.09) this.isInfected=true;} 
=======
	public void tryToInfect(Person spreader) {
		if (isImmune) return;
		if(Math.random()<0.004) {
			isInfected=true; 
			Map.totalInfected++;
			Map.totalActiveInfected++;
			spreader.peopleInfected++;
		}
>>>>>>> 4e0c38c23f1c2d492da2145971c90ecc1a0a8541
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
		// Map.spreadDisease();
		currentLocation.persons.remove(this);
		if (currentLocation.building!=null) currentLocation.building.persons.remove(this);
		((OfficeBuilding)workplace.building).workers.remove(this);
		((HouseBuilding)home.building).residents.remove(this);
		Map.totalDead++;
		isDead=true;
		++simulationConfig.deadCount;
		//System.out.println(simulationConfig.deadCount);
		
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
		if (this.age<15 || this.age> 60) 
		{return this.immunityStrength-2*v1.lethality ;}
		else return this.immunityStrength- v1.lethality;
	}

}
