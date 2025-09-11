import java.io.*;
import java.util.*;

public class BOJ16920 {
    private static class Pair {
        int r, c;
        public Pair(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    
    private static int N, M, P, emptyLand;
    private static int[] dr = {-1, 0, 1, 0};
    private static int[] dc = {0, 1, 0, -1};
    private static char[][] map;
    private static Queue<Pair>[] playerQueues; // 각 플레이어의 경계 저장
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        
        int[] move = new int[P + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= P; i++) {
            move[i] = Integer.parseInt(st.nextToken());
        }
        
        map = new char[N][M];
        playerQueues = new Queue[P + 1];
        for (int i = 1; i <= P; i++) {
            playerQueues[i] = new ArrayDeque<>();
        }
        
        emptyLand = 0;

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
                
                if (map[i][j] == '.') {
                    emptyLand++;
                } else if (map[i][j] >= '1' && map[i][j] <= '9') {
                    int player = map[i][j] - '0';
                    // 초기에는 모든 플레이어 위치가 경계
                    playerQueues[player].offer(new Pair(i, j));
                }
            }
        }
        
        // 게임 시뮬레이션
        while (emptyLand > 0) {
            boolean anyExpansion = false;
            
            for (int p = 1; p <= P; p++) {
                if (!playerQueues[p].isEmpty()) {
                    boolean expanded = expandPlayer(p, move[p]);
                    if (expanded) anyExpansion = true;
                }
                
                if (emptyLand == 0) break;
            }
            
            // 모든 플레이어가 확장하지 못했다면 종료
            if (!anyExpansion) break;
        }
        
        // 결과 계산
        int[] lands = new int[P + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] >= '1' && map[i][j] <= '9') {
                    lands[map[i][j] - '0']++;
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= P; i++) {
            sb.append(lands[i]).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
    
    /**
     * 특정 플레이어를 power만큼 확장
     * @param player 플레이어 번호
     * @param power 확장 횟수
     * @return 확장했는지 여부
     */
    private static boolean expandPlayer(int player, int power) {
        boolean expanded = false;
        Queue<Pair> currentQueue = playerQueues[player];
        
        for (int step = 0; step < power && !currentQueue.isEmpty(); step++) {
            int size = currentQueue.size();
            
            for (int i = 0; i < size; i++) {
                Pair current = currentQueue.poll();
                
                for (int dir = 0; dir < 4; dir++) {
                    int nr = current.r + dr[dir];
                    int nc = current.c + dc[dir];
                    
                    if (isValid(nr, nc) && map[nr][nc] == '.') {
                        map[nr][nc] = (char)('0' + player);
                        currentQueue.offer(new Pair(nr, nc)); // 새로운 경계 추가
                        emptyLand--;
                        expanded = true;
                    }
                }
            }
        }
        
        return expanded;
    }
    
    private static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }
}