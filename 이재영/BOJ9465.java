import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ9465 {
	static int T, N;
	static int[][] stickers;
	static int[][] dp; // 각 열 별 선택 경우의 수 (0: 선택안함, 1: 위, 2: 아래)

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		T = Integer.parseInt(br.readLine());

		while (T-- > 0) {
			N = Integer.parseInt(br.readLine());

			stickers = new int[2][N];
			dp = new int[N][3];
			for (int i = 0; i < N; i++) {
				dp[i][0] = 0;
			}

			for (int i = 0; i < 2; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					stickers[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			dp[0][1] = stickers[0][0];
			dp[0][2] = stickers[1][0];

//			i번째 줄의 값을 선택하지 않은경우
//			dp[i][0] = max(dp[i-1][0], dp[i-1][1], dp[i-1][2])
//			i번째 줄의 값을 위쪽을 선택한경우, 이전에 안고른 경우, 이전에 아래를 고른 경우
//			dp[i][1] = max(dp[i-1][0], dp[i-1][2])+stickers[0][i-1]
//			i번째 줄의 값을 아래쪽을 선택한경우
//			dp[i][2] = max(dp[i-1][0], dp[i-1][1])+stickers[1][i-1]

			for (int i = 1; i < N; i++) {
				dp[i][0] = Math.max(dp[i - 1][0], Math.max(dp[i - 1][1], dp[i - 1][2]));
				dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][2]) + stickers[0][i];
				dp[i][2] = Math.max(dp[i - 1][0], dp[i - 1][1]) + stickers[1][i];
			}

			System.out.println(Math.max(dp[N - 1][0], Math.max(dp[N - 1][1], dp[N - 1][2])));
		}
	}

}