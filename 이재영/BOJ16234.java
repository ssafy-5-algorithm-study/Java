import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ16234 {
    static int N, L, R;
    static boolean[][] visited;
    static int[][] populations;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        
        populations = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                populations[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int days = 0;
        while (true) {  // 날짜는 계속 증가
            visited = new boolean[N][N];    // 매일 확인해야되서 매번 초기화
            boolean moved = false;  // 날짜를 넘길지 결정

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(!visited[i][j]) {
                        List<int[]> ally = new ArrayList<>();   // 동맹 정보
                        int totalPopulation = dfs(i, j, ally);      // 동맹의 전체 인구수

                        if(ally.size() > 1) {
                            moved = true;
                            int newPopulation = totalPopulation / ally.size();

                            for (int[] country : ally) {
                                populations[country[0]][country[1]] = newPopulation;
                            }
                        }

                    }
                    
                }
            }

            if(!moved) {
                break;
            }

            days++;
        }
        System.out.println(days);
    }

    /**
     * @param r: 위치정보
     * @param c: 위치정보
     * @param ally: 동맹 그룹
     * @return 동맹 내 총 인구수
     */
    private static int dfs(int r, int c, List<int[]> ally) {
        visited[r][c] = true;
        ally.add(new int[]{r, c});
        int num = populations[r][c];

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c+ dc[i];

            if(nr >= 0 && nr < N && nc >= 0 && nc < N && !visited[nr][nc]) {
                int gap = Math.abs(populations[r][c] - populations[nr][nc]);    // 음수 나올 가능성 배제
                if(gap >= L && gap <= R) {
                    num += dfs(nr, nc, ally);
                }
            }
        }

        return num;
    }
}