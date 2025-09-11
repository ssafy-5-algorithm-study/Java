import java.io.*;
import java.util.*;

public class BOJ17142 {
    private static class Pos {
        int r, c, time;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
            this.time = 0;
        }
        
        public Pos(int r, int c, int time) {
            this.r = r;
            this.c = c;
            this.time = time;
        }
    }
    
    private static int N, M, virusCnt = 0;
    private static int[][] map = new int[51][51];
    private static ArrayList<Pos> candidateList = new ArrayList<>();
    private static int minTime = Integer.MAX_VALUE;
    
    // 상하좌우 이동
    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int emptyCnt = 0; // 빈 칸의 개수
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) {
                    candidateList.add(new Pos(i, j));
                    virusCnt++;
                } else if (map[i][j] == 0) {
                    emptyCnt++;
                }
            }
        }
        
        // 빈 칸이 없다면 시간이 0
        if (emptyCnt == 0) {
            System.out.println(0);
            return;
        }
        
        combination(0, 0, new ArrayList<>(), emptyCnt);
        
        System.out.println(minTime == Integer.MAX_VALUE ? -1 : minTime);
    }
    
    // 조합으로 M개의 바이러스 선택
    private static void combination(int start, int depth, List<Pos> selected, int emptyCnt) {
        if (depth == M) {
            contamination(selected, emptyCnt);
            return;
        }
        
        for (int i = start; i < virusCnt; i++) {
            selected.add(candidateList.get(i));
            combination(i + 1, depth + 1, selected, emptyCnt);
            selected.remove(selected.size() - 1);
        }
    }
    
    // BFS로 바이러스 확산 시뮬레이션
    private static void contamination(List<Pos> activeViruses, int emptyCnt) {
        boolean[][] v = new boolean[N][N];
        Queue<Pos> q = new ArrayDeque<>();
        
        // 활성화된 바이러스들을 큐에 추가
        for (Pos virus : activeViruses) {
            q.offer(new Pos(virus.r, virus.c, 0));
            v[virus.r][virus.c] = true;
        }
        
        int infected = 0; // 감염된 빈 칸의 수
        int maxTime = 0;
        
        while (!q.isEmpty()) {
            Pos cur = q.poll();
            
            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];
                
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (v[nr][nc] || map[nr][nc] == 1) continue;
                
                v[nr][nc] = true;
                
                // 빈 칸인 경우
                if (map[nr][nc] == 0) {
                    infected++;
                    maxTime = cur.time + 1;
                    
                    // 모든 빈 칸이 감염되었다면 종료
                    if (infected == emptyCnt) {
                        minTime = Math.min(minTime, maxTime);
                        return;
                    }
                }
                
                q.offer(new Pos(nr, nc, cur.time + 1));
            }
        }
        
        // 모든 빈 칸을 감염시키지 못한 경우는 무시
    }
}