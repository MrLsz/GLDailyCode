package gldailycode.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Leetcode_20 {
    private static final Map<Character, Character> BRACKET_PAIRS = new HashMap<>();

    static {
        BRACKET_PAIRS.put(')', '(');
        BRACKET_PAIRS.put('}', '{');
        BRACKET_PAIRS.put(']', '[');
    }

    public boolean isValid(String s) {
        // 特判：长度为奇数无法完全匹配
        if (s.length() % 2 != 0) {
            return false;
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char bracket : s.toCharArray()) {
            Character expectedLeft = BRACKET_PAIRS.get(bracket);
            if (expectedLeft != null) {
                // 右括号：栈顶必须匹配
                if (stack.isEmpty() || stack.peek() != expectedLeft) {
                    return false;
                }
                stack.pop();
            } else {
                // 左括号：压入栈
                stack.push(bracket);
            }
        }

        return stack.isEmpty();
    }
}
