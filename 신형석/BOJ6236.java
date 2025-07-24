import java.io.*;
import java.util.*;

public class BOJ6236
{
	static int arr[];
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = (br.readLine().trim()).split(" ");
		// N : 계산한 날짜, M : 통장에서 뺄 횟수
		int N = Integer.parseInt(tokens[0]); int M = Integer.parseInt(tokens[1]);
		arr = new int[N];
		// 시작, 끝 초기화
		int start = -1, end = 0;
		for (int i = 0; i < N; i++)
		{
			arr[i] = Integer.parseInt(br.readLine().trim());
			start = Math.max(start, arr[i]);
			end += arr[i];		
		}
		int answer = 0;
		while (start <= end)
		// 시작 값이 끝 값보다 크면 이분 탐색 중단
		{
			// 중간값
			int mid = (start + end) / 2;
			int find = find(mid, N);
			// find 함수에서 찾은 값이 원하는 값보다 작다면, 
			// 인출 횟수가 더 필요하다는 뜻이고, 이를 맞추기 위해선
			// 시작점 기준을 올려주어야 한다.
			if (M >= find)
			{
				answer = mid;
				end = mid - 1;
			}
			else
			{
				start = mid + 1;
			}
		}
		System.out.println(answer);
	}
	
	// 입력받은 금액 기준으로 인출하는 횟수 출력
	public static int find(int mid, int N)
	{
		int count = 1;
		int cur_money = mid;
		// 현재 돈에서 사용할 금액만큼을 빼고, 현재 돈이 얼마 남지 않았을 경우 인출받음
		for (int i = 0; i < N; i++)
		{
			cur_money -= arr[i];
			if (cur_money < 0)
			{
				count++;
				cur_money = mid - arr[i];
			}
		}
		return count;
	}
}