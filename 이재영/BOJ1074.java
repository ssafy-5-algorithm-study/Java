import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 전체를 4분면으로 파악 
 * 각 사분면의 0번값 = 2^2*(N-1)*구역번호{0, 1, 2, 3}
 * 좌표를 이용해 전체 배열 4분할 후 해당 구역번호 확인
 * 좌표값을 /2 하며 해당 구역을 반복확인
 * N=1이면 본 좌표의 크기 계산
 */
public class BOJ1074 {
	static int N, targetR, targetC;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		targetR = Integer.parseInt(st.nextToken());
		targetC = Integer.parseInt(st.nextToken());

		int result = find(targetR, targetC, N, 0);

		System.out.println(result);
	}

	public static int find(int currR, int currC, int N, int startNum) {
		if (N == 1) {
			return startNum + currR * 2 + currC;
		}

		int zoneNumber = 3;
		int len = (int) Math.pow(2, N - 1);
		if (currR < len && currC < len)
			zoneNumber = 0;
		else if (currR < len && currC >= len)
			zoneNumber = 1;
		else if (currR >= len && currC < len)
			zoneNumber = 2;

		int zoneSize = len * len;
		int newStartNum = startNum + zoneNumber * zoneSize;

		switch (zoneNumber) {
		case 0:
			return find(currR, currC, N - 1, newStartNum);
		case 1:
			return find(currR, currC - len, N - 1, newStartNum);
		case 2:
			return find(currR - len, currC, N - 1, newStartNum);
		case 3:
			return find(currR - len, currC - len, N - 1, newStartNum);
		}
		return -1;
	}
}
