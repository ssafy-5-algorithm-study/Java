import java.io.*;
import java.util.*;

public class BOJ2842 {
    static class Node {
        int r, c;

        public Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static final int[] dr = new int[] {-1, -1, -1, 0, 0, 1, 1, 1};
    static final int[] dc = new int[] {-1, 0, 1, -1, 1, -1, 0, 1};
    static int N;
    static char[][] map;
    static int[][] diffMap;
    static int sr, sc, totalK;
    static Set<Integer> heights;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        
        sr = sc = totalK = 0;
        heights = new HashSet<>();
        
        for (int i = 0; i < N; i++) {
            String in = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = in.charAt(j);
                if (map[i][j] == 'P') {
                    sr = i;
                    sc = j;
                } else if (map[i][j] == 'K') {
                    totalK++;
                }
            }
        }

        diffMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                diffMap[i][j] = Integer.parseInt(st.nextToken());
                heights.add(diffMap[i][j]);
            }
        }

        List<Integer> heightList = new ArrayList<>(heights);
        Collections.sort(heightList);
        
        int result = Integer.MAX_VALUE;
        
        // 모든 최소 높이에 대해 시도
        for (int minHeight : heightList) {
            if (diffMap[sr][sc] < minHeight) continue;
            
            // 이분탐색으로 최대 높이 찾기
            int left = minHeight;
            int right = Collections.max(heightList);
            
            while (left <= right) {
                int maxHeight = (left + right) / 2;
                
                if (canVisitAll(minHeight, maxHeight)) {
                    result = Math.min(result, maxHeight - minHeight);
                    right = maxHeight - 1;
                } else {
                    left = maxHeight + 1;
                }
            }
        }
        
        System.out.println(result);
    }
    
    static boolean canVisitAll(int minHeight, int maxHeight) {
        if (diffMap[sr][sc] < minHeight || diffMap[sr][sc] > maxHeight) {
            return false;
        }
        
        ArrayDeque<Node> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];
        
        q.offer(new Node(sr, sc));
        visited[sr][sc] = true;
        int visitedK = 0;
        
        while (!q.isEmpty()) {
            Node current = q.poll();
            
            if (map[current.r][current.c] == 'K') {
                visitedK++;
            }
            
            for (int i = 0; i < 8; i++) {
                int nr = current.r + dr[i];
                int nc = current.c + dc[i];
                
                if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc]) {
                    int height = diffMap[nr][nc];
                    if (height >= minHeight && height <= maxHeight) {
                        visited[nr][nc] = true;
                        q.offer(new Node(nr, nc));
                    }
                }
            }
        }
        
        return visitedK == totalK;
    }
}