import java.util.*;
import java.io.*;

public class BOJ2502 {

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		
		int D = Integer.parseInt(stringTokenizer.nextToken());
		int K = Integer.parseInt(stringTokenizer.nextToken());
		
		int[] dp = new int[D+1];
		
		// dp[n] = dp[n-1] + dp[n-2]
		
		for(int i=1; i<100000; i++) {
			for(int j=1; j<100000; j++) {
				dp[0] = i;
				dp[1] = j;
				
				for(int k=2; k<D+1; k++) {
					dp[k] = dp[k-1] + dp[k-2];
				}

				if(dp[D-1] == K) {
					System.out.println(dp[0]);
					System.out.println(dp[1]);
					
					return;
				}
			}
		}
	}

}
