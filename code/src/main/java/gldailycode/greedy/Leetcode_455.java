package gldailycode.greedy;

import java.util.Arrays;

public class Leetcode_455 {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);  // 孩子胃口升序
        Arrays.sort(s);  // 饼干尺寸升序

        int child = 0;
        int cookie = 0;

        while (child < g.length && cookie < s.length) {
            if (s[cookie] >= g[child]) {
                child++;   // 当前饼干满足该孩子
            }
            cookie++;      // 饼干移向下一块
        }

        return child;
    }
}
