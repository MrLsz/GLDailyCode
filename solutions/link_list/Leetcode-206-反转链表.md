# LeetCode 206 - Reverse Linked List（反转链表）

| 项目 | 内容 |
|------|------|
| 难度 | Easy |
| 链接 | https://leetcode.cn/problems/reverse-linked-list/ |
| 类别 | 链表 |
| 关联题目 | #92 反转链表 II, #25 K 个一组翻转链表, #234 回文链表 |

---

## 题目描述

给你单链表的头节点 `head`，请你反转链表，并返回反转后的链表。

### 示例

```
输入：head = [1,2,3,4,5]
输出：[5,4,3,2,1]

输入：head = [1,2]
输出：[2,1]

输入：head = []
输出：[]
```

### 约束

- 链表中节点的数目范围是 `[0, 5000]`
- `-5000 <= Node.val <= 5000`

---

## 解题思路

### 核心思想：三指针迭代，逐个反转指向

用两个指针 `preNode`（前驱）和 `curNode`（当前），遍历时把 `curNode.next` 指回 `preNode`，完成反转。

```
1. preNode = null（反转后尾节点指向 null）
2. curNode = head
3. 循环：
   - tempNode = curNode.next   // 先保存下一个节点，防止断链
   - curNode.next = preNode    // 反转当前节点指向
   - preNode = curNode         // 前驱后移
   - curNode = tempNode        // 当前后移
4. 返回 preNode（新的头节点）
```

> **为什么先保存 `tempNode`**：一旦 `curNode.next` 被改指向 `preNode`，原链表的下一个节点就找不到了，必须先用临时变量存住。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n) — 遍历链表一次 |
| 空间 | O(1) — 三个指针，原地反转 |

---

## 代码实现

### Java

```java
public class Leetcode_206 {
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode preNode = null;
        ListNode curNode = head;
        while (curNode != null) {
            ListNode tempNode = curNode.next;
            curNode.next = preNode;

            preNode = curNode;
            curNode = tempNode;
        }

        return preNode;
    }
}
```

**写法要点**：
- **先存后断**：`tempNode = curNode.next` 必须放在 `curNode.next = preNode` 之前，否则链表断裂
- **循环条件 `curNode != null`**：当 `curNode` 走到原链表末尾的 `null` 时结束，此时 `preNode` 正好是反转后的头节点
- **返回 `preNode`**：循环结束时 `curNode` 为 null，`preNode` 指向原链表最后一个节点，即新头节点
- **空链表**：`head == null` 直接返回 null

---

## 关键点总结

1. **三指针套路**：`pre` / `cur` / `temp` 逐个反转，链表反转的基石
2. **先存后断**：先保存 `cur.next` 再改指向，否则断链
3. **返回 preNode**：循环结束 preNode 即新头节点
4. **原地 O(1)**：不新建节点，只改指针

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #92 | 反转链表 II | 只反转指定区间 [left, right] |
| #25 | K 个一组翻转链表 | 每 k 个节点一组反转 |
| #234 | 回文链表 | 反转后半段后与前半段比较 |
