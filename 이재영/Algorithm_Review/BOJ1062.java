import java.io.*;
import java.util.*;

public class BOJ1062 {
    static int N, K;
    static List<String> words = new ArrayList<>();
    static int maxWords = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        // K가 5보다 작으면 기본 글자(a,n,t,i,c)도 못 배우므로 0개
        if (K < 5) {
            System.out.println(0);
            return;
        }
        
        // K가 26이면 모든 글자를 배울 수 있으므로 N개
        if (K == 26) {
            System.out.println(N);
            return;
        }
        
        for (int i = 0; i < N; i++) {
            words.add(br.readLine());
        }
        
        // 기본 글자 a, n, t, i, c를 미리 설정
        int basicAlphabets = 0;
        basicAlphabets |= (1 << ('a' - 'a')); // a
        basicAlphabets |= (1 << ('n' - 'a')); // n
        basicAlphabets |= (1 << ('t' - 'a')); // t
        basicAlphabets |= (1 << ('i' - 'a')); // i
        basicAlphabets |= (1 << ('c' - 'a')); // c
        
        // 남은 K-5개의 글자를 선택하여 조합 만들기
        dfs(0, basicAlphabets, 5);
        
        System.out.println(maxWords);
    }
    
    static void dfs(int idx, int alphabets, int count) {
        if (count == K) {
            // 현재 조합으로 읽을 수 있는 단어 수 계산
            int readableWords = countReadableWords(alphabets);
            maxWords = Math.max(maxWords, readableWords);
            return;
        }
        
        for (int i = idx; i < 26; i++) {
            // 이미 선택된 글자가 아니라면
            if ((alphabets & (1 << i)) == 0) {
                dfs(i + 1, alphabets | (1 << i), count + 1);
            }
        }
    }
    
    static int countReadableWords(int alphabets) {
        int count = 0;
        
        for (String word : words) {
            if (canRead(word, alphabets)) {
                count++;
            }
        }
        
        return count;
    }
    
    static boolean canRead(String word, int alphabets) {
        for (int i = 0; i < word.length(); i++) {
            int charBit = word.charAt(i) - 'a';
            // 해당 글자를 배우지 않았다면 읽을 수 없음
            if ((alphabets & (1 << charBit)) == 0) {
                return false;
            }
        }
        return true;
    }
}