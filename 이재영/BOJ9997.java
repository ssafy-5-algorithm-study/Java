import java.io.*;
import java.util.*;

public class BOJ9997 {
    static int n;
    static int[] wordMasks;  // Set ��� int �迭 ���
    static int answer = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        wordMasks = new int[n];
        
        // �� �ܾ ��Ʈ����ũ�� ��ȯ
        for (int i = 0; i < n; i++) {
            String word = br.readLine();
            int mask = 0;
            
            /*
            * 1 << n => 1 �ڿ� 0�� n�� �߰��ض�
            * a => 00000000000000000000000001
            * b => 00000000000000000000000010
            * c => 00000000000000000000000100
            * ...
            * a�� b�� ���ԵǾ��ٸ� a | b => 00000000000000000000000011
            */

            for (char c : word.toCharArray()) {
                if (c >= 'a' && c <= 'z') {
                    // 'a'�� 0�� ��Ʈ, 'b'�� 1�� ��Ʈ...
                    mask |= (1 << (c - 'a'));
                }
            }
            wordMasks[i] = mask;
        }
        
        dfs(0, 0);  // Set ��� int ���
        
        System.out.println(answer);
        br.close();
    }
    
    static void dfs(int index, int currentMask) {
        if (index == n) {
            // 26�� ���ĺ��� ��� ���ԵǾ����� Ȯ��
            if (Integer.bitCount(currentMask) == 26) {
                answer++;
            }
            return;
        }
        
        // ���� �ܾ �����ϴ� ���
        dfs(index + 1, currentMask | wordMasks[index]);
        
        // ���� �ܾ �������� �ʴ� ���  
        dfs(index + 1, currentMask);
    }
    
    // /**
    //  * ��� �Լ�
    //  * @param index ���� �ܾ��� �ε���
    //  * @param currentAlphabets ������� ���õ� �ܾ���� ���ĺ� ����
    //  */
    // static void dfs(int index, Set<Character> currentAlphabets) {
    //     // ���� ����: ��� �ܾ Ȯ������ ��
    //     if (index == n) {
    //         // ���ĺ� 26���� ��� ���ԵǾ����� Ȯ��
    //         if (currentAlphabets.size() == 26) {
    //             answer++;
    //         }
    //         return;
    //     }
        
    //     // ���� �ܾ �����ϴ� ���
    //     // ���ο� Set�� ���� ���� �ܾ��� ���ĺ����� �߰�
    //     Set<Character> newAlphabets = new HashSet<>(currentAlphabets);
    //     newAlphabets.addAll(wordSets[index]);
    //     dfs(index + 1, newAlphabets);
    //     // ���� �ܾ �������� �ʴ� ���
    //     dfs(index + 1, currentAlphabets);
    // }
}