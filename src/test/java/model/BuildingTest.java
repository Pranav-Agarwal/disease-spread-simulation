package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

class BuildingTest {
	
	@BeforeAll
	static void setupConfig() {
		simulationConfig.officeLockdown=false;
		simulationConfig.lockdownPeriod = 3;
	}

	@Test
	void TestUpdate1() {
		List<Location> locs = new ArrayList<>();
		locs.add(new Location(1,1));
		locs.add(new Location(1,2));
		locs.add(new Location(1,3));
		Building b1 = new OfficeBuilding(locs);
		Assertions.assertEquals(0,b1.timeSinceLockdown);
		b1.update();
		Assertions.assertEquals(0,b1.timeSinceLockdown);
		b1.isLockdown = true;
		b1.update();
		Assertions.assertEquals(1,b1.timeSinceLockdown);
		b1.update();
		b1.update();
		b1.update();
		Assertions.assertEquals(false,b1.isLockdown);
	}
	
	@Test
	void TestGetOccupancy1() {
		List<Location> locs = new ArrayList<>();
		locs.add(new Location(1,1));
		locs.add(new Location(1,2));
		locs.add(new Location(1,3));
		Building b1 = new PublicBuilding(locs);
		b1.persons.add(new Person(b1,b1));
		b1.persons.add(new Person(b1,b1));
		b1.persons.add(new Person(b1,b1));
		Assertions.assertEquals(3,b1.getCurrentOccupancy());
	}
	
	@Test
	void TestGetRandomLocation1() {
		List<Location> locs = new ArrayList<>();
		locs.add(new Location(1,1));
		locs.add(new Location(1,2));
		locs.add(new Location(1,3));
		Building b1 = new HouseBuilding(locs);
		Location testLocation = b1.getRandomLocation();
		Assertions.assertEquals(true,b1.locations.contains(testLocation));
	}

}
