import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ1759
{
	static int L;
	static int C;
	static boolean[] visited;
	static String[] alphabet;
	static String[] word;
	static int sum;

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = br.readLine().trim().split(" ");
		L = Integer.parseInt(tokens[0]);
		C = Integer.parseInt(tokens[1]);
		visited = new boolean[C];
		alphabet = new String[C];
		tokens = br.readLine().trim().split(" ");
		for (int i = 0; i < C; i++)
		{
			alphabet[i] = tokens[i];
		}
		Arrays.sort(alphabet);
		word = new String[L];
		sum = 0;
		comp(0, 0);
	}

	private static void comp(int start, int count)
	{
		if (count == L)
		{
			int temp1 = 0;
			int temp2 = 0;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < word.length; i++)
			{
				sb.append(word[i]);
				if (word[i].equals("a") || word[i].equals("e") || word[i].equals("i") || word[i].equals("o")
						|| word[i].equals("u"))
				{
					temp1++;
				} else
				{
					temp2++;
				}
			}
			if (temp1 > 0 && temp2 > 1)
			{
				System.out.println(sb);
			}
			return;
		}

		for (int i = start; i < C; i++)
		{
			word[count] = alphabet[i];
			comp(i + 1, count + 1);
		}
	}
}