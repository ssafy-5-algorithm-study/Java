import java.util.*;
import java.io.*;

public class Main {
	
	static int[] bitmask;
	static int N, count;

	public static void main(String[] args) throws Exception, IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(bf.readLine());
		count = 0;
		bitmask = new int[N];
		
		for(int i=0; i<N; i++) {
			String str = bf.readLine();
			int bit = 0;
			
			for(char c : str.toCharArray()) {
				// 'the' 글자별 사용 위치를 or 연산자로 기입함
				
				// 'THE'
				// 00ZY XWVU TSRQ PONM LKJI HGFE DCBA
				// 0000 0000 1000 0000 0000 1001 0000
				bit |= 1 << (c - 'a');
			}
			
			bitmask[i] = bit;
		}
		
		subset(0,0);
		System.out.println(count);
	}
	
	
	/**
	 * 
	 * @param index : 현재 인덱스
	 * @param bit : 지금까지 선택한 단어들의 알파벳 OR 한 결과
	 */
	public static void subset(int index, int bit) {
		if(index == N) {
			// 알파벳이 26개인지 검사
			if(bit == (1 << 26) -1) { // 26개의 비트를 모두 켰다는 의미
				// (1<<26-1) => 0011 1111 1111 1111 1111 1111 1111를 의미
				count++;
			}
			return;
		}
		
		// bit | bitmask[idex] => or연산자로 사용한 알파벳 추가
		subset(index+1, bit | bitmask[index]);
		subset(index+1, bit);
	}

}
