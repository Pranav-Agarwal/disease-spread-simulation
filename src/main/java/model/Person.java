package model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

//represents a person on the map
public class Person {
	
	private static int counter=0; //used to assign a unique ID
	public static enum State {WORKING, MOVING, IDLE};
	
	//infection state variables
	public Boolean isInfected=false;
	public Boolean isTestedInfected=false;
	public Boolean isImmune=false;
	public Boolean isDead=false;
	public Boolean isQuarantined=false;
	public Boolean isSymptomatic=false;
	public Boolean hasBeenExposedThisTick = false;
	public String infectionBuildingType="seed";
	private int infectionBuildingId;
	private int spreaderId=-1;
	
	//location state variables
	public Location currentLocation;
	public int x;
	public int y;
	public Building home;
	public Building workplace;
	public State state=State.IDLE;
	
	//property variables
	public Virus v1;
	private int id;
	public int age=0;
	public double immunityStrength=0.0;
	public int ticksSinceInfected=0;
	public int ticksSinceQuarantined=0;
	public int ticksSinceTested = simulationConfig.testCooldown;
	public Queue<Task> tasks = new LinkedList<>();
	
	//config parameters from file
	public double chanceToVisitPublic;
	
	public Person(Building home, Building workplace) {
		this.home = home;
		this.workplace = workplace;
		
		id = counter++;
		v1 = new Virus();
		age= assignAge();				// allotting age based on normal distribution pattern
		immunityStrength=getImmunityStrength();
		if (Math.random()<0.5) currentLocation = home.getRandomLocation();
		else currentLocation = workplace.getRandomLocation();
		x = currentLocation.x;
		y = currentLocation.y;
		chanceToVisitPublic=simulationConfig.chanceToVisitPublic;
	}
	
	//called every simulation tick
	public void update() {
		hasBeenExposedThisTick = false;
		ticksSinceTested++;
		if(isInfected) handleInfection();
		if(isQuarantined) handleQuarantine();
		else if(tasks.isEmpty()) assignTask();
		if(!tasks.isEmpty() && tasks.peek().run()) tasks.remove();			
	}
	
	public void handleInfection() {
		ticksSinceInfected++;
		if (ticksSinceInfected>v1.infectionPeriod) {
			Map.totalActiveInfected--;
			if(!isSymptomatic || !tryToKill()) {
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
			if(!simulationConfig.testMode) OutputWriter.writeInfectionData(id,age, immunityStrength,spreaderId,infectionBuildingId, infectionBuildingType,isSymptomatic,isTestedInfected);
		}
	}
	
	public void handleQuarantine() {
		ticksSinceQuarantined++;
		if((currentLocation==null ||currentLocation.building!=home) && tasks.isEmpty()) {
			tasks.add(new MoveTask(this,home.getRandomLocation()));
		}
		if (ticksSinceQuarantined>simulationConfig.quarantinePeriod) {
			isQuarantined=false;
			Map.totalQuarantined--;
		}
	}
	
	public void assignTask() {
		if(Math.random()<chanceToVisitPublic) {
			tasks.clear();
			Location publicLocation = pickPublicLocation();
			if(!publicLocation.building.isLockdown) {
				tasks.add(new MoveTask(this,publicLocation));
				tasks.add(new WorkTask(this,simulationConfig.timeSpentInPublic*(0.8+Math.random()*0.4)));  //Random +- 20%
			}			
			
		}
		else if(currentLocation.building == home && !workplace.isLockdown && (!simulationConfig.limitedReOpening || 
				((double)workplace.getCurrentOccupancy())/((OfficeBuilding)workplace).workers.size()<simulationConfig.limitedOccupancyPercentage)){  
			tasks.add(new MoveTask(this,workplace.getRandomLocation()));
			tasks.add(new WorkTask(this,simulationConfig.timeSpentInOffice*(0.8+Math.random()*0.4))); //Random +- 20%
		}	
		else{
			tasks.add(new MoveTask(this,home.getRandomLocation()));
			tasks.add(new WorkTask(this,simulationConfig.timeSpentInHome*(0.8+Math.random()*0.4))); //Random +- 20%
		}
	}
	
	public Location pickPublicLocation() {
		Random random = new Random();
		Location randomPublicLocation;
		if(simulationConfig.publicEventBuilding==null || random.nextDouble()>simulationConfig.publicEventPopularity) {
			Building randomPublicBuilding = Map.pickCloseBuilding(Map.public_places, currentLocation.building);
			randomPublicLocation = randomPublicBuilding.getRandomLocation();
		}
		else {
			randomPublicLocation = simulationConfig.publicEventBuilding.getRandomLocation();
		}
		return randomPublicLocation;
	}
	
	public boolean tryToInfect(Person spreader) {
		if (isImmune || hasBeenExposedThisTick) return false; //Do not spread if person is immune or has already been tried to spread to this tick
		double dist = Utils.getDistance(this,spreader);
		if(dist>simulationConfig.spreadRange) return false;  //Do not spread if outside the virus's droplet range
		hasBeenExposedThisTick = true;
		
		double chance=0.0;    //Factor that will decide if the spread is successful or not
		chance += v1.infectivity;    //Base infectivity of the virus
		chance -= immunityStrength;  //Base immune strength of the person
		if(simulationConfig.maskEnforcement) chance-=0.15; //Masks help!
		if(simulationConfig.socialDistancing && dist>(simulationConfig.spreadRange/2)) chance-=0.15; //Reduces infectivity at medium ranges
			
		if(chance>1) {
			if(spreaderId==-1)spreaderId = spreader.id;
			infectPerson();
			return true;
		}
		else return false;
	}
	
	public void infectPerson() {
		isInfected=true;
		Map.totalInfected++;
		Map.totalActiveInfected++;
		if(infectionBuildingType=="seed") {
			infectionBuildingType=currentLocation.building.getClass().getSimpleName();
			infectionBuildingId = currentLocation.building.id;
		}
	}
	
	public boolean tryToKill() {
		double chance = 0.0;
		chance += v1.lethality;
		chance -= immunityStrength;
		if(chance>1) {
			killPerson();
			return true;
		}
		else return false;
	}
	
	public void killPerson() {
		if(currentLocation!=null) {
			currentLocation.persons.remove(this);
			if(currentLocation.building!=null) currentLocation.building.persons.remove(this);
		}
		((OfficeBuilding)workplace).workers.remove(this);
		((HouseBuilding)home).residents.remove(this);
		Map.totalDead++;
		isDead=true;
		if(isQuarantined) {
			isQuarantined=false;
			Map.totalQuarantined--;
		}
		if(!simulationConfig.testMode) OutputWriter.writeDeathData(id,age, immunityStrength, isTestedInfected);
	}
	
	public void takeTest() {
		ticksSinceTested = 0;
		Map.totalTests++;
		if(isInfected && Math.random()<0.8) {  //assumed chance of false negative is 20%
			isTestedInfected = true;
			Map.totalPositiveTests++;
			if(simulationConfig.lockdownOnTest) workplace.isLockdown=true;
			if(simulationConfig.quarantineOnTest && !isQuarantined) {
				isQuarantined= true;
				Map.totalQuarantined++;
			}
			if(simulationConfig.contactTracing) {
				for(Person p : workplace.persons) {
					if(p.ticksSinceTested>simulationConfig.testCooldown) p.takeTest();
				}
				for(Person p : home.persons) {
					if(p.ticksSinceTested>simulationConfig.testCooldown) p.takeTest();
				}
			}
		}
	}

	public int assignAge() {
		Random random = new Random();
		int age =Math.abs((int)(random.nextGaussian()*15+35));   // mean=35 and std deviation of 15
		return age;
	}
	
	public double getImmunityStrength() {
		Random random = new Random();
		if(simulationConfig.testMode) random.setSeed(420);
		double temp = random.nextDouble();
		if(this.age<15 || this.age >60) temp-=0.2; // reducing immunity for kids and older people
		return temp;
	}
	
}
