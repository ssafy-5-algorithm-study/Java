import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ1197_Prim {
    static class Node{
        int to, weight;

        public Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static int V, E;
    static List<List<Node>> graph;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            // 무방향 그래프로 추가
            graph.get(from).add(new Node(to, weight));
            graph.get(to).add(new Node(from, weight));
        }

        int result = prim(graph, 1);

        System.out.println(result);
    }

    // Prim -> 노드를 중심으로 가중치가 가장 작은 노드를 연결
    private static int prim(List<List<Node>> graph, int start) {
        int n = graph.size();
        boolean[] visited = new boolean[n+1];
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);

        visited[start] = true;

        for (Node node : graph.get(start)) {
            pq.offer(node);
        }

        int totalWeight = 0;
        int nodeCount = 0;

        while (!pq.isEmpty() && nodeCount < n -1) {
            Node current = pq.poll();

            if(visited[current.to]) continue;

            visited[current.to] = true;
            totalWeight += current.weight;
            nodeCount++;

            for(Node node : graph.get(current.to)) {
                if(!visited[node.to]) {
                    pq.offer(node);
                }
            }
        }


        return totalWeight;
    }
    
}