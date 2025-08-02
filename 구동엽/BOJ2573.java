import java.util.*;
import java.io.*;

public class Main {
	static int[][] map;
	static boolean[][] visited;
	static int N, M;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		
		N = Integer.parseInt(stringTokenizer.nextToken());
		M = Integer.parseInt(stringTokenizer.nextToken());
		
		map = new int[N][M];
		
		for(int i=0; i<N; i++) {
			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = Integer.parseInt(stringTokenizer.nextToken());
			}
		}
		
		int year = 0;
		
		while(true) {
			int count = 0;
			year++;
			visited = new boolean[N][M];
			
			//빙산 녹이기
			meltIce();
			
			//분리 확인 
			for(int i=0; i<N; i++) {
				for(int j=0; j<M; j++) {
					if(!visited[i][j] && map[i][j] != 0) {
						count++;
						isSeparation(i,j);
					}
				}
			}
			
			if(count == 0) {
				System.out.println(0);
				return;
			}
			
			if(count >= 2) {
				System.out.println(year);
				return;
			}
			
		}
	}
	
	// 빙산을 녹이는 메서드
	public static void meltIce() {
		Queue<Position> queue = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j] != 0) {
					queue.offer(new Position(i, j));
				}
			}
		}
		
		while(!queue.isEmpty()) {
			Position current = queue.poll();
			int iceCount = 0;
			visited[current.x][current.y] = true;
			
			for(int i=0; i<4; i++) {
				int newX = current.x + dx[i];
				int newY = current.y + dy[i];
				
				if(newX >= 0 && newY >= 0 && newX < N && newY < M) {
					if(!visited[newX][newY] && map[newX][newY] == 0) {
						iceCount++;
					}
				}
			}
			
			if(map[current.x][current.y] - iceCount < 0) {
				map[current.x][current.y] = 0;
			}else {
				map[current.x][current.y] -= iceCount;
			}
		}
	}

  //분리 되었는지?
	public static void isSeparation(int x, int y) {
		if(!visited[x][y]) {
			visited[x][y] = true;
		}
		
		for(int i=0; i<4; i++) {
			int newX = x + dx[i];
			int newY = y + dy[i];
			
			if(newX >= 0 && newY >= 0 && newX < N && newY < M) {
				if(!visited[newX][newY] && map[newX][newY] != 0) {
					isSeparation(newX, newY);
				}
			}
		}
		
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
