# LeetCode 232 - Implement Queue using Stacks（用栈实现队列）

| 项目 | 内容 |
|------|------|
| 难度 | Easy |
| 链接 | https://leetcode.cn/problems/implement-queue-using-stacks/ |
| 类别 | 栈 · 队列 |
| 关联题目 | #225 用队列实现栈 |

---

## 题目描述

请你仅使用**两个栈**实现先入先出队列。队列应当支持一般队列支持的所有操作：

- `push(x)`：将元素 x 推到队列的末尾
- `pop()`：从队列的开头移除并返回元素
- `peek()`：返回队列开头的元素
- `empty()`：若队列为空返回 true，否则返回 false

### 说明

只能使用标准的栈操作：`push to top`、`peek/pop from top`、`size`、`is empty`。

---

## 解题思路

### 核心思想：用第二个栈「倒腾」出队首顺序

栈是后进先出（LIFO），队列是先进先出（FIFO）。要让栈实现队列，关键是**让队首元素处于栈顶**，这样 `pop` 才能直接弹出队首。

本实现采用「push 时倒腾」的策略：

```
push(x):
  1. 把 stack1 全部弹出并压入 stack2（此时顺序反转）
  2. 把新元素 x 压入 stack2（x 落到最底部）
  3. 再把 stack2 全部弹出并压回 stack1
     → 结果：stack1 栈顶是队首，栈底是队尾
```

这样 `pop` / `peek` 直接操作 `stack1` 栈顶即可。

### 复杂度分析

| 操作 | 时间 | 说明 |
|------|------|------|
| push | O(n) | 每次 push 都要来回倒腾两个栈 |
| pop | O(1) | 直接弹栈顶 |
| peek | O(1) | 直接看栈顶 |
| empty | O(1) | 判空 |
| 空间 | O(n) | 两个栈共存储 n 个元素 |

> **另一种等价写法（pop/peek 时倒腾）**：`push` 直接压入 stack1（O(1)），`pop`/`peek` 时若 stack2 为空则把 stack1 全部倒入 stack2 再操作。这种写法 `pop`/`peek` 摊还 O(1)，是更常见的优化版本，但本实现更直观。

---

## 代码实现

### Java

```java
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
```

**写法要点**：
- **两个栈分工**：`stack1` 始终维护「队首在栈顶」的顺序，`stack2` 只是 push 时的临时中转
- **push 两次倒腾**：第一次把旧元素倒入 stack2（逆序），第二次连同新元素倒回 stack1（恢复顺序），保证新元素在栈底
- **pop/peek 直接操作 stack1**：因为队首已被维护在栈顶，无需再判断
- **empty 只看 stack1**：元素始终都存在 stack1 中

---

## 关键点总结

1. **栈 vs 队列的本质差异**：LIFO vs FIFO，需要「倒腾」来反转顺序
2. **push 时倒腾**：维护 stack1 栈顶即队首，pop/peek 才能 O(1)
3. **两种实现策略**：push 时倒腾（本解，push O(n)）vs pop/peek 时倒腾（摊还 O(1)）
4. **与 #225 对照**：#225 用队列实现栈，是本题的逆问题，思路对称

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #225 | 用队列实现栈 | 逆问题：队列模拟栈，pop 时倒腾 |
