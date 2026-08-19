package gldailycode.queue;

import java.util.Stack;

public class Leetcode_232 {
    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    public Leetcode_232() {

    }

    public void push(int x) {
        // 先把 stack1 全部倒入 stack2，让新元素落到栈底
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }

        stack2.push(x);

        // 再把 stack2 倒回 stack1，恢复队首在栈顶的顺序
        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
    }

    public int pop() {
        return stack1.pop();
    }

    public int peek() {
        return stack1.peek();
    }

    public boolean empty() {
        return stack1.empty();
    }
}
