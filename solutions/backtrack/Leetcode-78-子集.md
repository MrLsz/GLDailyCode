# LeetCode 78 - Subsets（子集）

| 项目 | 内容 |
|------|------|
| 难度 | Medium |
| 链接 | https://leetcode.cn/problems/subsets/ |
| 类别 | 回溯 |
| 关联题目 | #90 子集 II, #46 全排列, #77 组合 |

---

## 题目描述

给你一个整数数组 `nums`，数组中的元素**互不相同**。返回该数组所有可能的子集（幂集）。

解集**不能包含重复的子集**，可以按任意顺序返回。

### 示例

```
输入：nums = [1,2,3]
输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]

输入：nums = [0]
输出：[[],[0]]
```

### 约束

- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`
- `nums` 中所有元素互不相同

---

## 解题思路

### 核心思想：回溯，每个节点都收集结果

子集问题与排列/组合的关键区别：**递归树的每一个节点都是一个合法子集**，而不是只在叶子节点收集。

```
                    []
        /           |           \
      [1]          [2]          [3]
     /   \          |
  [1,2]  [1,3]    [2,3]
    |
 [1,2,3]
```

递归三步：

```
1. 进入 traversal 时，先把当前 pathList 拷贝加入结果（每个 path 都是子集）
2. 从 step 开始遍历（防止回头，保证子集不重复）
3. 选择 nums[index] → 递归 index+1 → 回溯撤销
```

> **为什么从 step 开始遍历**：子集是无序的，`[1,2]` 和 `[2,1]` 算同一个。`step` 保证每次只往后选，天然去重。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n × 2^n) — 共 2^n 个子集，每个子集拷贝耗时 O(n) |
| 空间 | O(n) — 递归栈深度 + pathList 长度 |

---

## 代码实现

### Java

```java
public class Leetcode_78 {
    private final List<List<Integer>> resultList = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        traversal(nums, 0, new ArrayList<>());
        return resultList;
    }

    public void traversal(int[] nums, int step, List<Integer> pathList) {
        // 每个节点都是一个子集，拷贝后加入结果
        resultList.add(new ArrayList<Integer>(pathList));

        for (int index = step; index < nums.length; index++) {
            pathList.add(nums[index]);        // 选择
            traversal(nums, index + 1, pathList); // 递归
            pathList.remove(pathList.size() - 1); // 回溯，撤销选择
        }
    }
}
```

**写法要点**：
- **`new ArrayList<>(pathList)` 必须拷贝**：`pathList` 是共享引用，回溯时会不断增删，直接 `add(pathList)` 会让所有结果指向同一个最终被清空的列表
- **`step` 参数**：记录当前可选元素的起始下标，保证只向后选，避免 `[1,2]` 和 `[2,1]` 重复
- **回溯撤销**：`remove(size - 1)` 撤销上一步选择，让 `pathList` 回到递归前的状态
- **收集时机**：`traversal` 一进来就收集，所以空集 `[]` 也会被正确加入

---

## 关键点总结

1. **每个节点都收集**：子集问题在递归树每个节点收集结果，而非仅叶子
2. **拷贝副本**：`new ArrayList<>(pathList)`，防止引用污染
3. **step 防重复**：只往后选，天然保证子集无序去重
4. **回溯撤销**：`add` → 递归 → `remove`，三步固定套路

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #90 | 子集 II | 数组含重复元素，需先排序 + 同层去重 |
| #46 | 全排列 | 排列有序，叶子收集 + visited 标记 |
| #77 | 组合 | 固定长度 k 的子集，收集条件加 `pathList.size() == k` |
