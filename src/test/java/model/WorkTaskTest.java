package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WorkTaskTest {

	@BeforeAll
	static void setupState() {
		new Map();
	}
	
	@Test
	void testRun1() {
		Person p = new Person(Map.grid[0][0],Map.grid[1][1]);
		WorkTask testTask = new WorkTask(p,3);
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(true,testTask.run());
	}
	
	@Test
	void testRun2() {
		Person p = new Person(Map.grid[0][0],Map.grid[1][1]);
		WorkTask testTask = new WorkTask(p,5);
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(false,testTask.run());
		Assertions.assertEquals(true,testTask.run());
	}

}
