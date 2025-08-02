import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ11051 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tokens = br.readLine().trim().split(" ");
        int N = Integer.parseInt(tokens[0]);
        int R = Integer.parseInt(tokens[1]);
        
        if (R > N - R) {
            R = N - R;
        }
        
        long[] dp = new long[R + 1];
        dp[0] = 1;
        
        for (int i = 1; i <= N; i++) {
            for (int j = Math.min(i, R); j > 0; j--) {
                dp[j] = (dp[j] + dp[j-1]) % 10007;
            }
        }
        
        System.out.println(dp[R]);
    }
}