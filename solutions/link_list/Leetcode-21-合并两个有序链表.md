# LeetCode 21 - Merge Two Sorted Lists（合并两个有序链表）

| 项目 | 内容 |
|------|------|
| 难度 | Easy |
| 链接 | https://leetcode.cn/problems/merge-two-sorted-lists/ |
| 类别 | 链表 · 双指针 |
| 关联题目 | #23 合并 K 个升序链表, #88 合并两个有序数组, #148 排序链表 |

---

## 题目描述

将两个**升序**链表合并为一个新的**升序**链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。

### 示例

```
输入：l1 = [1,2,4], l2 = [1,3,4]
输出：[1,1,2,3,4,4]

输入：l1 = [], l2 = []
输出：[]

输入：l1 = [], l2 = [0]
输出：[0]
```

### 约束

- 两个链表的节点数目范围是 `[0, 50]`
- `-100 <= Node.val <= 100`
- `l1` 和 `l2` 均按非递减顺序排列

---

## 解题思路

### 核心思想：双指针 + 虚拟头节点（dummy）

用两个指针 `node1`、`node2` 分别遍历两个链表，每次把值较小的节点接到结果链表尾部，直到其中一个链表遍历完，再把另一个链表的剩余部分直接接上。

**虚拟头节点**的作用：避免处理「结果链表头节点为空」的特殊情况，最后返回 `dummy.next` 即可。

```
1. 新建 dummy 节点，rebuild 指针指向 dummy（结果链表的尾节点）
2. node1、node2 分别指向 list1、list2
3. 循环比较 node1.val 和 node2.val：
   - 较小者接入 rebuild.next，对应指针后移
   - rebuild 后移
4. 循环结束，把未遍历完的链表剩余部分接到 rebuild.next
5. 返回 dummy.next
```

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(m + n) — 遍历两个链表各一次 |
| 空间 | O(1) — 只用了常数个指针，原地拼接 |

---

## 代码实现

### Java

```java
public class Leetcode_21 {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        // 虚拟头节点，简化边界处理
        ListNode newHeadNode = new ListNode(0);

        ListNode rebuildNode = newHeadNode;
        ListNode node1 = list1;
        ListNode node2 = list2;
        while (node1 != null && node2 != null) {
            if (node1.val < node2.val) {
                rebuildNode.next = node1;
                node1 = node1.next;
            } else {
                rebuildNode.next = node2;
                node2 = node2.next;
            }

            rebuildNode = rebuildNode.next;
        }

        // 接上剩余的非空链表
        if (node1 != null) {
            rebuildNode.next = node1;
        } else {
            rebuildNode.next = node2;
        }

        return newHeadNode.next;
    }
}
```

**写法要点**：
- **虚拟头节点** `new ListNode(0)`：让结果链表始终有头，省去「第一个节点从哪来」的判断
- **两个指针后移**：谁小接谁，对应指针 `node1`/`node2` 后移；`rebuild` 始终后移
- **剩余链表直接拼接**：循环结束后，非空的那条链表整体接到尾部，无需再逐个遍历
- **相等时**：`node1.val < node2.val` 不成立则接 `node2`，两者相等时顺序不影响结果

---

## 关键点总结

1. **dummy 节点**是链表合并类题目的通用技巧，避免处理头节点边界
2. **谁小接谁**：双指针同步遍历，比较后接入较小者
3. **剩余直接拼接**：一条链表走完，另一条剩下的有序部分直接接上，O(1) 完成
4. **原地操作**：没有新建节点（除 dummy），空间 O(1)

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #23 | 合并 K 个升序链表 | 两两合并 → 优先队列/分治 |
| #88 | 合并两个有序数组 | 链表变数组，从后往前填充 |
| #148 | 排序链表 | 归并排序的 merge 步骤 |
