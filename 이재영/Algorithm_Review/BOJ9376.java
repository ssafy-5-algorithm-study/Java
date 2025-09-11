import java.io.*;
import java.util.*;

public class BOJ9376 {
    static class Info implements Comparable<Info> {
        int r, c, d;
        
        public Info(int r, int c, int d) {
            this.r = r;
            this.c = c; 
            this.d = d;
        }
        
        @Override
        public int compareTo(Info other) {
            return Integer.compare(this.d, other.d);
        }
    }
    
    private static final int INF = 0x3f3f3f3f;
    private static int R, C;
    private static char[][] arr;
    private static int[][][] dist;
    private static int[] dr = {0, 0, 1, -1};
    private static int[] dc = {1, -1, 0, 0};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        
        int T = Integer.parseInt(br.readLine());
        
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            
            arr = new char[R][C];
            dist = new int[3][R][C];
            List<int[]> prisoners = new ArrayList<>();
            
            for (int i = 0; i < R; i++) {
                String line = br.readLine();
                for (int j = 0; j < C; j++) {
                    arr[i][j] = line.charAt(j);
                    if (arr[i][j] == '$') {
                        prisoners.add(new int[]{i, j});
                    }
                }
            }
            
            // 거리 배열 초기화
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < R; i++) {
                    Arrays.fill(dist[k][i], INF);
                }
            }
            
            // 각 죄수에 대해 다익스트라 실행
            for (int i = 0; i < 2; i++) {
                int[] prisoner = prisoners.get(i);
                dist[i][prisoner[0]][prisoner[1]] = 0;
                relax(dist[i]);
            }
            
            // 두 죄수의 거리를 합치고, 문인 경우 중복 제거
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (dist[0][i][j] != INF && dist[1][i][j] != INF) {
                        dist[2][i][j] = dist[0][i][j] + dist[1][i][j];
                        if (arr[i][j] == '#') {
                            dist[2][i][j]--;  // 문인 경우 중복 제거
                        }
                    }
                }
            }
            
            // 합친 거리 배열에 대해 다시 다익스트라 실행
            relax(dist[2]);
            
            // 각 배열에 대해 경계에서의 최솟값 구하기
            int[] ans = {INF, INF, INF};
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < R; i++) {
                    for (int j = 0; j < C; j++) {
                        // 경계 체크
                        if (i == 0 || i == R-1 || j == 0 || j == C-1) {
                            ans[k] = Math.min(ans[k], dist[k][i][j]);
                        }
                    }
                }
            }
            
            // 독립 탈출 vs 합쳐진 경로 탈출 중 최솟값
            sb.append(Math.min(ans[0] + ans[1], ans[2])).append('\n');
        }
        
        System.out.print(sb);
    }
    
    private static void relax(int[][] d) {
        PriorityQueue<Info> pq = new PriorityQueue<>();
        
        // 초기 상태를 큐에 추가
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (d[i][j] != INF) {
                    pq.offer(new Info(i, j, d[i][j]));
                }
            }
        }
        
        while (!pq.isEmpty()) {
            Info current = pq.poll();
            
            // 이미 더 짧은 경로로 방문된 경우 스킵
            if (d[current.r][current.c] < current.d) continue;
            
            for (int t = 0; t < 4; t++) {
                int nr = current.r + dr[t];
                int nc = current.c + dc[t];
                
                if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                
                char cell = arr[nr][nc];
                if (cell == '*') continue;  // 벽은 통과 불가
                
                int newDist;
                if (cell == '.' || cell == '$') {
                    newDist = current.d;  // 빈 공간이나 죄수 위치는 비용 0
                } else { // cell == '#'
                    newDist = current.d + 1;  // 문은 비용 1
                }
                
                if (newDist < d[nr][nc]) {
                    d[nr][nc] = newDist;
                    pq.offer(new Info(nr, nc, newDist));
                }
            }
        }
    }
}