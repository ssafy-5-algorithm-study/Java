import java.util.*;
import java.io.*;


// 원숭이는 K번만 말처럼 움직이고, 나머지는 인접한 칸으로 움직일 수 있다.
public class Main {

	static int K, W, H;
	static int[][] map;
	static boolean[][][] visited;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static int[] horseX = {-1, -2, -2, -1, 1, 2, 2, 1};
	static int[] horseY = {-2, -1, 1, 2, 2, 1, -1, -2};
	
	public static void main(String[] args) throws Exception, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		K = Integer.parseInt(br.readLine());
		
		st = new StringTokenizer(br.readLine());
		
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		map = new int[H][W];
		
		for(int i=0; i<H; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<W; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); 
			}
		}
		
		int result = BFS(0,0);
		System.out.println(result);
	}
	
	public static int BFS(int x, int y) {
		Queue<Position> queue = new LinkedList<>();
		queue.offer(new Position(x, y, 0, 0));
		
		visited = new boolean[K+1][H][W];
		visited[0][x][y] = true;
		
		while(!queue.isEmpty()) {
			Position current = queue.poll();
			int currentX = current.x;
			int currentY = current.y;
			int currentDist = current.dist;
			int currentHorse = current.horse;

			if(currentX == H-1 && currentY == W-1) {
				return currentDist;
			}
			
			// 그냥 인접 이동
			for(int i=0; i<4; i++) {
				int newX = currentX + dx[i];
				int newY = currentY + dy[i];
				int newH = currentHorse;
				
				if(newX >= 0 && newY >= 0 && newX < H && newY < W) {
					if(map[newX][newY] == 0) {
						if(!visited[newH][newX][newY]) {
							visited[newH][newX][newY] = true;
							queue.offer(new Position(newX, newY, currentDist+1, newH));
						}
					}
				}
			}
			
			
			// 말로 이동
			if(currentHorse < K) {
				for(int i=0; i<8; i++) {
					int newX = currentX + horseX[i];
					int newY = currentY + horseY[i];
					int newH = currentHorse + 1;
					
					if(newX >= 0 && newY >= 0 && newX < H && newY < W) {
						if(map[newX][newY] == 0) {
							if(!visited[newH][newX][newY]) {
								visited[newH][newX][newY] = true;
								queue.offer(new Position(newX, newY, currentDist+1, newH));
							}
						}
					}
				}
			}
		}
		
		return -1;
	}
	
	public static class Position{
		int x;
		int y;
		int dist;
		int horse;
		
		Position(int x, int y, int dist, int horse) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.horse = horse;
		}
	}

}
