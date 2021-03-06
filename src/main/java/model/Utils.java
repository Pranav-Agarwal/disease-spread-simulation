package model;

//contains static methods used in the program
public class Utils {
	
	public static int getDistance(Building b1,Building b2) {
		int x1 = b1.locations.get(0).x;
		int y1 = b1.locations.get(0).y;
		int x2 = b2.locations.get(0).x;
		int y2 = b2.locations.get(0).y;
		return (int)Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	}
	
	public static int getDistance(Person p1,Person p2) {
		int x1 = p1.x;
		int y1 = p1.y;
		int x2 = p2.x;
		int y2 = p2.y;
		return (int)Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	}
	
	
}
