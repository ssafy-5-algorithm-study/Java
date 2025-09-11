import java.io.*;
import java.util.*;

public class BOJ20303 {
    private static int N,M,K;
    private static int[] candy, friend, size, dp;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        candy = new int[N+1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            candy[i] = Integer.parseInt(st.nextToken());
        }

        friend = new int[N+1];
        size = new int[N+1];
        for (int i = 1; i <= N; i++) {
            friend[i] = i;
            size[i] = 1;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            union(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        dp = new int[K]; //K명을 뺏엇을 때 최대값
        compute();

        System.out.println(dp[K-1]);
    }

    private static void compute() {
        Map<Integer, Integer> candyMap = new HashMap<>();
        Map<Integer, Integer> sizeMap = new HashMap<>();
        
        for (int i = 1; i <= N; i++) {
            int root = find(i);
            candyMap.put(root, candyMap.getOrDefault(root, 0)+candy[i]);
            sizeMap.put(root, size[root]);
        }
        
        for (int root : candyMap.keySet()) {
            int val = candyMap.get(root);
            int sz = sizeMap.get(root);
            
            // top down
            for (int k = K-1; k >= sz; k--) {
                dp[k] = Math.max(dp[k], dp[k-sz]+val);
            }
        }
    }

    private static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) return;

        if (size[rootA] < size[rootB]) {
            int temp = rootA;
            rootA = rootB;
            rootB = temp;
        }
        
        friend[rootB] = rootA;
        size[rootA] += size[rootB];
    }

    private static int find(int a) {
        return friend[a] == a ? a : (friend[a] = find(friend[a]));
    }
}