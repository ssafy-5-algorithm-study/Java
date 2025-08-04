import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ16974 {
    static int N;
    static long X;
    static long[] pattyCount;
    static long[] totalSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        X = Long.parseLong(st.nextToken());

        pattyCount = new long[N+1];
        totalSize = new long[N+1];

        

        compute(N);

        long result = solve(N, X);
        System.out.println(result);
        // TLE
        // recursive(N, sb);
        // String eaten = sb.substring(0, X);

        // int result = 0;
        // for(char ch : eaten.toCharArray()) {
        //     if(ch == 'P') {
        //         result++;
        //     }
        // }


    }

    private static void compute(int level) {
        pattyCount[0] = 1;
        totalSize[0] = 1;

        for(int i = 1; i <= N; i++) {
            // level n = "B(level n-1)P(levle n-1)B"
            pattyCount[i] = 1+ pattyCount[i-1]*2;
            totalSize[i] = 3 + totalSize[i-1]*2;
        }
    }

    /**
     * 레벨-level 버거에서 처음 x개를 먹었을 때 패티 개수
     */
    private static long solve(int level, long x) {
        // 기저 조건: 레벨-0은 패티
        if (level == 0) {
            return 1;
        }
        
        if (x <= 0) {
            return 0;
        }

        // 레벨-level 구조: B + 레벨-(level-1) + P + 레벨-(level-1) + B
        long leftBun = 1;                        // 첫 번째 번
        long leftBurger = totalSize[level - 1];  // 왼쪽 하위 버거
        long middlePatty = 1;                    // 가운데 패티
        long rightBurger = totalSize[level - 1]; // 오른쪽 하위 버거
        // long rightBun = 1;                    // 마지막 번

        // 구간별 처리
        if (x <= leftBun) {
            // 첫 번째 번만 먹음 → 패티 0개
            return 0;
        }
        else if (x <= leftBun + leftBurger) {
            // 첫 번째 번 + 왼쪽 버거 일부
            return solve(level - 1, x - leftBun);
        }
        else if (x <= leftBun + leftBurger + middlePatty) {
            // 첫 번째 번 + 왼쪽 버거 전체 + 가운데 패티
            return pattyCount[level - 1] + 1;
        }
        else if (x <= leftBun + leftBurger + middlePatty + rightBurger) {
            // 첫 번째 번 + 왼쪽 버거 + 가운데 패티 + 오른쪽 버거 일부
            return pattyCount[level - 1] + 1 + solve(level - 1, x - leftBun - leftBurger - middlePatty);
        }
        else {
            // 전체 다 먹음
            return pattyCount[level];
        }
    }

    // TLE
    // private static void recursive(int level, StringBuilder sb) {
    //     // 기저조건: level = 0 이면 패티 추가
    //     if(level == 0) {
    //         sb.append("P");
    //         return;
    //     }

    //     // level n = "B(level n-1)P(levle n-1)B"
    //     sb.append("B");
    //     recursive(level-1, sb);
    //     sb.append("P");
    //     recursive(level-1, sb);
    //     sb.append("B");
    // }
}
