import java.io.*;
import java.util.*;

public class BOJ1520 {
    static int M, N; // M: 세로 크기, N: 가로 크기
    static int[][] map;
    static int[][] dp;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    
    static int dfs(int x, int y) {
        // 목적지에 도달한 경우
        if(x == M-1 && y == N-1) {
            return 1;
        }
        
        // 이미 계산된 경우
        if(dp[x][y] != -1) {
            return dp[x][y];
        }
        
        // 새로 계산하는 경우
        dp[x][y] = 0;
        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            // 지도 범위 내이고 내리막길인 경우
            if(nx >= 0 && nx < M && ny >= 0 && ny < N && map[nx][ny] < map[x][y]) {
                dp[x][y] += dfs(nx, ny);
            }
        }
        
        return dp[x][y];
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        
        // M행 N열 크기로 맵과 DP 테이블 초기화
        map = new int[M][N];
        dp = new int[M][N];
        
        // DP 테이블을 -1로 초기화
        for(int i = 0; i < M; i++) {
            Arrays.fill(dp[i], -1);
        }
        
        // 지도 정보 입력
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        System.out.println(dfs(0, 0));
    }
}
