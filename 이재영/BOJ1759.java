import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1759 {
    static int L, C;
    static char[] cipher;
    static char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        cipher = new char[C];
        String[] input = br.readLine().trim().split(" ");
        for(int i = 0; i < C; i++) {
            cipher[i] = input[i].charAt(0);
        }
        
        // 오름차순 정렬
        Arrays.sort(cipher);
        
        recursive(0, 0, new char[L]);
    }
    
    private static void recursive(int index, int count, char[] result) {
        // 기저 조건: L개의 문자를 모두 선택했을 때
        if(count == L) {
            if(isValid(result)) {
                System.out.println(new String(result));
            }
            return;
        }
        
        // 남은 문자가 충분하지 않으면 종료
        if(index >= C || C - index < L - count) {
            return;
        }
        
        // 현재 문자를 선택하는 경우
        result[count] = cipher[index];
        recursive(index + 1, count + 1, result);
        
        // 현재 문자를 선택하지 않는 경우
        recursive(index + 1, count, result);
    }
    
    // 모음이 최소 1개, 자음이 최소 2개 있는지 확인
    private static boolean isValid(char[] password) {
        int vowelCount = 0;
        int consonantCount = 0;
        
        for(char c : password) {
            if(isVowel(c)) {
                vowelCount++;
            } else {
                consonantCount++;
            }
        }
        
        return vowelCount >= 1 && consonantCount >= 2;
    }
    
    private static boolean isVowel(char c) {
        for(char vowel : vowels) {
            if(c == vowel) return true;
        }
        return false;
    }
}