# LeetCode 146 - LRU Cache（LRU 缓存）

| 项目 | 内容 |
|------|------|
| 难度 | Medium |
| 链接 | https://leetcode.cn/problems/lru-cache/ |
| 类别 | 哈希表 · 双向链表 |
| 关联题目 | #460 LFU 缓存 |

---

## 题目描述

设计并实现一个满足 **LRU（最近最少使用）** 缓存约束的数据结构。

实现 `LRUCache` 类：

- `LRUCache(int capacity)`：以正整数作为容量初始化 LRU 缓存
- `int get(int key)`：若 key 存在则返回对应 value，否则返回 -1
- `void put(int key, int value)`：若 key 已存在则更新 value；否则插入。当缓存容量达到上限时，应**逐出最久未使用**的键值对

`get` 和 `put` 必须以 O(1) 的平均时间复杂度运行。

### 示例

```
输入：
["LRUCache","put","put","get","put","get","put","get","get","get"]
[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]
输出：[null,null,null,1,null,-1,null,-1,3,4]
```

---

## 解题思路

### 核心思想：HashMap + 双向链表

- **HashMap**：`key → Node`，实现 O(1) 查找
- **双向链表**：维护访问顺序，头部是最近使用，尾部是最久未使用

```
虚拟头 head ⟷ 最近使用 ⟷ ... ⟷ 最久未使用 ⟷ 虚拟尾 last
```

关键操作：

1. **get(key)**：查 HashMap，命中则把节点移到头部（`moveToHead`）
2. **put(key, value)**：
   - key 已存在 → 更新 value 并移到头部
   - key 不存在 → 若已满先 `removeLast`，再新节点插入头部
3. **moveToHead**：先从原位置摘除，再插入 head 之后
4. **removeLast**：删除 last 前一个节点（最久未使用）

### 复杂度分析

| 操作 | 时间 |
|------|------|
| get | O(1) — HashMap 查找 + 链表移动 |
| put | O(1) — HashMap 操作 + 链表插入/删除 |
| 空间 | O(capacity) |

---

## 代码实现

### Java

```java
public class Leetcode_146 {
    private HashMap<Integer, Node> hashMap;
    private Node headNode;   // 虚拟头节点
    private Node lastNode;   // 虚拟尾节点
    private int capacity;

    public Leetcode_146(int capacity) {
        this.capacity = capacity;
        this.hashMap = new HashMap<>();
        this.headNode = new Node(0, 0);
        this.lastNode = new Node(0, 0);
        headNode.nextNode = lastNode;
        lastNode.preNode = headNode;
    }

    public int get(int key) {
        if (!hashMap.containsKey(key)) {
            return -1;
        }

        Node curNode = hashMap.get(key);
        this.moveToHead(curNode);
        return curNode.value;
    }

    public void moveToHead(Node curNode) {
        // 1. 从原位置摘除
        curNode.preNode.nextNode = curNode.nextNode;
        curNode.nextNode.preNode = curNode.preNode;

        // 2. 插入头部
        curNode.nextNode = headNode.nextNode;
        curNode.nextNode.preNode = curNode;   // 原首节点的 preNode 指向 curNode
        headNode.nextNode = curNode;
        curNode.preNode = headNode;
    }

    public void put(int key, int value) {
        if (this.capacity <= 0) {
            return;
        }

        if (hashMap.containsKey(key)) {
            Node curNode = hashMap.get(key);
            curNode.value = value;
            this.moveToHead(curNode);
            return;
        }

        if (hashMap.size() == this.capacity) {
            this.removeLast();
        }

        // 新节点直接插入头部
        Node curNode = new Node(key, value);
        curNode.nextNode = headNode.nextNode;
        curNode.nextNode.preNode = curNode;
        headNode.nextNode = curNode;
        curNode.preNode = headNode;
        hashMap.put(key, curNode);
    }

    public void removeLast() {
        if (hashMap.isEmpty()) {
            return;
        }

        Node tailNode = lastNode.preNode;   // 最久未使用节点
        tailNode.preNode.nextNode = lastNode;
        lastNode.preNode = tailNode.preNode;
        hashMap.remove(tailNode.key);
    }
}

class Node {
    public int key;
    public int value;
    public Node nextNode;
    public Node preNode;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
```

---

## 原代码的 Bug 修复说明

1. **`moveToHead` 缺少一行（核心 bug）**：把节点插到头部时，只更新了 `headNode.nextNode = curNode`，却没更新「原首节点」的 `preNode`，导致双向链表断裂。已补上 `curNode.nextNode.preNode = curNode;`

2. **`put` 插入冗余**：新节点先插到链表尾部、再 `moveToHead` 移回头部，多余两步。改为新节点直接插入头部

3. **`removeLast` 变量命名**：`preNode` 实为「尾部节点」，易混淆，改名 `tailNode` 更清晰（逻辑本身正确）

---

## 关键点总结

1. **HashMap + 双向链表**是 LRU 的标准结构：Hash 管查找，链表管顺序
2. **虚拟头尾节点**：消除「链表为空」「删除头/尾」的边界判断
3. **moveToHead 必须完整双向操作**：摘除 + 插入，两个方向的指针都要改，漏一个链表就断
4. **节点存 key**：删除尾部时要用 `tailNode.key` 从 HashMap 同步移除，所以 Node 里必须存 key

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #460 | LFU 缓存 | 按使用频率淘汰，需额外的频率计数 |
