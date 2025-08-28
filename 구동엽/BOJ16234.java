import java.util.*;
import java.io.*;

public class Main {
	
	static int N, L, R;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static int[][] map;
	static boolean[][] visited;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int days = 0;
		
		while(true) {
			visited = new boolean[N][N];
			boolean canMove = false;
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(!visited[i][j]) {
						if(bfs(i,j)) {
							canMove = true;
						}
					}
				}
			}
			
			if(!canMove) break;
			
			days++;
		}
		
		System.out.println(days);
	}
	
	public static boolean bfs(int x, int y) {
		Queue<Position> queue = new LinkedList<>();
		List<Position> union = new ArrayList<>();
		
		queue.offer(new Position(x,y));
		union.add(new Position(x,y));
		
		visited[x][y] = true;
		
		int sum = map[x][y];
		
		while(!queue.isEmpty()) {
			Position current = queue.poll();
			
			for(int i=0; i<4; i++) {
				int newX = current.x + dx[i];
				int newY = current.y + dy[i];
				
				if(newX >= 0 && newY >= 0 && newX < N && newY < N) {
					if(!visited[newX][newY]) {
						
						int diff = Math.abs(map[current.x][current.y] - map[newX][newY]);
						
						if(L <= diff && diff <= R) {
							visited[newX][newY] = true;
							queue.add(new Position(newX, newY));
							union.add(new Position(newX,newY));
							sum += map[newX][newY];
						}
					}
				}
			}
		}
		
		if(union.size() <= 1) return false;
		
		int movePeople = sum / union.size();
		
		for(Position current : union) {
			map[current.x][current.y] = movePeople;
		}
		
		return true;
	}

	
	public static class Position{
		int x;
		int y;
		
		Position(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
}
