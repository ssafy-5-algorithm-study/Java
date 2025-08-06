import java.io.*;
import java.util.Arrays;

public class Main {
	static long[] patties_layer;
	static long[] total_layer;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = br.readLine().trim().split(" ");
		int N = Integer.parseInt(tokens[0]);
		long X = Long.parseLong(tokens[1]);
		patties_layer = new long[N + 1];
		total_layer = new long[N + 1];
		patties_layer[0] = 1;
		total_layer[0] = 1;
		for (int level = 1; level <= N; level++)
		{
			patties_layer[level] = patties_layer[level - 1] * 2 + 1;
			total_layer[level] = total_layer[level - 1] * 2 + 3;
		}
		System.out.println(eat_patties(N, X));
	}
	private static long eat_patties(int N, long X)
	{
		if (X == 0)
		{
			return 0;
		}
		else if (X >= total_layer[N])
		{
			return patties_layer[N];
		}
		else if (X < total_layer[N] && X > total_layer[N] / 2 + 1)
		{
			return patties_layer[N - 1] + 1 + eat_patties(N - 1, X - total_layer[N - 1] - 2);
		}
		else if (X == total_layer[N] / 2 + 1)
		{
			return patties_layer[N - 1] + 1;
		}
		else
		{
			return eat_patties(N - 1, X - 1); 
		}
	}
}
