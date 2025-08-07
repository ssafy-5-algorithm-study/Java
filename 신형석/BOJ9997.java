import java.io.*;
import java.util.Arrays;

public class Main {

	static String[] dictionary;
	static int[] visitedMask;
	static int N;
	static int sum = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine().trim());
		dictionary = new String[N];
		visitedMask = new int[N	];
		for (int i = 0; i < N; i++)
		{
			dictionary[i] = br.readLine().trim();
		}
		for (int i = 0; i < N; i++)
		{
			int mask = 0;
			for (char c : dictionary[i].toCharArray())
			{
				//입력받은 단어를 전부 비트로 변환
				//시프트 연산자 : 비트를 왼쪽, 오른쪽으로 이동시키는 연산
				//왼쪽 시프트 << : 비트를 왼쪽으로 이동, 2의 거듭제곱 곱셈과 같음
				//오른쪽 시프트 >> : 비트를 왼쪽으로 이동, 2의 거듭제곱 나눗셈과 같음
				//여기서 사용하는 시프트 연산자는, 입력받은 단어의 철자를
				//비트로 변환해서 넣는 것과 같음
				/*
		         * 1 << n => 1 뒤에 0을 n개 추가해라
		         * a => 00000000000000000000000001
		         * b => 00000000000000000000000010
		         * c => 00000000000000000000000100
		         * ...
		         * a와 b가 포함되었다면 a | b => 00000000000000000000000011
		        */
				mask |= (1 << (c - 'a'));
			}
			visitedMask[i] = mask;
		}
		recursive(0, 0);
		System.out.println(sum);
	}
	
	private static void recursive(int start, int visited)
	{
		//전부 다 돌았을 경우
		if (start == N)
		{
			//bitCount : 현재 숫자에서 1이 얼마나 있는지 확인하는 함수
			//visited 숫자에 계속 or연산을 진행했으므로, 선택한 단어의 조합이 전부 1을 가지고 있는 경우를 찾으면
			//문제에서 찾는 조합이 될 것
			if (Integer.bitCount(visited) == 26)
			{
				sum++;
			}
			return;
		}
		
		//현재 가리키는 단어를 선택하는 경우, 방문한 비트마스크 숫자와 추가할 단어의 비트마스크 숫자를 or연산 진행
		recursive(start + 1, visited | visitedMask[start]);
		
		//현재 가리키는 단어를 선택하지 않는 경우, 방문한 비트마스크 숫자를 그대로 넘김
		recursive(start + 1, visited);
		
		return;
	}
}
