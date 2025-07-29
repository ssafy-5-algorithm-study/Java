import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ2573 {
	static int N, M;
	static int turns = 0, continents = 1;
	static int[][] map;
	static boolean[][] visited;
	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		while (continents == 1) {
			turns++;
			int[][] meltAmount = new int[N][M];
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < M; c++) {
					if (map[r][c] > 0) {
						int count = 0;
						for (int dir = 0; dir < 4; dir++) {
							int nr = r + dr[dir];
							int nc = c + dc[dir];

							if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] < 1) {
								count++;
							}

						}
						meltAmount[r][c] = count;
					}
				}
			}
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < M; c++) {
					map[r][c] -= meltAmount[r][c];
				}
			}

			continents = countContinents();
		}

		System.out.println(continents >= 2 ? turns : 0);
	}

	private static int countContinents() {
		visited = new boolean[N][M];
		int count = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] > 0 && !visited[r][c]) {
					paintContinent(r, c);
					count++;
				}
			}
		}
		return count;
	}

	private static void paintContinent(int r, int c) {
		visited[r][c] = true;

		for (int dir = 0; dir < 4; dir++) {
			int nr = r + dr[dir];
			int nc = c + dc[dir];

			if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] > 0 && !visited[nr][nc]) {
				paintContinent(nr, nc);
			}
		}
	}
}