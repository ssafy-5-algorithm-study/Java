import java.io.*;
import java.util.*;

public class BOJ13911 {
    private static class Node implements Comparable<Node>{
        int to, w;

        public Node(int to, int w) {
            this.to = to;
            this.w = w;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.w, o.w);
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            graph.get(u).add(new Node(v, w));
            graph.get(v).add(new Node(u, w));
        }

        // 맥도날드 정보
        st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        List<Integer> macdonalds = new ArrayList<>();
        Set<Integer> macSet = new HashSet<>();
        for (int i = 0; i < M; i++) {
            int mac = Integer.parseInt(st.nextToken());
            macdonalds.add(mac);
            macSet.add(mac);
        }
        
        // 스타벅스 정보
        st = new StringTokenizer(br.readLine());
        int S = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        List<Integer> starbucks = new ArrayList<>();
        Set<Integer> starSet = new HashSet<>();
        for (int i = 0; i < S; i++) {
            int star = Integer.parseInt(st.nextToken());
            starbucks.add(star);
            starSet.add(star);
        }

        // 멀티소스 다익스트라 실행
        int[] macDist = multiSourceDijkstra(V, macdonalds, graph);
        int[] starDist = multiSourceDijkstra(V, starbucks, graph);

        // 최적 위치 찾기
        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= V; i++) {
            // 맥도날드나 스타벅스가 아닌 위치만 고려
            if (!macSet.contains(i) && !starSet.contains(i)) {
                // 거리 제한 조건 확인
                if (macDist[i] <= x && starDist[i] <= y) {
                    result = Math.min(result, macDist[i] + starDist[i]);
                }
            }
        }

        System.out.println(result == Integer.MAX_VALUE ? -1 : result);
    }

    /**
     * 멀티소스 다익스트라 알고리즘
     * @param V 정점 수
     * @param sources 시작점들 (모든 맥도날드 또는 모든 스타벅스)
     * @param graph 그래프
     * @return 각 정점에서 가장 가까운 소스까지의 거리
     */
    private static int[] multiSourceDijkstra(int V, List<Integer> sources, List<List<Node>> graph) {
        int[] dist = new int[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        // 모든 소스를 거리 0으로 설정하고 우선순위 큐에 추가
        for (int source : sources) {
            dist[source] = 0;
            pq.offer(new Node(source, 0));
        }
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.to;
            int currentDist = current.w;
            
            // 이미 처리된 정점은 건너뛰기
            if (currentDist > dist[u]) continue;
            
            // 인접 정점들 확인
            for (Node edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.w;
                int newDist = dist[u] + weight;
                
                // 더 짧은 경로 발견 시 업데이트
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.offer(new Node(v, newDist));
                }
            }
        }
        
        return dist;
    }
}