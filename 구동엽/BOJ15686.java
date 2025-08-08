package algorithm;

import java.util.*;
import java.io.*;

public class 치킨배달 {
	
	static int N, M;
	static int[][] map;
	static boolean[][] visited;
	static Position[] home;
	static Position[] chicken;
	static Position[] sel;
	static int chickenIndex;
	static int homeIndex;
	static int minDis;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(bf.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N+1][N+1];
		home = new Position[2*N];
		visited = new boolean[N+1][N+1];
		chicken = new Position[13];
		minDis = Integer.MAX_VALUE;
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(bf.readLine());
			for(int j=1; j<=N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 2) {
					chicken[chickenIndex++] = new Position(i,j);
				}else if(map[i][j] == 1) {
					home[homeIndex++] = new Position(i,j); 
				}
			}
		}
		
		// 치킨집 1개 ~ M개까지 살리기 (조합 만들기)
		for(int i=1; i<=M; i++) {
			sel = new Position[13];
			backTracking(0, 0, i);
		}
		
		System.out.println(minDis);
	}
	
	public static void backTracking(int depth, int index, int maxDepth) {
	    if (depth == maxDepth) {
	    	minDis = Math.min(calc(), minDis);
	        return;
	    }

	    for (int i = index; i < chickenIndex; i++) {
	        sel[depth] = chicken[i];
	        backTracking(depth + 1, i + 1, maxDepth);
	    }
	}
	
	public static int calc() {
		int sum = 0;
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				// 집 찾았고
				if(map[i][j] == 1) {
					int min = Integer.MAX_VALUE;
					int dis = 0;
					//치킨집들이랑 최소 거리 구하기
					for(int k=0; k<sel.length; k++) {
						if(sel[k] != null) {
							dis = Math.abs(i - sel[k].x) + Math.abs(j - sel[k].y);
						}
						
						min = Math.min(min, dis);
					}
					
					sum += min;
				}
			}
		}
		
		return sum;
	}
	
	
	public static class Position {
		int x;
		int y;
		
		Position(int x, int y){
			this.x = x;
			this.y= y;
		}
	}

}
