import java.io.*;
import java.util.*;

public class BOJ2234 {
    static int N, M;
    static int[][] walls;
    static int[] parent;
    static int[] size;
    /*
     * 남/동/북/서
     * 1: 0001 / 2: 0010 / 3: 0011 / 4: 0100
     * 5: 0101 / 6: 0110 / 7: 0111 / 8: 1000
     * 9: 1001 / 10: 1010 / 11: 1011 / 12: 1100
     * 13: 1101 / 14: 1110 / 15: 1111
     */

static int[] dr = {0, -1, 0, 1};
static int[] dc = {-1, 0, 1, 0};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        walls = new int[M][N];
        parent = new int[M * N];
        size = new int[M * N];

        for (int i = 0; i < M * N; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                walls[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int currentId = i*N+j;

                for (int d = 0; d < 4; d++) {
                    if((walls[i][j] & (1 << d)) == 0) {
                        int ni = i+dr[d];
                        int nj = j+dc[d];

                        if(ni >= 0 && ni < M && nj >= 0 && nj < N) {
                            int nextId = ni*N+nj;
                            union(currentId, nextId);
                        }
                    }
                }
            }
        }

        Set<Integer> rooms = new HashSet<>();
        int maxRoomSize = 0;

        for (int i = 0; i < M*N; i++) {
            int root = find(i);
            rooms.add(root);
            maxRoomSize = Math.max(maxRoomSize, size[root]);
        }

        int roomCnt = rooms.size();

        int maxAfterBreak = maxRoomSize;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                int currentId = i * N + j;
                int currentRoot = find(currentId);
                
                // 각 방향의 벽을 확인
                for (int dir = 0; dir < 4; dir++) {
                    // 현재 위치에 해당 방향 벽이 있는지 확인
                    if ((walls[i][j] & (1 << dir)) != 0) {
                        int ni = i + dr[dir];
                        int nc = j + dc[dir];
                        
                        // 인접한 위치가 범위 내에 있다면
                        if (ni >= 0 && ni < M && nc >= 0 && nc < N) {
                            int nextId = ni * N + nc;
                            int nextRoot = find(nextId);
                            
                            // 다른 방이라면 합친 크기 계산
                            if (currentRoot != nextRoot) {
                                int combinedSize = size[currentRoot] + size[nextRoot];
                                maxAfterBreak = Math.max(maxAfterBreak, combinedSize);
                            }
                        }
                    }
                }
            }
        }
        
        System.out.println(roomCnt);
        System.out.println(maxRoomSize);
        System.out.println(maxAfterBreak);
    }

    static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        // Union by Size
        if (rootX != rootY) {
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
        }
    }

}