import java.util.*;
import java.io.*;

public class Main 
{
    public static void main(String[] args) throws IOException 
    {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        
        while (T-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            
            // 스티커 점수 입력
            int[][] sticker = new int[2][n];
            for (int i = 0; i < 2; i++) {
            	String[] tokens = br.readLine().trim().split(" ");
                for (int j = 0; j < n; j++) {
                    sticker[i][j] = Integer.parseInt(tokens[j]);
                }
            }
            
            // DP 테이블 생성
            // dp[i][0] = i번째 열 위쪽 선택
            // dp[i][1] = i번째 열 아래쪽 선택  
            // dp[i][2] = i번째 열 선택 안함
            int[][] dp = new int[n][3];
            
            // 초기값 설정
            dp[0][0] = sticker[0][0];
            dp[0][1] = sticker[1][0];
            dp[0][2] = 0;
            
            // DP 점화식 적용
            for (int i = 1; i < n; i++) {
            	// i번째 열 위쪽을 선택하는 경우, i-1번째의 아래쪽과, 선택하지 않는 경우의 수 두 가지를 비교하여
            	// 큰쪽을 더해줌
                dp[i][0] = Math.max(dp[i-1][1], dp[i-1][2]) + sticker[0][i];
                // i번째 열 아래쪽을 선택하는 경우, i-1번째의 위쪽과, 선택하지 않는 경위의 수 두 가지를 비교하여
                // 큰쪽을 더해줌
                dp[i][1] = Math.max(dp[i-1][0], dp[i-1][2]) + sticker[1][i];
                // i번째 열을 선택하지 않는 경우, 이전에 있었던 값들 중 최대값을 들고옴
                dp[i][2] = Math.max(Math.max(dp[i-1][0], dp[i-1][1]), dp[i-1][2]);
            }
            
            // 최댓값 출력
            int answer = Math.max(Math.max(dp[n-1][0], dp[n-1][1]), dp[n-1][2]);
            System.out.println(answer);
        }
        br.close();
    }
}
