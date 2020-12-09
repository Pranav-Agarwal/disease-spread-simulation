package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


class UtilsTest {

	@Test
	void TestDistanceOfficeBuildings() {
		
		List<Location> locs1 = new ArrayList<>();
		locs1.add(new Location(1,1));
		locs1.add(new Location(5,6));
		
		List<Location> locs2 = new ArrayList<>();
		locs2.add(new Location(2,2));
		locs2.add(new Location(5,6));
		Building b1 = new OfficeBuilding(locs1);
		Building b2 = new OfficeBuilding(locs2);
		Assertions.assertEquals(1,Utils.getDistance(b1,b2));
	}
	
	@Test
	void TestDistanceHouseBuildings() {

		List<Location> locs1 = new ArrayList<>();		
		locs1.add(new Location(1,4));
		locs1.add(new Location(1,6));		
		List<Location> locs2 = new ArrayList<>();
		locs2.add(new Location(1,2));
		locs2.add(new Location(1,3));
		Building b1 = new HouseBuilding(locs1);
		Building b2 = new HouseBuilding(locs2);
		Assertions.assertEquals(2,Utils.getDistance(b1,b2));
	}


	@Test
	void TestDistanceHouseBuildings2() {
		List<Location> locs1 = new ArrayList<>();		
		locs1.add(new Location(1,4));
		locs1.add(new Location(1,6));		
		List<Location> locs2 = new ArrayList<>();
		locs2.add(new Location(1,4));
		locs2.add(new Location(1,3));
		Building b1 = new HouseBuilding(locs1);
		Building b2 = new HouseBuilding(locs2);
		Assertions.assertEquals(0,Utils.getDistance(b1,b2));
	}
	
	@Test
	void TestDistanceBetnPersons() {
		List<Location> locs1 = new ArrayList<>();		
		locs1.add(new Location(1,4));		
		List<Location> locs2 = new ArrayList<>();
		locs2.add(new Location(1,4));
		Building b1 = new HouseBuilding(locs1);
		Building b2 = new OfficeBuilding(locs2);
		
		Person p1 = new Person(b1,b2);
		Person p2 = new Person(b1,b2);
		
		Assertions.assertEquals(0,Utils.getDistance(p1, p2));
		

	}
	
 	@Test
 
	void TestDistanceBetnPersons2() {
		List<Location> locs1 = new ArrayList<>();		
		locs1.add(new Location(1,6));		
		List<Location> locs2 = new ArrayList<>();
		locs2.add(new Location(1,4));
		Building b1 = new HouseBuilding(locs1);
		Building b2 = new OfficeBuilding(locs2);
		
		Person p1 = new Person(b1,b2);
		Person p2 = new Person(b1,b2);
		
		int outcome=Utils.getDistance(p1, p2);
		Assertions.assertTrue((outcome==2) ||(outcome==0));
 	}
	

}