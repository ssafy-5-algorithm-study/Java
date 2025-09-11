import java.io.*;
import java.util.*;

public class BOJ1039 {
    private static int K;
    private static int maxResult = -1;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        String N = st.nextToken();
        K = Integer.parseInt(st.nextToken());
        
        // BFS를 사용하여 K번의 교환 수행
        Set<String> currentLevel = new HashSet<>();
        currentLevel.add(N);
        
        for (int depth = 0; depth < K; depth++) {
            Set<String> nextLevel = new HashSet<>();
            
            for (String num : currentLevel) {
                // 가능한 모든 교환 시도
                for (int i = 0; i < num.length() - 1; i++) {
                    for (int j = i + 1; j < num.length(); j++) {
                        String swapped = swap(num, i, j);
                        
                        // 0으로 시작하면 안됨
                        if (swapped.charAt(0) != '0') {
                            nextLevel.add(swapped);
                        }
                    }
                }
            }
            
            currentLevel = nextLevel;
            
            // 더 이상 교환할 수 없는 경우 (모든 숫자가 0으로 시작)
            if (currentLevel.isEmpty()) {
                maxResult = -1;
                break;
            }
        }
        
        // K번 교환 후 최댓값 찾기
        if (!currentLevel.isEmpty()) {
            for (String num : currentLevel) {
                maxResult = Math.max(maxResult, Integer.parseInt(num));
            }
        }
        
        System.out.println(maxResult);
    }
    
    private static String swap(String str, int left, int right) {
        char[] arr = str.toCharArray();
        char temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        return new String(arr);
    }
}