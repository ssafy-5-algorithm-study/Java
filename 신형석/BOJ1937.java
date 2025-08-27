import java.io.*;

public class Main 
{
	static int N;
	static int[][] map;
	static int[][] dp;
	static int[] dx = {-1, 1, 0, 0};  // 상하좌우
	static int[] dy = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = br.readLine().trim().split(" ");
		
		N = Integer.parseInt(tokens[0]);
		
		map = new int[N][N];
		dp = new int[N][N];
		
		// 지도 입력
		for (int i = 0; i < N; i++)
		{
			tokens = br.readLine().trim().split(" ");
			for (int j = 0; j < N; j++) 
			{
				map[i][j] = Integer.parseInt(tokens[j]);
			}
		}
		
		int maxValue = Integer.MIN_VALUE;
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++) 
			{
				maxValue = Math.max(dfs(i, j), maxValue);
			}
		}
		System.out.println(maxValue);
		br.close();
	}
	
	static int dfs(int r, int c) 
	{
		if (dp[r][c] != 0)
		{
			return dp[r][c];
		}
		
		dp[r][c] = 1;
		
		for (int i = 0; i < 4; i++)
		{
			int mr = r + dx[i];
			int mc = c + dy[i];

			if (mr < 0 || mc < 0 || mr >= N || mc >= N) continue;
			if (map[mr][mc] > map[r][c])
			{
				dp[r][c] = Math.max(dp[r][c], dfs(mr, mc) + 1);
			}
		}
		
		return dp[r][c];
	}
}
