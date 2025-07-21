import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ6236 {
    
    // 주어진 K원으로 정확히 M번 인출하여 N일을 버틸 수 있는지 확인
    public static boolean canManage(int[] history, int days, int times, int price) {
        int currentRemain = 0;
        int count = 0;
        
        // 첫 번째 시뮬레이션: 반드시 인출해야 하는 횟수 확인
        for (int i = 0; i < days; i++) {
            if (currentRemain < history[i]) {
                if (count >= times) {  // 더 이상 인출할 수 없음
                    return false;
                }
                currentRemain = price;
                count++;
                if (currentRemain < history[i]) {  // price가 부족함
                    return false;
                }
            }
            currentRemain -= history[i];
        }
        
        // 인출 횟수가 times보다 적으면, 남은 인출을 강제로 해야 함
        if (count < times) {
            // 다시 시뮬레이션하되, 가능한 곳에서 추가 인출
            currentRemain = 0;
            count = 0;
            
            for (int i = 0; i < days; i++) {
                // 반드시 인출해야 하는 경우
                boolean mustWithdraw = currentRemain < history[i];
                
                // 추가 인출이 필요한 경우 (times번을 맞추기 위해)
                int remainingDays = days - i;
                int remainingWithdrawals = times - count;
                boolean shouldWithdraw = mustWithdraw || 
                    (remainingWithdrawals > 0 && remainingDays <= remainingWithdrawals);
                
                if (shouldWithdraw) {
                    if (count >= times) {
                        return false;
                    }
                    currentRemain = price;
                    count++;
                    if (currentRemain < history[i]) {
                        return false;
                    }
                }
                
                currentRemain -= history[i];
            }
            
            return count == times;
        }
        
        return count == times;
    }
    
    public static void main(String[] args) throws IOException {
        // Input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // N - days, M - times
        int days = Integer.parseInt(st.nextToken());
        int times = Integer.parseInt(st.nextToken());
        
        int[] history = new int[days];
        
        // find range(min, max)
        int maxValue = 0;
        int totalSum = 0;
        
        for (int i = 0; i < days; i++) {
            history[i] = Integer.parseInt(br.readLine());
            if (history[i] > maxValue) {
                maxValue = history[i];
            }
            totalSum += history[i];
        }
        
        // 이진 탐색
        int left = maxValue;    // 최소한 하루 최대 비용은 되어야 함
        int right = totalSum;   // 최대한 전체 비용만큼
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canManage(history, days, times, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        System.out.println(left);
        br.close();
    }
}