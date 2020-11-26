package model;

public class Utils {
	
	public static int getDistance(Building b1,Building b2) {
		int x1 = b1.locations.get(0).x;
		int y1 = b1.locations.get(0).y;
		int x2 = b2.locations.get(0).x;
		int y2 = b2.locations.get(0).y;
		return (int)Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	}
	
}
