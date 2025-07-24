import java.io.*;

public class BOJ2502
{
	// 1일차, 2일차가 몇 번 불렸는지 확인
	static int day1_recursive = 0;
	static int day2_recursive = 0;
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] tokens = (br.readLine().trim()).split(" ");
		// D : 첫 날에 준 떡 갯수, K : 통장에서 뺄 횟수
		int D = Integer.parseInt(tokens[0]); int K = Integer.parseInt(tokens[1]);
		// 재귀로 구현 가능 (게시판에는 피보나치로 많이 풀었는데, 
		// 피보나치 자체가 어차피 재귀로 많이 구현하니 비슷한 결)
		recursive(D);
		// 1일차, 2일차가 몇 번 불렸는지 확인하고, 
		// 이에 맞는 해를 구한다
		int day1_count, day2_count;
		for (int i = 1;;i++)
		{
			// 맞는 해를 구했다면 이를 저장하고 반복문에서 탈출
			if ((K - (day1_recursive * i)) % day2_recursive == 0)
			{
				day1_count = i; day2_count = (K - (day1_recursive * i)) / day2_recursive;
				break;
			}
		}
		System.out.println(day1_count + "\n" + day2_count);
	}
	
	// 재귀 함수
	static int recursive(int i)
	{
		// 1일차가 불려졌을 경우
		if (i == 1)
		{
			day1_recursive++;
			return 1;
		}
		// 2일차가 불려졌을 경우
		else if (i == 2)
		{
			day2_recursive++;
			return 2;
		}
		return recursive(i - 1) + recursive(i - 2);
	}
}