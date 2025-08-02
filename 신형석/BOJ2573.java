import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

class Point
{
	int x;
	int y;

	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}

public class Main
{
	static int[][] ocean;

	static int[] mr =
	{ -1, 0, 1, 0 };
	static int[] mc =
	{ 0, 1, 0, -1 };

	static Queue<Point> queue = new LinkedList<Point>();

	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = br.readLine().trim().split(" ");

		int N = Integer.parseInt(tokens[0]);
		int M = Integer.parseInt(tokens[1]);
		ocean = new int[N][M];
		for (int i = 0; i < N; i++)
		{
			tokens = br.readLine().trim().split(" ");
			for (int j = 0; j < M; j++)
			{
				ocean[i][j] = Integer.parseInt(tokens[j]);
			}
		}
		int[][] zero_count;
		boolean[][] visited;
		int year = 0;
		for (;;)
		{
			year++;
			zero_count = new int[N][M];
			visited = new boolean[N][M];
			// 사방탐색을 이용, 녹을 빙산의 양 계산
			for (int i = 0; i < N; i++)
			{
				for (int j = 0; j < M; j++)
				{
					if (ocean[i][j] <= 0)
					{
						continue;
					} else
					{
						for (int idx = 0; idx < 4; idx++)
						{
							int r = i + mr[idx];
							int c = j + mc[idx];
							if (ocean[r][c] <= 0)
							{
								zero_count[i][j]++;
							}
						}
					}
				}
			}

			// 빙산이 녹은 후 상태
			for (int i = 0; i < N; i++)
			{
				for (int j = 0; j < M; j++)
				{
					ocean[i][j] -= zero_count[i][j];
				}
			}

			// 빙산이 녹은 후, BFS를 이용하여 인접 빙하 확인
			int count = 0;
			for (int i = 0; i < N; i++)
			{
				for (int j = 0; j < M; j++)
				{
					if (ocean[i][j] > 0 && !visited[i][j])
					{
						count++;
						BFS(ocean, visited, i, j);
					}
				}
			}
			// 빙하가 2개 이상 있는 경우
			if (count > 1)
			{
				break;
			} else if (count == 0) // 빙하가 하나도 없는 경우
			{
				System.out.println(0);
				return;
			}
		}
		System.out.println(year);
	}

	// 큐를 이용한 BFS. 사방탐색을 통해 근처 노드를 전부 찾은 후 큐에 추가, 방문 표시
	static void BFS(int[][] ocean, boolean[][] visited, int i, int j)
	{
		queue.add(new Point(i, j));
		visited[i][j] = true;
		while (!queue.isEmpty())
		{
			Point pivot = queue.poll();
			for (int idx = 0; idx < 4; idx++)
			{
				int r = pivot.x + mr[idx];
				int c = pivot.y + mc[idx];
				if (ocean[r][c] > 0 && !visited[r][c])
				{
					queue.add(new Point(r, c));
					visited[r][c] = true;
				}
			}
		}
	}
}
