import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ1197_Kru {
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

    static class UnionFind {
        private int[] parent, rank;
    
        public UnionFind(int n) {
            parent = new int[n+1];
            rank = new int[n+1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
            }
        }
    
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // 경로 압축
            }
            return parent[x];
        }
    
        public boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
    
            if (rootX == rootY) return false;
    
            // Union by rank
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
    }

    static int V, E;
    static List<Node> graph;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            Node newNode = new Node(from, to, weight);
            graph.add(newNode);
        }

        List<Node> mst = kruskal(V, graph);

        int totalWeight = 0;

        for(Node node : mst) {
            totalWeight += node.weight;
        }

        System.out.println(totalWeight);
    }
    // 크루스칼 -> 가중치 순서로 정렬 후 모든 노드가 연결되는 경우만 합침(유니온-파인드), 그리디
    private static List<Node> kruskal(int v, List<Node> graph) {
        List<Node> mst = new ArrayList<>();
        UnionFind uf = new UnionFind(v);
        Collections.sort(graph);

        for(Node node : graph) {
            if (uf.union(node.from, node.to)) {
                mst.add(node);
                if(mst.size() == v - 1) break;
            }
        }

        return mst;
    }
}