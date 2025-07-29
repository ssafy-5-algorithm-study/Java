import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BOJ 2573번 - 빙산 문제
 * 빙산이 매년 녹아서 분리되는데, 몇 년 후에 두 덩어리 이상으로 분리되는지 구하는 문제
 */
public class BOJ2573 {
	static int N, M; // 격자의 행과 열 크기
	static int turns = 0, continents = 1; // 경과 년수와 대륙(빙산 덩어리) 개수
	static int[][] map; // 빙산의 높이를 저장하는 격자
	static boolean[][] visited; // DFS 탐색시 방문 체크용 배열
	static int[] dr = { -1, 0, 1, 0 }; // 상하좌우 이동을 위한 행 방향 배열
	static int[] dc = { 0, 1, 0, -1 }; // 상하좌우 이동을 위한 열 방향 배열

	public static void main(String[] args) throws IOException {
		// 입력 받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];

		// 초기 빙산 높이 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 빙산이 두 덩어리 이상으로 분리될 때까지 시뮬레이션
		while (continents == 1) {
			turns++; // 년수 증가
			
			// 각 위치별로 녹을 양 계산
			int[][] meltAmount = new int[N][M];
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < M; c++) {
					if (map[r][c] > 0) { // 빙산이 있는 위치만 확인
						int count = 0;
						// 상하좌우 인접한 바다(0인 곳)의 개수 세기
						for (int dir = 0; dir < 4; dir++) {
							int nr = r + dr[dir];
							int nc = c + dc[dir];

							// 격자 범위 내이고 바다인 경우
							if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] < 1) {
								count++;
							}
						}
						meltAmount[r][c] = count; // 녹을 양 저장
					}
				}
			}
			
			// 계산된 녹을 양만큼 빙산 높이 감소
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < M; c++) {
					map[r][c] -= meltAmount[r][c];
					// 음수가 되면 0으로 처리 (사실 문제에서는 자동으로 0이 됨)
					if (map[r][c] < 0) map[r][c] = 0;
				}
			}

			// 현재 빙산 덩어리 개수 계산
			continents = countContinents();
		}

		// 결과 출력: 2개 이상으로 분리되면 년수, 완전히 녹으면 0
		System.out.println(continents >= 2 ? turns : 0);
	}

	/**
	 * 현재 빙산이 몇 개의 덩어리로 나누어져 있는지 계산
	 * DFS를 이용해 연결된 빙산들을 하나의 덩어리로 카운트
	 */
	private static int countContinents() {
		visited = new boolean[N][M]; // 방문 배열 초기화
		int count = 0; // 덩어리 개수
		
		// 모든 격자를 확인하여 빙산 덩어리 찾기
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				// 빙산이 있고 아직 방문하지 않은 위치
				if (map[r][c] > 0 && !visited[r][c]) {
					paintContinent(r, c); // DFS로 연결된 빙산 모두 방문 처리
					count++; // 새로운 덩어리 발견
				}
			}
		}
		return count;
	}

	/**
	 * DFS를 이용해 (r, c)에서 시작하여 연결된 모든 빙산을 방문 처리
	 * 상하좌우로 연결된 빙산들을 재귀적으로 탐색
	 */
	private static void paintContinent(int r, int c) {
		visited[r][c] = true; // 현재 위치 방문 처리

		// 상하좌우 4방향 탐색
		for (int dir = 0; dir < 4; dir++) {
			int nr = r + dr[dir];
			int nc = c + dc[dir];

			// 격자 범위 내이고, 빙산이 있으며, 아직 방문하지 않은 위치
			if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] > 0 && !visited[nr][nc]) {
				paintContinent(nr, nc); // 재귀적으로 연결된 빙산 탐색
			}
		}
	}
}