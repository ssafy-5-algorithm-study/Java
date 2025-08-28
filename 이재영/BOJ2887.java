import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ2887 {
    private static  class Planet{
        int x, y, z, index;

        public Planet(int x, int y, int z, int index) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.index = index;
        }

        // x 좌표 기준 비교
        public static int compareByX(Planet p1, Planet p2) {
            return Integer.compare(p1.x, p2.x);
        }
    
        // y 좌표 기준 비교  
        public static int compareByY(Planet p1, Planet p2) {
            return Integer.compare(p1.y, p2.y);
        }
    
        // z 좌표 기준 비교
        public static int compareByZ(Planet p1, Planet p2) {
            return Integer.compare(p1.z, p2.z);
        }
    }

    private static class Node implements Comparable<Node>{
        int distance, from, to;

        public Node(int from, int to, int distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.distance, o.distance);
        }

        
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        List<Planet> planetList = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            planetList.add(new Planet(x, y, z, i));
        }
        List<Node> graph = new ArrayList<>();

        // x 기준 정렬
        List<Planet> sortedByX = new ArrayList<>(planetList);
        sortedByX.sort(Planet::compareByX);
        for (int i = 0; i < N-1; i++) {
            Planet p1 = sortedByX.get(i);
            Planet p2 = sortedByX.get(i+1);
            
            int distance = Math.min(Math.abs(p1.x - p2.x),  Math.min(Math.abs(p1.y - p2.y), Math.abs(p1.z - p2.z)));
            
            graph.add(new Node(p1.index, p2.index, distance));
        }
        // y 기준 정렬
        List<Planet> sortedByY = new ArrayList<>(planetList);
        sortedByY.sort(Planet::compareByY);
        for (int i = 0; i < N-1; i++) {
            Planet p1 = sortedByY.get(i);
            Planet p2 = sortedByY.get(i+1);
            
            int distance = Math.min(Math.abs(p1.x - p2.x), Math.min(Math.abs(p1.y - p2.y), Math.abs(p1.z - p2.z)));
            
            graph.add(new Node(p1.index, p2.index, distance));
        }

        // z 기준 정렬  
        List<Planet> sortedByZ = new ArrayList<>(planetList);
        sortedByZ.sort(Planet::compareByZ);
        for (int i = 0; i < N-1; i++) {
            Planet p1 = sortedByZ.get(i);
            Planet p2 = sortedByZ.get(i+1);
            
            int distance = Math.min(Math.abs(p1.x - p2.x), Math.min(Math.abs(p1.y - p2.y), Math.abs(p1.z - p2.z)));
            
            graph.add(new Node(p1.index, p2.index, distance));
        }

        List<Node> mst = kruskal(N, graph);

        int totalWeight = 0;

        for (Node node : mst) {
            totalWeight += node.distance;
        }

        System.out.println(totalWeight);
    }

    private static class UnionFind {
        int[] parent;
        int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n+1];
            rank = new int[n+1];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        private int find(int x) {
            if(parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        private boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if(rootX == rootY) return false;

            if(rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX; 
            } else if (rank[rootY] > rank[rootX]) {
                parent[rootX]=rootY;
            } else {
                parent[rootY]=rootX;
                rank[rootX]++;
            }

            return true;
        }
    }

    private static List<Node> kruskal(int N, List<Node> graph) {
        List<Node> mst = new ArrayList<>();
        Collections.sort(graph);
        UnionFind uf = new UnionFind(N);

        for (Node node : graph) {
            if(uf.union(node.from, node.to)) {
                mst.add(node);
                if(mst.size() == N-1) break;
            }
        }

        return mst;
    }
}