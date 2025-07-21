import java.util.*;
import java.io.*;

public class BOJ2502 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int D = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        // D일째의 피보나치 수열에서 첫째날(A)과 둘째날(B)의 계수 구하기
        int[] coefA = new int[D + 1];  // A의 계수
        int[] coefB = new int[D + 1];  // B의 계수
        
        coefA[1] = 1; coefB[1] = 0;  // 첫째날: A*1 + B*0
        coefA[2] = 0; coefB[2] = 1;  // 둘째날: A*0 + B*1
        
        // 3일차부터 계수 계산
        for (int i = 3; i <= D; i++) {
            coefA[i] = coefA[i-1] + coefA[i-2];
            coefB[i] = coefB[i-1] + coefB[i-2];
        }
        
        // A와 B 찾기: coefA[D] * A + coefB[D] * B = K
        // 여러개일경우 아무거나 한개만 출력하면 되서 1부터 시작해서 증가, K부터 시작해서 감소 상관 없음
        for (int A = 1; A <= K; A++) {
            // 정수인지 확인 후 B에 입력
        	if ((K - coefA[D] * A) % coefB[D] == 0) {
                int B = (K - coefA[D] * A) / coefB[D];
                // 제약조건 확인(1≤ A ≤ B)
                if (B >= A) {
                    System.out.println(A);
                    System.out.println(B);
                    break;
                }
            }
        }
    }
}