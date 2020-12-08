package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WorkTaskTest {

	static Building b1;
	@BeforeAll
	static void setupState() {
		new Map();
		List<Location> locs = new ArrayList<>();
		locs.add(new Location(1,1));
		locs.add(new Location(1,2));
		locs.add(new Location(1,3));
		b1 = new OfficeBuilding(locs);
	}
	
	@Test
	void testRun1() {
		Person p = new Person(b1,b1);
		WorkTask testTask = new WorkTask(p,3);
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(true,testTask.run());
	}
	
	@Test
	void testRun2() {
		Person p = new Person(b1,b1);
		WorkTask testTask = new WorkTask(p,5);
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(true,testTask.run());
	}

}
