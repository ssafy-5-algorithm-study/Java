import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 이항계수
 * (n k) = n!/k!(n-k!)
 */
public class BOJ11051 {
    static int N, K;
    static int MOD = 10007;
    static int[][] memoization;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        memoization = new int[N+1][N+1];  
        int result = bicoef(N,K);

        System.out.println(result);
    }
    private static int bicoef(int n, int k) {
        // nC0 = nCn = 1
        if(k == 0 || k == n) return 1;
        
        
        if(memoization[n][k] != 0) return memoization[n][k];
        
        // nCk = n-1Ck-1 + n-1Ck
        memoization[n][k] = (bicoef(n-1, k-1) + bicoef(n-1, k)) % MOD;
        // nCk = nCn-k
        // memoization[n][n-k] = memoization[n][k];
        
        return memoization[n][k];
    }
}