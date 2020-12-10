package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonTest {
	
	static Building b1;
	static Building b2;
	static Building b3;
	@BeforeEach
	void setupState() {
		try {
			new simulationConfig("config.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Map();		
		List<Location> locs = new ArrayList<>();
		locs.add(new Location(1,1));
		locs.add(new Location(1,2));
		locs.add(new Location(1,3));
		b1 = new OfficeBuilding(locs);
		b2 = new HouseBuilding(locs);
		b3 = new PublicBuilding(locs);
	}
	
	@Test
	void testHandleInfection() {
		simulationConfig.infectionPeriod = 2;
		simulationConfig.incubationPeriod = 2;
		simulationConfig.chanceToGetSymptoms = 1;
		simulationConfig.lethality = 0;
		Person p1 = new Person(b1,b1);
		p1.isTestedInfected = true;
		p1.isInfected = true;
		p1.handleInfection();
		Assertions.assertEquals(1,p1.ticksSinceInfected);
		p1.handleInfection();
		Assertions.assertEquals(2,p1.ticksSinceInfected);
		Assertions.assertTrue(p1.isSymptomatic);
		p1.handleInfection();
		Assertions.assertEquals(3,p1.ticksSinceInfected);
		Assertions.assertTrue(p1.isImmune);
		Assertions.assertTrue(!p1.isInfected);
	}

	@Test
	void testHandleQuarantine() {
		simulationConfig.quarantinePeriod = 1;
		Person p1 = new Person(b1,b1);
		p1.isQuarantined=true;
		p1.handleQuarantine();
		Assertions.assertEquals(1,p1.ticksSinceQuarantined);
		Assertions.assertTrue(p1.isQuarantined);
		p1.tasks.clear();
		p1.handleQuarantine();
		Assertions.assertTrue(!p1.isQuarantined);
		Assertions.assertEquals(1,p1.tasks.size());
		Assertions.assertTrue(p1.tasks.peek() instanceof MoveTask);
	}

	@Test
	void testAssignTask1() {
		Person p1 = new Person(b1,b2);
		p1.chanceToVisitPublic = 0;
		p1.tasks.clear();
		p1.currentLocation = b1.locations.get(0);
		p1.assignTask();
		Assertions.assertEquals(2,p1.tasks.size());
		Assertions.assertTrue(p1.tasks.peek() instanceof MoveTask);
		Assertions.assertEquals(b2.getRandomLocation(),((MoveTask)p1.tasks.peek()).destination);
	}
	
	@Test
	void testAssignTask2() {
		Person p1 = new Person(b1,b2);
		p1.chanceToVisitPublic = 0;
		p1.tasks.clear();
		p1.currentLocation = b2.locations.get(0);
		p1.assignTask();
		Assertions.assertEquals(2,p1.tasks.size());
		Assertions.assertTrue(p1.tasks.peek() instanceof MoveTask);
		Assertions.assertEquals(b1.getRandomLocation(),((MoveTask)p1.tasks.peek()).destination);
	}
	
	@Test
	void testAssignTask3() {
		Person p1 = new Person(b1,b2);
		p1.chanceToVisitPublic = 0;
		p1.tasks.clear();
		p1.currentLocation = b1.locations.get(0);
		b2.isLockdown = true;
		p1.assignTask();
		Assertions.assertEquals(2,p1.tasks.size());
		Assertions.assertTrue(p1.tasks.peek() instanceof MoveTask);
		Assertions.assertEquals(b1.getRandomLocation(),((MoveTask)p1.tasks.peek()).destination);
	}

	@Test
	void testPickPublicLocation() {
		simulationConfig.publicEventBuilding = b3;
		simulationConfig.publicEventPopularity = 1;
		Person p1 = new Person(b1,b2);
		Assertions.assertEquals(b3.getRandomLocation(),p1.pickPublicLocation());
	}

	@Test
	void testTryToInfect1() {
		simulationConfig.infectivity = 2.3;
		Person p1 = new Person(b2,b1);
		Person p2 = new Person(b2,b1);
		p1.immunityStrength = 1;
		p1.infectionBuildingType = "test";
		simulationConfig.spreadRange=300;
		Assertions.assertTrue(p1.tryToInfect(p2));
	}
	
	void testTryToInfect2() {
		simulationConfig.infectivity = 2;
		Person p1 = new Person(b2,b1);
		Person p2 = new Person(b2,b1);
		p1.immunityStrength = 1;
		p1.infectionBuildingType = "test";
		simulationConfig.spreadRange=300;
		Assertions.assertFalse(p1.tryToInfect(p2));
	}
	
	void testTryToInfect3() {
		simulationConfig.infectivity = 2.3;
		Person p1 = new Person(b2,b1);
		Person p2 = new Person(b2,b1);
		p1.immunityStrength = 1;
		p1.infectionBuildingType = "test";
		simulationConfig.maskEnforcement = true;
		simulationConfig.socialDistancing = true;
		simulationConfig.spreadRange=300;
		Assertions.assertFalse(p1.tryToInfect(p2));
	}

	@Test
	void testInfectPerson() {
		Person p1 = new Person(b2,b1);
		p1.infectionBuildingType = "test";
		Assertions.assertTrue(!p1.isInfected);
		p1.infectPerson();
		Assertions.assertTrue(p1.isInfected);
	}

	@Test
	void testTryToKill1() {
		simulationConfig.lethality = 2;
		Person p1 = new Person(b2,b1);
		p1.immunityStrength = 0;
		Assertions.assertTrue(p1.tryToKill());
	}
	
	@Test
	void testTryToKill2() {
		simulationConfig.lethality = 0;
		Person p1 = new Person(b2,b1);
		p1.immunityStrength = 1;
		Assertions.assertTrue(!p1.tryToKill());
	}

	@Test
	void testKillPerson() {
		Person p1 = new Person(b2,b1);
		Location t1 = p1.currentLocation;
		p1.killPerson();
		Assertions.assertTrue(p1.isDead);
		Assertions.assertTrue(!t1.persons.contains(p1));
		Assertions.assertTrue(!((HouseBuilding)(p1.home)).residents.contains(p1));
		Assertions.assertTrue(!((OfficeBuilding)(p1.workplace)).workers.contains(p1));
	}

	@Test
	void testGetImmunityStrength1() {
		Person p1 = new Person(b2,b1);
		p1.age = 35;
		Assertions.assertEquals(new Random(420).nextDouble(),p1.getImmunityStrength());
	}
	
	@Test
	void testGetImmunityStrength2() {
		Person p1 = new Person(b2,b1);
		p1.age = 61;
		Assertions.assertEquals(new Random(420).nextDouble()-0.2,p1.getImmunityStrength());
	}

}
