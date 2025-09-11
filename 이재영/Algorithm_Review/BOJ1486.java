import java.io.*;
import java.util.*;

public class BOJ1486 {
    static int N, M, T, D;
    static int[][] map;
    static int[][] dist;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static final int INF = 987654321;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken()); 
        T = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String in = br.readLine();
            for (int j = 0; j < M; j++) {
                char c = in.charAt(j);
                if ('A' <= c && c <= 'Z') {
                    map[i][j] = c - 'A';
                } else {
                    map[i][j] = c - 'a' + 26;
                }
            }
        }

        int totalSize = N * M;
        dist = new int[totalSize][totalSize];

        initializeDistance(totalSize);
        buildGraph();
        floydWarshall(totalSize);

        int result = findMaxHeight();
        System.out.println(result);
    }

    static int getIndex(int r, int c) {
        return r * M + c;
    }

    static void initializeDistance(int totalSize) {
        for (int i = 0; i < totalSize; i++) {
            for (int j = 0; j < totalSize; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = INF;
                }
            }
        }
    }

    static void buildGraph() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                int currentIdx = getIndex(r, c);

                for (int dir = 0; dir < 4; dir++) {
                    int nr = r + dr[dir];
                    int nc = c + dc[dir];

                    if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                        int nextIdx = getIndex(nr, nc);
                        int heightDiff = map[r][c] - map[nr][nc];
                        
                        if (Math.abs(heightDiff) <= T) {
                            int weight;
                            if (heightDiff < 0) {
                                weight = heightDiff * heightDiff;
                            } else {
                                weight = 1;
                            }
                            dist[currentIdx][nextIdx] = weight;
                        }
                    }
                }
            }
        }
    }

    static void floydWarshall(int totalSize) {
        for (int k = 0; k < totalSize; k++) {
            for (int i = 0; i < totalSize; i++) {
                for (int j = 0; j < totalSize; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
    }

    static int findMaxHeight() {
        int startIdx = 0;
        int maxHeight = map[0][0];
        
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                int targetIdx = getIndex(r, c);

                int goTime = dist[startIdx][targetIdx];
                int backTime = dist[targetIdx][startIdx];

                if (goTime != INF && backTime != INF && goTime + backTime <= D) {
                    maxHeight = Math.max(maxHeight, map[r][c]);
                }
            }
        }
        
        return maxHeight;
    }
}