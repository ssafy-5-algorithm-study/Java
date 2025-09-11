import java.io.*;
import java.util.*;

public class BOJ1126 {
    static int N;
    static int[] blocks;
    static int[][] dp;
    static final int OFFSET = 250000; // 최대 차이값 offset
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        blocks = new int[N];
        
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            blocks[i] = Integer.parseInt(st.nextToken());
        }
        
        // dp[i][diff + OFFSET] = 첫 번째 탑과 두 번째 탑의 차이가 diff일 때, 첫 번째 탑의 최대 높이
        dp = new int[N + 1][2 * OFFSET + 1];
        
        // 초기화: -1은 불가능한 상태
        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], -1);
        }
        
        dp[0][OFFSET] = 0; // 초기 상태: 차이 0, 높이 0
        
        for (int i = 0; i < N; i++) {
            for (int diff = -OFFSET; diff <= OFFSET; diff++) {
                if (dp[i][diff + OFFSET] == -1) continue;
                
                int currentHeight = dp[i][diff + OFFSET];
                
                // 1. 현재 블록을 사용하지 않는 경우
                dp[i + 1][diff + OFFSET] = Math.max(dp[i + 1][diff + OFFSET], currentHeight);
                
                // 2. 첫 번째 탑에 추가하는 경우
                int newDiff1 = diff + blocks[i];
                if (newDiff1 >= -OFFSET && newDiff1 <= OFFSET) {
                    dp[i + 1][newDiff1 + OFFSET] = Math.max(dp[i + 1][newDiff1 + OFFSET], 
                                                           currentHeight + blocks[i]);
                }
                
                // 3. 두 번째 탑에 추가하는 경우
                int newDiff2 = diff - blocks[i];
                if (newDiff2 >= -OFFSET && newDiff2 <= OFFSET) {
                    dp[i + 1][newDiff2 + OFFSET] = Math.max(dp[i + 1][newDiff2 + OFFSET], 
                                                           currentHeight);
                }
            }
        }
        
        // 결과: 차이가 0일 때의 최대 높이
        int result = dp[N][OFFSET];
        System.out.println(result == 0 ? -1 : result);
    }
}