import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ1600 {
    static int[] horseMoveRow = {-2, -1, 1, 2, 2, 1, -1, -2};
    static int[] horseMoveCol = {1, 2, 2, 1, -1, -2, -2, -1};
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int[][] map;
    static boolean[][][] visited;   // row, col, how(1: horse, 0: walk)
    static int K, W, H;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        K = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        visited = new boolean[H][W][K+1];

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bfs();

    }

    private static void bfs() {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0, 0, 0}); // row, col, horsemove(usage count), distance(++)
        visited[0][0][0] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();

            int curR = curr[0];
            int curC = curr[1];
            int horseMove = curr[2];
            int dist = curr[3];

            if(curR == H-1 && curC == W-1) {
                System.out.println(dist);
                return;
            }
            
            // 상하좌우(말 따라하기 X)
            for (int i = 0; i < 4; i++) {
                int nr = curR+dr[i];
                int nc = curC+dc[i];

                if(nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] != 1 && !visited[nr][nc][horseMove]) {
                    visited[nr][nc][horseMove] = true;
                    q.offer(new int[] {nr, nc, horseMove, dist+1});
                }
            }
            
            // 아직 말 따라할 기회가 남았다면...
            if (horseMove < K) {
                for (int i = 0; i < 8; i++) {
                    int nr = curR+horseMoveRow[i];
                    int nc = curC+horseMoveCol[i];

                    if(nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] != 1 && !visited[nr][nc][horseMove+1]) {
                        visited[nr][nc][horseMove+1] = true;
                        q.offer(new int[] {nr, nc, horseMove+1, dist+1});
                    }
                }
            }
        }

        System.out.println(-1);
    }

    
}
