import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ6497 {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        while(true) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken()); // 집의 수
            int n = Integer.parseInt(st.nextToken()); // 길의 수
            
            if(m == 0 && n == 0) break;
            
            List<Node> graph = new ArrayList<>();
            int totalWeight = 0;
            
            for (int i = 0; i < n; i++) {  // 정확히 n개의 간선
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());
                
                graph.add(new Node(x, y, z));
                totalWeight += z;
            }
            
            int mstWeight = kruskal(m, graph);  // m: 정점 수
            System.out.println(totalWeight - mstWeight);
        }

    }

    static class Node implements Comparable<Node>{
        int from, to, weight;

        public Node(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    private static class UnionFind {
        int[] parent;
        int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n+1];
            rank = new int[n+1];

            for (int i = 0; i <= n; i++) {
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

            if(rank[rootX]>rank[rootY]) {
                parent[rootY]=rootX;
            } else if (rank[rootY]>rank[rootX]) {
                parent[rootX]=rootY;
            } else {
                parent[rootY]=rootX;
                rank[rootX]++;
            }

            return true;
        }
        
    }

    private static int kruskal(int N, List<Node> graph) {
        List<Node> mst = new ArrayList<>();
        Collections.sort(graph);
        UnionFind uf = new UnionFind(N);

        for (Node node : graph) {
            if(uf.union(node.from, node.to)) {
                mst.add(node);

                if(mst.size() == N-1) break;
            }
        }

        int totalWeight = 0;

        for (Node node : mst) {
            totalWeight += node.weight;
        }

        return totalWeight;
    }
}