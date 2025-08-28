import java.io.*;
import java.util.*;

public class BOJ1937 {
    static int n;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};
    static int[][] bamboo;
    static int[][] dp; //(i,j)에서 시작했을 때 최대 칸수
    
    static int dfs(int x, int y) {
        if(dp[x][y] != 0) {
            return dp[x][y];
        }
        dp[x][y] = 1;
        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < n && ny >= 0 && ny < n && bamboo[nx][ny] > bamboo[x][y]) {
                // 이동할 수 있는 최대 칸 수 갱신
                dp[x][y] = Math.max(dp[x][y], dfs(nx, ny) + 1);
            }
        }
        return dp[x][y];
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        bamboo = new int[n][n];
        dp = new int[n][n]; //(i,j)에서 시작했을 때 최대 칸수
        
        for(int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                bamboo[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        int maxMove = Integer.MIN_VALUE;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                maxMove = Math.max(maxMove, dfs(i, j));
            }
        }
        
        System.out.println(maxMove);
    }
}
