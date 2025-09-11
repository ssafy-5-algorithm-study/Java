import java.io.*;
import java.util.*;

public class BOJ15684 {
    private static int N, M, H;
    private static int[][] ladder;
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        ladder = new int[H+1][N+1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int h = Integer.parseInt(st.nextToken());
            int pos = Integer.parseInt(st.nextToken());
            ladder[h][pos] = 1;
        }

        if(checkLadder()) {
            System.out.println(0);
            return;
        }

        for (int add = 1; add <= 3; add++) {
            if(build(0, add, 1, 1)) {
                System.out.println(add);
                return;
            }
        }

        System.out.println(-1);
    }

    private static boolean build(int cnt, int addCnt, int startH, int startPos) {
        if(cnt == addCnt) {
            return checkLadder();
        }

        for (int h = startH; h <= H; h++) {
            int pos = (h == startH) ? startPos : 1;

            while (pos < N) {
                // 현재위치 && 왼쪽 && 오른쪽
                if(ladder[h][pos] == 0 && ladder[h][pos-1] == 0 && ladder[h][pos+1] == 0) {
                    // 지어!
                    ladder[h][pos] = 1;

                    // 내려가!
                    if(build(cnt+1, addCnt, h, pos+2)) {
                        return true;
                    }

                    // 안되네...
                    ladder[h][pos] = 0;
                }

                pos++;
            }
        }
        return false;
    }

    private static boolean checkLadder() {
        for (int start = 1; start <= N; start++) {
            int pos = start;
            for (int h = 1; h <= H; h++) {
                // 왼쪽
                if(pos > 1 && ladder[h][pos-1] == 1) {
                    pos--;
                }
                // 오른쪽 
                else if (pos < N && ladder[h][pos] == 1) {
                    pos++;
                }
            }

            if(pos != start) {
                return false;
            }
        }
        return true;
    }
}
