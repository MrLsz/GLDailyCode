package gldailycode.queue;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode_225 {
    private Queue<Integer> queue1 = new LinkedList<>();
    private Queue<Integer> queue2 = new LinkedList<>();

    public void push(int x) {
        // 1. 新元素入辅助队列
        queue2.offer(x);

        // 2. 主队列全部元素倒入辅助队列（新元素变队首）
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }

        // 3. 交换引用：queue1 始终保持栈顺序
        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;
    }

    public int pop() {
        return queue1.poll();   // 队首 = 栈顶
    }

    public int top() {
        return queue1.peek();   // 队首 = 栈顶
    }

    public boolean empty() {
        return queue1.isEmpty();
    }
}
