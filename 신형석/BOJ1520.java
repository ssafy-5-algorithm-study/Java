import java.io.*;

public class Main {
	static int M, N;
	static int[][] map;
	static int[][] dp;
	static int[] dx = {-1, 1, 0, 0};  // 상하좌우
	static int[] dy = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = br.readLine().trim().split(" ");
		
		M = Integer.parseInt(tokens[0]);  // 세로 크기
		N = Integer.parseInt(tokens[1]);  // 가로 크기
		
		map = new int[M][N];
		dp = new int[M][N];
		
		// 지도 입력
		for (int i = 0; i < M; i++)
		{
			tokens = br.readLine().trim().split(" ");
			for (int j = 0; j < N; j++) 
			{
				map[i][j] = Integer.parseInt(tokens[j]);
			}
		}
		
		for (int i = 0; i < M; i++)
		{
			for (int j = 0; j < N; j++) 
			{
				dp[i][j] = -1;
			}
		}
		
		int result = dfs(0, 0);
		System.out.println(result);
		br.close();
	}
	
	static int dfs(int r, int c) 
	{
		if (r == M - 1 && c == N - 1) 
		{
			return 1;
		}
		
		if (dp[r][c] != -1)
		{
			return dp[r][c];
		}
		
		dp[r][c] = 0;
		
		for (int i = 0; i < 4; i++)
		{
			int mr = r + dx[i];
			int mc = c + dy[i];

			if (mr < 0 || mc < 0 || mr >= M || mc >= N) continue;
			if (map[mr][mc] < map[r][c])
			{
				dp[r][c] += dfs(mr, mc);
			}
		}
		
		return dp[r][c];
	}
}
