import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 해당 구역이 모두 1로 구성되어있는지 확인
 * 2. 그렇다면 카운트 증가
 * 3. 아니라면 4분할 후 반복
 */
public class BOJ2630 {
	static int N;
	static int white = 0, blue = 0;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		makePaper(0, 0, N);
		
		System.out.println(white);
		System.out.println(blue);
	}

	
	public static void makePaper(int startR, int startC, int size) {
		if(size == 1) {
			if(map[startR][startC] == 1) {
				blue++;
			} else {
				white++;
			}
			return;
		}
		
		boolean isFull = true;
		int flag = map[startR][startC];
		for(int r = startR; r < startR+size; r++) {
			for(int c = startC; c < startC+size; c++) {
				if(map[r][c] != flag) {
					isFull = false;
					break;
				}
			}
		}
		
		if(isFull) {
			if(map[startR][startC] == 1) {
				blue++;
			} else {
				white++;
			}
			return;
		} else {
			size /= 2;
			makePaper(startR, startC, size);	// 좌상단
			makePaper(startR, startC+size, size);	// 우상단
			makePaper(startR+size, startC, size);	// 좌하단
			makePaper(startR+size, startC+size, size);	// 우하단
		}
	}
}
