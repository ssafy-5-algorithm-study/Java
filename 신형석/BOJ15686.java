import java.io.*;
import java.util.*;
public class Main {
	static class Point
	{
		int x;
		int y;
		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString()
		{
			return x + ", " + y;
		}
	}
	static List<Point> house;
	static List<Point> chicken;
	static int N;
	static int M;
	static Point[] sel;
	static int min_distance = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = br.readLine().trim().split(" ");
		
		N = Integer.parseInt(tokens[0]);
		M = Integer.parseInt(tokens[1]);
		
		house = new ArrayList<Point>();
		chicken = new ArrayList<Point>();
		
		for (int i = 0; i < N; i++)
		{
			tokens = br.readLine().trim().split(" ");
			for (int j = 0; j < N; j++)
			{
				Point coord = new Point(i, j);
				if (Integer.parseInt(tokens[j]) == 1)
				{
					house.add(coord);
				}
				else if (Integer.parseInt(tokens[j]) == 2)
				{
					chicken.add(coord);
				}
			}
		}
		for (int i = 1; i <= M; i++)
		{
			sel = new Point[i];
			recursive(0, 0, i);	
		}
		System.out.println(min_distance);
	}
	
	private static void recursive(int start, int count, int limit)
	{
		if (count == limit)
		{
			if (min_distance > distance(sel))
			{
				min_distance = distance(sel);
			}
			return;
		}
		
		for (int i = start; i < chicken.size(); i++)
		{
			sel[count] = chicken.get(i);
			recursive(i + 1, count + 1, limit);
		}
	}
	
	private static int distance(Point[] sel)
	{
		int dis = 0;
		int temp = Integer.MAX_VALUE;
		for (Point p : house)
		{
			for (int i = 0; i < sel.length; i++)
			{
				if (sel[i] != null)
				{
					temp = Math.min(temp, get_dis(sel[i],  p));
				}
			}
			dis += temp;
			temp = Integer.MAX_VALUE;
		}
		return dis;
	}
	
	private static int get_dis(Point p1, Point p2)
	{
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}
}
