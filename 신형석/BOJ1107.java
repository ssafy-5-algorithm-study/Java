import java.io.*;
import java.util.*;
public class Main {
	static int[] digit = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	static final int start_channel = 100;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine().trim());
		int M = Integer.parseInt(br.readLine().trim());
		String[] tokens;
		if (M != 0)
		{
			tokens = br.readLine().trim().split(" ");
		}
		else
		{
			tokens = new String[0];
		}
		List<Integer> broken_buttons = new ArrayList<Integer>();
		for (int i = 0; i < M; i++)
		{
			broken_buttons.add(Integer.parseInt(tokens[i]));
		}
		if (N == start_channel)
		{
			System.out.println(0);
			return;
		}
		if (broken_buttons.size() == 10)
		{
			System.out.println(Math.abs(N - start_channel));
			return;
		}
		int N_down = N;
		int N_up = N;
		int N_down_min = Integer.MAX_VALUE;
		int N_up_min = Integer.MAX_VALUE;
		int near_channel = 0;
		int temp = Math.abs(N - start_channel);
		int button_click = 0;
		for (;;)
		{
			if (findNumbers(N_down, broken_buttons))
			{
				N_down_min = Math.min(N_down_min, N_down);
			}
			
			if (findNumbers(N_up, broken_buttons))
			{
				N_up_min = Math.min(N_up_min, N_up);
			}
			
			if (N_down_min != Integer.MAX_VALUE || N_up_min != Integer.MAX_VALUE)
			{
				near_channel = Math.min(N_down_min, N_up_min);
				button_click = findDigits(near_channel);
				break;
			}
			N_down = Math.max(0, N_down - 1);
			N_up++;
		}
		button_click = Math.min(Math.abs(N - near_channel) + button_click, temp);
		System.out.println(button_click);
		br.close();
	}
	
	private static int findDigits(int num)
	{
		int sum = 0;
		while (num > 0)
		{
			sum++;
			num /= 10;
		}
		return Math.max(sum, 1);
	}
	
	private static boolean findNumbers(int number, List<Integer> list)
	{
		if (number == 0)
		{
			return !list.contains(number);
		}
		while (number > 0)
		{
			for (int num : digit)
			{
				if (number % 10 == num && list.contains(num))
				{
					return false;
				}
			}
			number /= 10;
		}
		return true;
	}
}
