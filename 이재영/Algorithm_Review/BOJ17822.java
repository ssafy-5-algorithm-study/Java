import java.io.*;
import java.util.*;

public class BOJ17822 {
    private static int N, M, T;
    private static int[][] disks;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        disks = new int[N+1][M]; // 1-indexed
        
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                disks[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            
            simulate(x, d, k);
        }

        System.out.println(getSum());
    }
    
    private static void simulate(int x, int d, int k) {
        // 1. x의 배수인 원판들을 회전
        for (int i = x; i <= N; i += x) {
            rotateDisk(i, d, k);
        }
        
        // 2. 인접하면서 같은 수 찾아서 제거
        boolean removed = removeAdjacent();
        
        // 3. 제거된 수가 없으면 평균 계산해서 조정
        if (!removed) {
            adjust();
        }
    }
    
    private static void rotateDisk(int diskNum, int d, int k) {
        int[] temp = new int[M];
        
        for (int i = 0; i < M; i++) {
            if (d == 0) { // 시계방향
                temp[(i + k) % M] = disks[diskNum][i];
            } else { // 반시계방향  
                temp[(i - k + M) % M] = disks[diskNum][i];
            }
        }
        
        disks[diskNum] = temp;
    }
    
    private static boolean removeAdjacent() {
        boolean[][] toRemove = new boolean[N+1][M];
        boolean hasAdjacent = false;
        
        // 같은 원판 내에서 인접한 수 찾기
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < M; j++) {
                if (disks[i][j] == 0) continue;
                
                // 같은 원판에서 양옆 확인 (원형)
                int left = (j - 1 + M) % M;
                int right = (j + 1) % M;
                
                if (disks[i][j] == disks[i][left]) {
                    toRemove[i][j] = toRemove[i][left] = true;
                    hasAdjacent = true;
                }
                if (disks[i][j] == disks[i][right]) {
                    toRemove[i][j] = toRemove[i][right] = true;
                    hasAdjacent = true;
                }
            }
        }
        
        // 다른 원판과의 인접한 수 찾기 (같은 각도)
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (disks[i][j] == 0 || disks[i+1][j] == 0) continue;
                
                if (disks[i][j] == disks[i+1][j]) {
                    toRemove[i][j] = toRemove[i+1][j] = true;
                    hasAdjacent = true;
                }
            }
        }
        
        // 표시된 수들 제거
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < M; j++) {
                if (toRemove[i][j]) {
                    disks[i][j] = 0;
                }
            }
        }
        
        return hasAdjacent;
    }
    
    private static void adjust() {
        double avg = getAVG();
        
        if(avg == 0) return;
        
        // 평균과 비교해서 조정
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < M; j++) {
                if (disks[i][j] > 0) {
                    if (disks[i][j] > avg) {
                        disks[i][j]--;
                    } else if (disks[i][j] < avg) {
                        disks[i][j]++;
                    }
                }
            }
        }
    }
    
    private static double getAVG() {
        int sum = 0;
        int count = 0;
        
        // 평균 계산
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < M; j++) {
                if (disks[i][j] > 0) {
                    sum += disks[i][j];
                    count++;
                }
            }
        }
        
        if (count == 0) return 0;
        
        return (double) sum / count;    
    }

    private static int getSum() {
        int sum = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < M; j++) {
                sum += disks[i][j];
            }
        }
        return sum;
    }
}