import java.io.*;
import java.util.*;

public class BOJ9997 {
    static int n;
    static int[] wordMasks;  // Set 대신 int 배열 사용
    static int answer = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        wordMasks = new int[n];
        
        // 각 단어를 비트마스크로 변환
        for (int i = 0; i < n; i++) {
            String word = br.readLine();
            int mask = 0;
            
            /*
            * 1 << n => 1 뒤에 0을 n개 추가해라
            * a => 00000000000000000000000001
            * b => 00000000000000000000000010
            * c => 00000000000000000000000100
            * ...
            * a와 b가 포함되었다면 a | b => 00000000000000000000000011
            */

            for (char c : word.toCharArray()) {
                if (c >= 'a' && c <= 'z') {
                    // 'a'는 0번 비트, 'b'는 1번 비트...
                    mask |= (1 << (c - 'a'));
                }
            }
            wordMasks[i] = mask;
        }
        
        dfs(0, 0);  // Set 대신 int 사용
        
        System.out.println(answer);
        br.close();
    }
    
    static void dfs(int index, int currentMask) {
        if (index == n) {
            // 26개 알파벳이 모두 포함되었는지 확인
            if (Integer.bitCount(currentMask) == 26) {
                answer++;
            }
            return;
        }
        
        // 현재 단어를 선택하는 경우
        dfs(index + 1, currentMask | wordMasks[index]);
        
        // 현재 단어를 선택하지 않는 경우  
        dfs(index + 1, currentMask);
    }
    
    // /**
    //  * 재귀 함수
    //  * @param index 현재 단어의 인덱스
    //  * @param currentAlphabets 현재까지 선택된 단어들의 알파벳 집합
    //  */
    // static void dfs(int index, Set<Character> currentAlphabets) {
    //     // 기저 조건: 모든 단어를 확인했을 때
    //     if (index == n) {
    //         // 알파벳 26개가 모두 포함되었는지 확인
    //         if (currentAlphabets.size() == 26) {
    //             answer++;
    //         }
    //         return;
    //     }
        
    //     // 현재 단어를 선택하는 경우
    //     // 새로운 Set을 만들어서 현재 단어의 알파벳들을 추가
    //     Set<Character> newAlphabets = new HashSet<>(currentAlphabets);
    //     newAlphabets.addAll(wordSets[index]);
    //     dfs(index + 1, newAlphabets);
    //     // 현재 단어를 선택하지 않는 경우
    //     dfs(index + 1, currentAlphabets);
    // }
}