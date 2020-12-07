package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MoveTaskTest {
	
	@BeforeAll
	static void setupState() {
		new Map();
	}
	
	@Test
	void testRun1() {
		Person p = new Person(Map.grid[0][0],Map.grid[0][0]);
		MoveTask testTask = new MoveTask(p,Map.grid[2][2]);
		testTask.run();
		Assertions.assertEquals(1,p.x);
		Assertions.assertEquals(1,p.y);
		testTask.run();
		Assertions.assertEquals(2,p.x);
		Assertions.assertEquals(2,p.y);
	}
	
	@Test
	void testRun2() {
		Person p = new Person(Map.grid[2][2],Map.grid[2][2]);
		MoveTask testTask = new MoveTask(p,Map.grid[0][0]);
		testTask.run();
		Assertions.assertEquals(1,p.x);
		Assertions.assertEquals(1,p.y);
		testTask.run();
		Assertions.assertEquals(0,p.x);
		Assertions.assertEquals(0,p.y);
	}

	@Test
	void testFindMovementDirection1() {
		MoveTask testTask = new MoveTask(new Person(Map.grid[0][0],Map.grid[0][0]),Map.grid[1][1]);
		int[] testDir = testTask.findMovementDirection();
		Assertions.assertEquals(1,testDir[0]);
		Assertions.assertEquals(1,testDir[1]);
	}
	
	@Test
	void testFindMovementDirection2() {
		MoveTask testTask = new MoveTask(new Person(Map.grid[2][2],Map.grid[2][2]),Map.grid[1][1]);
		int[] testDir = testTask.findMovementDirection();
		Assertions.assertEquals(-1,testDir[0]);
		Assertions.assertEquals(-1,testDir[1]);
	}

}
