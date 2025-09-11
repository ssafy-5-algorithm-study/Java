import java.io.*;
import java.util.*;

public class BOJ1103 {
    static int N, M;
    static char[][] map;
    static int[][] dp;
    static boolean[][] visited;
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        map = new char[N][M];
        dp = new int[N][M];
        visited = new boolean[N][M];
        
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
                dp[i][j] = -1;
            }
        }
        
        int result = dfs(0, 0);
        System.out.println(result);
    }
    
    private static int dfs(int r, int c) {
        // 구멍에 떨어진 경우
        if (map[r][c] == 'H') {
            return 0;
        }
        
        // 이미 방문 중인 경우 (무한 루프)
        if (visited[r][c]) {
            System.out.println(-1);
            System.exit(0);
        }
        
        // 이미 계산된 경우
        if (dp[r][c] != -1) {
            return dp[r][c];
        }
        
        visited[r][c] = true;
        dp[r][c] = 0;
        
        int move = map[r][c] - '0';
        
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i] * move;
            int nc = c + dc[i] * move;
            
            // 보드 밖으로 나가는 경우: 1번 이동으로 게임 종료
            if (nr < 0 || nr >= N || nc < 0 || nc >= M) {
                dp[r][c] = Math.max(dp[r][c], 1);
            }
            // 보드 내부로 이동하는 경우
            else {
                dp[r][c] = Math.max(dp[r][c], dfs(nr, nc) + 1);
            }
        }
        
        visited[r][c] = false;
        return dp[r][c];
    }
}