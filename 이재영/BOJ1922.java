import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ1922 {
    public static class Node implements Comparable<Node>{
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

    public static class UnionFind {
        static int[] parent;
        static int[] rank;
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

            if (rootX > rootY) {
                parent[rootY]=rootX;
            } else if (rootY > rootX) {
                parent[rootX]=rootY;
            } else {
                parent[rootY]=rootX;
                rank[rootX]++;
            }
            return true;
        }
    }

    static int N, M;
    public static void main(String[] args) throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        List<Node> graph = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph.add(new Node(from, to, weight));
        }

        List<Node> mst = kruskal(N, graph);

        int totalWeight = 0;

        for (Node node : mst) {
            totalWeight += node.weight;
        }

        System.out.println(totalWeight);
    }
    private static List<Node> kruskal(int n, List<Node> graph) {
        Collections.sort(graph);
        UnionFind uf = new UnionFind(n);
        List<Node> mst = new ArrayList<>();

        for(Node node : graph) {
            if (uf.union(node.from, node.to)) {
                mst.add(node);
                if(mst.size() == n - 1) break;
            }
        }

        return mst;
    }

}