import java.io.*;
import java.util.*;

public class BOJ1043 {
    @SuppressWarnings("unused")
    private static int N, M;
    private static Set<Integer> truth = new HashSet<>();
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int known = Integer.parseInt(st.nextToken());

        for (int i = 0; i < known; i++) {
            truth.add(Integer.parseInt(st.nextToken()));
        }

        Set<Integer>[] partySet = new Set[M];
        for (int i = 0; i < M; i++) {
            partySet[i] = new HashSet<>();
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());

            for (int j = 0; j < n; j++) {
                partySet[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        // 동시 처리
        boolean changed = true;
        while (changed) {
            changed = false;
            int beforeSize = truth.size();
            
            for (int i = 0; i < M; i++) {
                Set<Integer> party = partySet[i];
                
                // 현재 파티에 진실을 아는 사람이 있는지 확인
                boolean hasTruthKnower = false;
                for (Integer person : truth) {
                    if (party.contains(person)) {
                        hasTruthKnower = true;
                        break;
                    }
                }
                
                // 진실을 아는 사람이 있다면 파티의 모든 사람이 진실을 알게 됨
                if (hasTruthKnower) {
                    truth.addAll(party);
                }
            }
            
            // 진실을 아는 사람이 추가되었다면 다시 반복
            if (truth.size() > beforeSize) {
                changed = true;
            }
        }

        // 거짓말할 수 있는 파티 개수 세기
        int ans = 0;
        for (int i = 0; i < M; i++) {
            Set<Integer> party = partySet[i];
            boolean canLie = true;
            
            for (Integer person : party) {
                if (truth.contains(person)) {
                    canLie = false;
                    break;
                }
            }
            
            if (canLie) {
                ans++;
            }
        }

        System.out.println(ans);
    }
}