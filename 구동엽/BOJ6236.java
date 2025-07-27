import java.util.*;
import java.io.*;

public class BOJ6236 {

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		
		int N = Integer.parseInt(stringTokenizer.nextToken());
		int M = Integer.parseInt(stringTokenizer.nextToken());
		
		int[] array = new int[N];
		
		int right = 0; // right(1901)
		int left = 0; // left(500)
		int result = 0;
		
		for(int i=0; i<N; i++) {
			array[i] = Integer.parseInt(bufferedReader.readLine());
			right += array[i];
			left = Math.max(left, array[i]);
		}
		
		while(left <= right) {
			int mid = (left + right) / 2; //1200
			
			
			if(M >= getCount(array, mid)) {
				result = mid;
				right = mid - 1;
			}else {
				left = mid + 1;
			}
		}
		
		System.out.println(result);
	}
	
	public static int getCount(int[] array, int money) {
		int count = 1;
		int current = money;
		
		for(int num : array) {
			current -= num;
			
			if(current < 0) {
				++count;
				current = money - num;
			}
		}
		
		return count;
	}

}
