

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj1107 {
    static boolean[] broken = new boolean[10];
    static String N;
    static int M;
    static final String startChannel = "100";

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = br.readLine();
        M = Integer.parseInt(br.readLine());
        
        // 고장난 버튼이 없으면(위,아래 왔다 갔다 vs 직접입력(자릿수))
        if(M == 0) {
            System.out.println(Math.min(N.length(), Math.abs(100 - Integer.parseInt(N))));
            return;
        }
        
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 고장난 번호 확인
        for (int i = 0; i < M; i++) {
            int index = Integer.parseInt(st.nextToken());
            broken[index] = true;
        }
        
        // 시작채널(100)과 같으면
        if(N.equals(startChannel)) {
            System.out.println(0);
            return;
        }

        int currentChannel = Integer.parseInt(startChannel);
        
        // 위, 아래 딸깍
        int onlyUpDown = Math.abs(Integer.parseInt(N) - currentChannel);
        // 채널 이동 후 딸깍
        int complexUsage = Integer.MAX_VALUE;

        int targetN = Integer.parseInt(N);
        int digits = N.length();
        // 최대값(아무리 커도 자릿수+1보다 크진 않을거다... (목표 999 / 가능한 수 1000))
        int maxRange = (int) Math.pow(10, digits + 1);

        for (int channel = 0; channel < maxRange; channel++) {
            if(canMakeChannel(channel)) {
                // 채널 입력을 위한 번호 수 + 딸깍
                int usage = String.valueOf(channel).length() + Math.abs(channel - targetN);
                complexUsage = Math.min(complexUsage, usage);
            }
        }

        System.out.println(Math.min(onlyUpDown, complexUsage));
    }

    /**
     * @param channel 채널 번호
     * @return 사용 가능한 번호로 만들 수 있는가
     */
    static boolean canMakeChannel(int channel) {
        String channelStr = String.valueOf(channel);
        for (char digit : channelStr.toCharArray()) {
            int digitNum = digit - '0';
            if (broken[digitNum]) {  // 이 버튼이 고장났다면
                return false;
            }
        }
        return true;
    }
}
