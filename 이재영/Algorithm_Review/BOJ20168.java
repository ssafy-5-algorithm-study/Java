import java.io.*;
import java.util.*;

public class BOJ20168 {
    private static class Edge {
        int to, cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    private static int N, M, A, B, C;
    private static List<List<Edge>> graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        int maxCost = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(u).add(new Edge(v, cost));
            graph.get(v).add(new Edge(u, cost));
            maxCost = Math.max(maxCost, cost);
        }

        // 이분탐색으로 최대 간선 비용의 최솟값 찾기
        int left = 1, right = maxCost;
        int answer = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            
            if (canReach(mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        System.out.println(answer);
    }

    private static boolean canReach(int maxEdgeCost) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        
        dist[A] = 0;
        pq.offer(new Edge(A, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int node = current.to;
            int cost = current.cost;

            if (cost > dist[node]) continue;
            if (node == B) return cost <= C;

            for (Edge next : graph.get(node)) {
                if (next.cost > maxEdgeCost) continue;
                
                int newCost = cost + next.cost;
                if (newCost <= C && newCost < dist[next.to]) {
                    dist[next.to] = newCost;
                    pq.offer(new Edge(next.to, newCost));
                }
            }
        }

        return dist[B] <= C;
    }
}