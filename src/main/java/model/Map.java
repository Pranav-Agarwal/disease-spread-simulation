package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Map {
	static Location[][] grid;
	static Set<Person> persons;
	static int size;
	static char[][] map;
	
	public Map(int size) {
		Map.size = size;
		persons = new HashSet<>();
		grid = new Location[size][size];
		map = new char[size][size];
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				grid[i][j] = new Location(i,j);
				map[i][j] = '-';
			}
		}
	}
	
	public void addPeople(int count) {
		for(int i=0;i<count;i++) {
			int a =  (int) ((Math.random() * (size-1 - 0)) + 0);
			int b =  (int) ((Math.random() * (size-1 - 0)) + 0);
			int c =  (int) ((Math.random() * (size-1 - 0)) + 0);
			int d =  (int) ((Math.random() * (size-1 - 0)) + 0);
			persons.add(new Person(grid[a][b],grid[c][d]));
		}
	}
	
	public void update() {
		for(Person p:persons) {
			p.update();
		}
	}
	
	public void refreshAndPrintMap() {
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(grid[i][j].persons.size()==0) System.out.print("- ");
				else System.out.print("O ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
