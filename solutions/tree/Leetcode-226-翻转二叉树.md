# LeetCode 226 - Invert Binary Tree（翻转二叉树）

| 项目 | 内容 |
|------|------|
| 难度 | Easy |
| 链接 | https://leetcode.cn/problems/invert-binary-tree/ |
| 类别 | 树 |
| 关联题目 | #101 对称二叉树, #100 相同的树, #572 另一棵树的子树, #543 二叉树直径 |

---

## 题目描述

给你一棵二叉树的根节点 `root`，翻转这棵二叉树（即每个节点的左右子树互换），并返回其根节点。

### 示例

```
输入：root = [4,2,7,1,3,6,9]
输出：[4,7,2,9,6,3,1]

输入：root = [2,1,3]
输出：[2,3,1]

输入：root = []
输出：[]
```

### 约束

- 树中节点数目范围 `[0, 100]`
- `-100 <= Node.val <= 100`

---

## 解题思路

### 核心思想：交换每个节点的左右指针

翻转二叉树的本质就是：对树中的**每一个节点**，把它的 `left` 和 `right` 指针互换。用递归逐层处理即可。

### 递归三步（先交换、再递归）

```
1. 交换当前节点的左右子节点：temp = node.left; node.left = node.right; node.right = temp
2. 递归处理交换后的左子树 traversal(node.left)
3. 递归处理交换后的右子树 traversal(node.right)
```

> **先交换再递归**：先交换当前节点，再深入处理其子节点（前序交换）。交换前用临时变量保存原左子树，避免引用丢失。先递归后交换同样可行，效果一致。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n) — 每个节点访问一次 |
| 空间 | O(h) — 递归栈深度，最坏 O(n)（链状树），平均 O(log n) |

---

## 代码实现

### Java — 递归

```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        traversal(root);
        return root;
    }

    public void traversal(TreeNode node) {
        if (node == null) {
            return;
        }
        // 先交换当前节点的左右子节点
        TreeNode tempNode = node.left;
        node.left = node.right;
        node.right = tempNode;
        // 再递归处理子节点
        traversal(node.left);
        traversal(node.right);
    }
}
```

### Java — 迭代（BFS 层序遍历）

```java
import java.util.*;

class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode node = q.poll();

            // 交换当前节点的左右子节点
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            // 子节点入队继续处理
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }

        return root;
    }
}
```

**写法要点**：
- 递归版采用「先交换、再递归」的前序交换：先用 `tempNode` 保存原左子树，交换后继续递归处理
- 迭代版用队列做 BFS，对每个出队节点交换其左右子节点后继续入队即可
- 空节点 `node == null` 直接返回，是递归终止条件和迭代起始守卫

---

## 关键点总结

1. **本质是「交换指针」**：翻转不是重建节点，只是把每个节点的 `left`、`right` 互换
2. **递归顺序**：先交换再递归（前序交换），用临时变量保存原左子树避免引用丢失
3. **迭代等价写法**：BFS（队列）或 DFS（栈）逐节点交换，效果一致
4. **空树处理**：`null` 直接返回，是递归出口

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #101 | 对称二叉树 | 判断是否镜像对称（相当于翻转后仍相同） |
| #100 | 相同的树 | 判断两棵树结构 + 值是否完全相同 |
| #572 | 另一棵树的子树 | 判断一棵树是否包含另一棵子树 |
| #543 | 二叉树直径 | 递归求深度时顺便统计直径 |
