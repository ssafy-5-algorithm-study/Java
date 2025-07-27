import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ2630
{
	static int count = 0;
	static int white_paper = 0;
	static int blue_paper = 0;

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine().trim());
		int[][] paper = new int[N][N];
		for (int i = 0; i < N; i++)
		{
			String[] tokens = br.readLine().trim().split(" ");
			for (int j = 0; j < N; j++)
			{
				paper[i][j] = Integer.parseInt(tokens[j]);
			}
		}
		// 분할 정복 시작
		paper_divide(paper, N, 0, 0);
		System.out.println(white_paper + "\n" + blue_paper);
		br.close();
	}

	static void paper_divide(int[][] paper, int size, int start_r, int start_c)
	{
		int current_color = paper[start_r][start_c];
		boolean isSameColor = true;
		int i;
		int j;
		for (i = start_r; i < start_r + size; i++)
		{
			// for문 내부에서 j를 초기화해주지 않았기 때문에 j를 실행하지 않는 부분이 생겼다
			for (j = start_c; j < start_c + size; j++)
			{
				// 색종이 부분 검사 후 색깔이 다른 부분이 발견되면 같은 색깔일 가능성 없음
				if (current_color != paper[i][j])
				{
					isSameColor = false;
					break;
				}
			}
		}
		// 같은 색이라면 색깔 구분해서 각 count 증가
		if (isSameColor)
		{
			if (current_color == 1)
			{
				blue_paper++;
			} else
			{
				white_paper++;
			}
			return;
			// 다른 색이라면 네 부분으로 나누어 (Divide) 다시 검사
		} else
		{
			paper_divide(paper, size / 2, start_r, start_c);
			paper_divide(paper, size / 2, start_r + size / 2, start_c);
			paper_divide(paper, size / 2, start_r, start_c + size / 2);
			paper_divide(paper, size / 2, start_r + size / 2, start_c + size / 2);
		}
	}
}