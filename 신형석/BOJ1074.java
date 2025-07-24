import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ1074
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] tokens = br.readLine().trim().split(" ");
		int N = Integer.parseInt(tokens[0]);
		int r = Integer.parseInt(tokens[1]);
		int c = Integer.parseInt(tokens[2]);
		// 분할 시작
		int answer = visit_Z(0, N, r, c);
		System.out.println(answer);
	}

	static int visit_Z(int answer, int N, int r, int c)
	{
		if (N == 0) // N == 0일때 답 출력
		{
			return answer;
		}
		// 백준에서 참고한 게시물 :
		// https://www.acmicpc.net/board/view/153237
		// 사분면에 대한 설명이 있는데, 게시글에 틀린 부분이 있다. Z 탐색을 하기 위해서
		// 필요한 조건은 게시글에 적힌 사분면 조건과 조금 틀림
		// r과 c가 속해있는 사분면 구하기
		int num;
		if (r < (int) Math.pow(2, N - 1) && c < (int) Math.pow(2, N - 1))
		{
			num = 1;
		} else if (r < (int) Math.pow(2, N - 1) && c >= (int) Math.pow(2, N - 1))
		{
			num = 2;
		} else if (r >= (int) Math.pow(2, N - 1) && c < (int) Math.pow(2, N - 1))
		{
			num = 3;
		} else if (r >= (int) Math.pow(2, N - 1) && c >= (int) Math.pow(2, N - 1))
		{
			num = 4;
		} else
		{
			num = 1;
		}
		// 누적된 정답에 현재 사분면 가장 왼쪽 위 숫자를 누적
		answer = answer + ((int) Math.pow(2, 2 * (N - 1)) * (num - 1));
		// 절대적인 위치는 사용 불가하므로, 스케일링을 통해 상대적인 위치를 파악
		// r과 c가 2 ^ (N - 1)보다 클 경우, 스케일링 진행
		return visit_Z(answer, N - 1, r >= (int) Math.pow(2, N - 1) ? r - (int) Math.pow(2, N - 1) : r,
				c >= (int) Math.pow(2, N - 1) ? c - (int) Math.pow(2, N - 1) : c);

	}
}