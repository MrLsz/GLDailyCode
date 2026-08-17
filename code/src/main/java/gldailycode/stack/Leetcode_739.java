package gldailycode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Leetcode_739 {
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null) {
            return null;
        }

        int[] res = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();   // 存下标，单调递减栈

        for (int i = 0; i < temperatures.length; i++) {
            // 当前温度 > 栈顶温度 → 栈顶元素找到答案，批量出栈
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                res[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        // 栈中剩余元素自动为 0（int 数组默认值）
        return res;
    }
}
