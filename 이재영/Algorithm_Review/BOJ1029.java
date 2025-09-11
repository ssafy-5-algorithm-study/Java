import java.io.*;
import java.util.*;

public class BOJ1029 {
    static int memo[][][], deals[][];
    static int N;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        deals = new int[N][N];

        for (int i = 0; i < N; i++) {
            String in = br.readLine();
            for (int j = 0; j < N; j++) {
                int price = in.charAt(j) - '0';
                deals[i][j] = price;
            }
        }
        
        memo = new int[N][10][1<<N]; // 현재 소유자, 현재 가격, 이전 그림 소유자들

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 10; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }

        System.out.println(dfs(0, 0, 1));// 0번이 0원의 가격으로 갖고 있으며 1명 보유함

    }

    private static int dfs(int currentOwner, int price, int prevOwners) {
        if(memo[currentOwner][price][prevOwners] != -1) {
            return memo[currentOwner][price][prevOwners];
        }

        int result = Integer.bitCount(prevOwners);

        for (int nextOwner = 0; nextOwner < N; nextOwner++) {
            if((prevOwners & (1<<nextOwner)) != 0) {
                continue;
            }
            
            int nextPrice = deals[currentOwner][nextOwner];
            if(nextPrice >= price) {
                int nextResult = dfs(nextOwner, nextPrice, prevOwners | (1 << nextOwner));
                result = Math.max(result, nextResult);
            }
        }

        memo[currentOwner][price][prevOwners] = result;
        return result;
    }
}