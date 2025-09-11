import java.io.*;
import java.util.*;

public class BOJ1941 {
    private static int[] dr = {-1, 0, 1, 0};
    private static int[] dc = {0, 1, 0, -1};
    private static char[][] map = new char[5][5];
    private static int ans = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        for (int i = 0; i < 5; i++) {
            String line = br.readLine();
            for (int j = 0; j < 5; j++) {
                map[i][j] = line.charAt(j);
            }
        }
        
        // 25개 위치 중 7개를 선택하는 조합
        combination(new ArrayList<>(), 0);
        System.out.println(ans);
    }
    
    private static void combination(List<Integer> selected, int start) {
        if (selected.size() == 7) {
            if (isValid(selected)) {
                ans++;
            }
            return;
        }
        
        for (int i = start; i < 25; i++) {
            selected.add(i);
            combination(selected, i + 1);
            selected.remove(selected.size() - 1);
        }
    }
    
    private static boolean isValid(List<Integer> selected) {
        // S가 4명 이상인지 확인
        int sCount = 0;
        for (int pos : selected) {
            int r = pos / 5;
            int c = pos % 5;
            if (map[r][c] == 'S') {
                sCount++;
            }
        }
        if (sCount < 4) return false;
        
        return getConnectedCount(selected) == 7;
    }

    private static int getConnectedCount(List<Integer> selected) {
        // 연결성 확인
        boolean[][] visited = new boolean[5][5];
        Set<Integer> selectedSet = new HashSet<>(selected);
        
        // 첫 번째 선택된 위치부터 BFS 시작
        int startPos = selected.get(0);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startPos);
        visited[startPos / 5][startPos % 5] = true;
        int connectedCount = 1;
        
        while (!queue.isEmpty()) {
            int pos = queue.poll();
            int r = pos / 5;
            int c = pos % 5;
            
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                int newPos = nr * 5 + nc;
                
                if (nr >= 0 && nr < 5 && nc >= 0 && nc < 5 && 
                    !visited[nr][nc] && selectedSet.contains(newPos)) {
                    visited[nr][nc] = true;
                    queue.offer(newPos);
                    connectedCount++;
                }
            }
        }
        return connectedCount;
    }
}