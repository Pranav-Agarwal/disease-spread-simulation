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
	private String infectionBuildingType="seed";
	private int infectionBuildingId;
	private int spreaderId=-1;
	
	//location state variables
	public Location currentLocation;
	public int x;
	public int y;
	public Location home;
	public Location workplace;
	public State state=State.IDLE;
	
	//property variables
	private Virus v1;
	private int id;
	private int age=0;
	private double immunityStrength=0.0;
	private double chanceToCatchInfection=0.0;
	private int ticksSinceInfected=0;
	private int ticksSinceQuarantined=0;
	private int ticksSinceTested = simulationConfig.testCooldown;
	private Queue<Task> tasks = new LinkedList<>();
	
	//config parameters from file
	private double chanceToKill;
	private double chanceToVisitPublic;
	
	public Person(Location home, Location workplace) {
		this.home = home;
		this.workplace = workplace;
		
		id = counter++;
		v1 = new Virus();
		age= getAge();				// allotting age based on normal distribution pattern
		immunityStrength=getImmunityStrength();
		if (Math.random()<0.5) currentLocation = home;
		else currentLocation = workplace;
		x = currentLocation.x;
		y = currentLocation.y;
		chanceToKill= chanceToKill();
		chanceToVisitPublic=simulationConfig.chanceToVisitPublic;
		chanceToCatchInfection=immunityStrength-v1.infectivity;
	}
	
	//called every simulation tick
	public void update() {
		ticksSinceTested++;
		if(isInfected) {
			ticksSinceInfected++;
			if (ticksSinceInfected>v1.infectionPeriod) {
				Map.totalActiveInfected--;
				if(chanceToKill<=0.3 && isSymptomatic) killPerson();  // inverse as we are subtracting lethality
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
			if (ticksSinceQuarantined>simulationConfig.quarantinePeriod) {
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
			else if(currentLocation == home && !workplace.building.isLockdown && (!simulationConfig.limitedReOpening || 
					((double)workplace.building.getCurrentOccupancy())/((OfficeBuilding)workplace.building).workers.size()<simulationConfig.limitedOccupancyPercentage)){  
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
	
	public void tryToInfect(Person spreader) {
		if (this.isImmune) return;
			double dist = Utils.getDistance(this,spreader);
			if (simulationConfig.socialDistancing==false || ( simulationConfig.socialDistancing==true && dist <simulationConfig.socialDistancing_radius))
				chanceToCatchInfection = chanceToCatchInfection-0.2;
			if (simulationConfig.maskEnforcement ==true)
				chanceToCatchInfection = chanceToCatchInfection+0.2;
			if(chanceToCatchInfection <= 0.5){   // inverse as we are subtracting infectivty
			isInfected=true;
			Map.totalInfected++;
			Map.totalActiveInfected++;
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
		if(isInfected && Math.random()<0.8) {  //assumed chance of false negative is 20%
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
		if(this.age<15 || this.age >60)
		{return random.nextDouble()-0.09;}  // reducing immunity for kids and older people
		else return random.nextDouble();
	}
	
	private double chanceToKill()
	{
		return this.immunityStrength - v1.lethality; 
	}

}
