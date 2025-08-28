import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ1562 {
    static final int MOD = 1000000000;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        if (N < 10) {
            System.out.println(0);
            return;
        }
        // DP 배열 구조
        // dp[자리수][마지막숫자][비트마스크]
        int[][][] memo = new int[N+1][10][1<<10];

        /*
         * 10자리 전까지 모두 0
         * 10자리 : 1
         * 11자리 : 
         */
        for (int i = 1; i <= 9; i++) {  // 1~9로 시작 (0으로 시작 불가)
            memo[1][i][1 << i] = 1;      // 첫 번째 자리, 숫자 i 사용
        }

        for (int i = 2; i <= N; i++) {
            for (int j = 0; j < 10; j++) {
                for (int mask = 0; mask < (1 << 10); mask++) {
                    if (memo[i-1][j][mask] == 0) {
                        continue;
                    }
                    if (j > 0) {
                        int newMask = mask | (1 << (j - 1));
                        memo[i][j-1][newMask] = (memo[i][j-1][newMask] + memo[i-1][j][mask]) % MOD;
                    }
                    if (j < 9) {
                        int newMask = mask | (1 << (j + 1));
                        memo[i][j+1][newMask] = (memo[i][j+1][newMask] + memo[i-1][j][mask]) % MOD;
                    }
                }
            }
        }

        int target = (1 << 10) - 1;
        int result = 0;
        for (int i = 0; i < 10; i++) {
            result = (result + memo[N][i][target]) % MOD;
        }

        System.out.println(result);
    }
}
