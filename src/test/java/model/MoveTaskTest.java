package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MoveTaskTest {
	
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
		Person p1 = new Person(b1,b1);
		p1.x = 0;
		p1.y = 0;
		MoveTask testTask = new MoveTask(p1,Map.grid[2][2]);
		testTask.run();
		Assertions.assertEquals(1,p1.x);
		Assertions.assertEquals(1,p1.y);
		testTask.run();
		Assertions.assertEquals(2,p1.x);
		Assertions.assertEquals(2,p1.y);
	}
	
	@Test
	void testRun2() {
		Person p1 = new Person(b1,b1);
		p1.x = 2;
		p1.y=2;
		MoveTask testTask = new MoveTask(p1,Map.grid[0][0]);
		testTask.run();
		Assertions.assertEquals(1,p1.x);
		Assertions.assertEquals(1,p1.y);
		testTask.run();
		Assertions.assertEquals(0,p1.x);
		Assertions.assertEquals(0,p1.y);
	}

	@Test
	void testFindMovementDirection1() {
		Person p1 = new Person(b1,b1);
		p1.x = 0;
		p1.y = 0;
		MoveTask testTask = new MoveTask(p1,Map.grid[1][1]);
		int[] testDir = testTask.findMovementDirection();
		Assertions.assertEquals(1,testDir[0]);
		Assertions.assertEquals(1,testDir[1]);
	}
	
	@Test
	void testFindMovementDirection2() {
		Person p1 = new Person(b1,b1);
		p1.x = 2;
		p1.y = 2;
		MoveTask testTask = new MoveTask(p1,Map.grid[1][1]);
		int[] testDir = testTask.findMovementDirection();
		Assertions.assertEquals(-1,testDir[0]);
		Assertions.assertEquals(-1,testDir[1]);
	}

}
