package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Location.Type;

class MapTest {
	
	static Building b1;
	@BeforeEach
	void setupState() {
		new Map();
		simulationConfig.testMode = true;
	}

	@Test
	void testAddChartValue() {
		LinkedList<Integer> l1 = new LinkedList<>();
		Map.addChartValue(l1, 5);
		Assertions.assertEquals(1,l1.size());
		Assertions.assertEquals(5,l1.getFirst());
	}
	
	@Test
	void testAddPeople() {	
		List<Location> locations1 = new ArrayList<>();
		locations1.add(new Location(1,1));
		List<Location> locations2 = new ArrayList<>();
		locations2.add(new Location(90,90));
		Building b1 = new OfficeBuilding(locations1);
		Building b2 = new HouseBuilding(locations2);
		
		Map.offices.add(b1);
		Map.houses.add(b2);
		
		Map.instance.addPeople(3);
		Assertions.assertEquals(3,Map.persons.size());
		for (Person p : Map.persons) {
			Assertions.assertEquals(b1,p.workplace);
			Assertions.assertEquals(b2,p.home);
		}
	}

	@Test
	void testPickCloseBuilding() {
		List<Location> locations1 = new ArrayList<>();
		locations1.add(new Location(1,1));
		List<Location> locations2 = new ArrayList<>();
		locations2.add(new Location(90,90));
		List<Location> locations3 = new ArrayList<>();
		locations3.add(new Location(8,8));
		List<Building> testList = new ArrayList<>();
		Building b1 = new OfficeBuilding(locations1);
		Building b2 = new OfficeBuilding(locations2);
		Building b3 = new OfficeBuilding(locations3);
		testList.add(b1);
		testList.add(b2);
		testList.add(b3);
		
		simulationConfig.closenessFactor = 1;
		
		List<Location> locationsTest1 = new ArrayList<>();
		locationsTest1.add(new Location(2,2));
		Building t1 = new OfficeBuilding(locationsTest1);	
		List<Location> locationsTest2 = new ArrayList<>();
		locationsTest2.add(new Location(70,70));
		Building t2 = new OfficeBuilding(locationsTest2);
		List<Location> locationsTest3 = new ArrayList<>();
		locationsTest3.add(new Location(14,14));
		Building t3 = new OfficeBuilding(locationsTest3);

		Assertions.assertEquals(b1,Map.pickCloseBuilding(testList,t1));
		Assertions.assertEquals(b2,Map.pickCloseBuilding(testList,t2));
		Assertions.assertEquals(b3,Map.pickCloseBuilding(testList,t3));
	}

	@Test
	void testSeedVirus() {
		List<Location> locs = new ArrayList<>();
		locs.add(new Location(1,1));
		locs.add(new Location(1,2));
		locs.add(new Location(1,3));
		b1 = new OfficeBuilding(locs);
		
		Person p1 = new Person(b1,b1);
		Person p2 = new Person(b1,b1);
		Person p3 = new Person(b1,b1);
		Person p4 = new Person(b1,b1);
		Person p5 = new Person(b1,b1);
		
		Map.persons.add(p1);
		Map.persons.add(p2);
		Map.persons.add(p3);
		Map.persons.add(p4);
		Map.persons.add(p5);
		
		Map.instance.seedVirus(3);
		
		int count = 0;
		for(Person p : Map.persons) if(p.isInfected) count++;
		Assertions.assertEquals(3,count);
	}

	@Test
	void testSeedBuildingPublic() {
		Map.instance.seedBuilding(Type.PUBLIC,3,simulationConfig.publicMinSize,simulationConfig.publicMaxSize);
		Assertions.assertEquals(3,Map.public_places.size());
	}

	@Test
	void testSeedBuildingRectangle() {
		Map.instance.seedBuilding(Type.WORK,5,simulationConfig.officeSize,simulationConfig.officeSizeVariation);
		Assertions.assertEquals(5,Map.offices.size());	
	}
	
	@Test
	void testSpreadBuildingRectangle() {
		Random random = new Random(420);
		int t1 = (int) (random.nextDouble()*(4)+(8))+1;
		int t2 = (int) (random.nextDouble()*(4)+(8))+1;
		List<Location> locations = new ArrayList<>();
		Building b1 = new HouseBuilding(locations);
		Map.instance.spreadBuilding(b1, locations, 10, 2, 1, 1, Location.Type.HOUSE);
		Assertions.assertEquals((t1*t2),locations.size());
	}

	@Test
	void testCreatePublicEvent() {
		Map.instance.seedBuilding(Type.PUBLIC,3,simulationConfig.publicMinSize,simulationConfig.publicMaxSize);
		Assertions.assertNull(simulationConfig.publicEventBuilding);
		Map.instance.createPublicEvent();
		Assertions.assertTrue(simulationConfig.publicEventBuilding instanceof PublicBuilding );
	}

	@Test
	void testStopPublicEvent() {
		Map.instance.seedBuilding(Type.PUBLIC,3,simulationConfig.publicMinSize,simulationConfig.publicMaxSize);
		Map.instance.createPublicEvent();
		Assertions.assertTrue(simulationConfig.publicEventBuilding instanceof PublicBuilding );
		Map.instance.stopPublicEvent();
		Assertions.assertNull(simulationConfig.publicEventBuilding);
	}

	@Test
	void testLockdownBuildings() {
		List<Location> locations = new ArrayList<>();
		List<Building> testList = new ArrayList<>();
		Building b1 = new OfficeBuilding(locations);
		Building b2 = new OfficeBuilding(locations);
		Building b3 = new OfficeBuilding(locations);
		testList.add(b1);
		testList.add(b2);
		testList.add(b3);
		Assertions.assertTrue(!b1.isLockdown);
		Assertions.assertTrue(!b2.isLockdown);
		Assertions.assertTrue(!b3.isLockdown);
		Map.instance.lockdownBuildings(testList);
		Assertions.assertTrue(b1.isLockdown);
		Assertions.assertTrue(b2.isLockdown);
		Assertions.assertTrue(b3.isLockdown);
	}

	@Test
	void testLiftLockdownBuildings() {
		List<Location> locations = new ArrayList<>();
		List<Building> testList = new ArrayList<>();
		Building b1 = new OfficeBuilding(locations);
		Building b2 = new OfficeBuilding(locations);
		Building b3 = new OfficeBuilding(locations);
		testList.add(b1);
		testList.add(b2);
		testList.add(b3);
		Map.instance.lockdownBuildings(testList);
		Assertions.assertTrue(b1.isLockdown);
		Assertions.assertTrue(b2.isLockdown);
		Assertions.assertTrue(b3.isLockdown);
		Map.instance.liftLockdownBuildings(testList);
		Assertions.assertTrue(!b1.isLockdown);
		Assertions.assertTrue(!b2.isLockdown);
		Assertions.assertTrue(!b3.isLockdown);
	}

	@Test
	void testEnforceQuarantine() {
		List<Location> locs = new ArrayList<>();
		locs.add(new Location(1,1));
		locs.add(new Location(1,2));
		locs.add(new Location(1,3));
		b1 = new OfficeBuilding(locs);
		
		Person p1 = new Person(b1,b1);
		p1.isDead = true;
		Person p2 = new Person(b1,b1);
		p2.isTestedInfected = false;
		Person p3 = new Person(b1,b1);
		p3.isImmune = true;
		Person p4 = new Person(b1,b1);
		p4.isQuarantined = true;
		Person p5 = new Person(b1,b1);
		p5.isDead = false;
		p5.isTestedInfected = true;
		p5.isImmune = false;
		p5.isQuarantined = false;
		
		Map.persons.add(p1);
		Map.persons.add(p2);
		Map.persons.add(p3);
		Map.persons.add(p4);
		Map.persons.add(p5);
		
		Map.instance.enforceQuarantine();
		
		Assertions.assertTrue(!p1.isQuarantined);
		Assertions.assertTrue(!p2.isQuarantined);
		Assertions.assertTrue(!p3.isQuarantined);
		Assertions.assertTrue(p4.isQuarantined);
		Assertions.assertTrue(p5.isQuarantined);
	}

}
