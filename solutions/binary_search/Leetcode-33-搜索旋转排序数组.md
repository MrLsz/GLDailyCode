# LeetCode 33 - Search in Rotated Sorted Array（搜索旋转排序数组）

| 项目 | 内容 |
|------|------|
| 难度 | Medium |
| 链接 | https://leetcode.cn/problems/search-in-rotated-sorted-array/ |
| 类别 | 二分查找 |
| 关联题目 | #81 搜索旋转排序数组 II, #153 寻找旋转排序数组中的最小值, #704 二分查找 |

---

## 题目描述

整数数组 `nums` 原本按升序排列，数组中的值**互不相同**。在传递给函数之前，`nums` 在某个未知下标 `k` 上进行了旋转。

给你旋转后的数组 `nums` 和一个整数 `target`，如果 `nums` 中存在 `target`，返回它的下标；否则返回 `-1`。

要求算法时间复杂度为 `O(log n)`。

### 示例

```
输入：nums = [4,5,6,7,0,1,2], target = 0
输出：4

输入：nums = [4,5,6,7,0,1,2], target = 3
输出：-1

输入：nums = [1], target = 0
输出：-1
```

### 约束

- `1 <= nums.length <= 5000`
- `-10^4 <= nums[i] <= 10^4`
- `nums` 中每个值独一无二
- `nums` 在预先未知的某个下标上进行了旋转
- `-10^4 <= target <= 10^4`

---

## 解题思路

### 核心思想：旋转数组二分后，至少一半是有序的

普通二分依赖「整个数组有序」，旋转数组破坏了这一点。但关键性质是：**把数组从中间一分为二，至少有一半仍然是有序的**。

于是先判断哪一半有序，再看 `target` 是否落在有序的那一半里：

```
1. 计算 mid
2. nums[mid] == target → 直接返回
3. 判断左半边是否有序：nums[mid] >= nums[left]
   - 左半边有序：若 target 在 [nums[left], nums[mid]) 内 → 收缩左半，否则收缩右半
   - 右半边有序：若 target 在 (nums[mid], nums[right]] 内 → 收缩右半，否则收缩左半
```

> **判断左半边有序的依据**：旋转数组里，`nums[mid] >= nums[left]` 说明 mid 落在未旋转的左侧递增段（左半边有序）；反之 mid 落在右侧递增段（右半边有序）。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(log n) — 每次二分缩小一半 |
| 空间 | O(1) |

---

## 代码实现

### Java

```java
public class Leetcode_33 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int leftIndex = 0;
        int rightIndex = nums.length - 1;
        while (leftIndex <= rightIndex) {
            int midIndex = leftIndex + (rightIndex - leftIndex) / 2;
            if (nums[midIndex] == target) {
                return midIndex;
            }

            // 左半边有序
            if (nums[midIndex] >= nums[leftIndex]) {
                if (target >= nums[leftIndex] && target < nums[midIndex]) {
                    rightIndex = midIndex - 1;
                } else {
                    leftIndex = midIndex + 1;
                }
            } else {
                // 右半边有序
                if (target > nums[midIndex] && target <= nums[rightIndex]) {
                    leftIndex = midIndex + 1;
                } else {
                    rightIndex = midIndex - 1;
                }
            }
        }

        return -1;
    }
}
```

**写法要点**：
- **先判断哪半边有序**：`nums[mid] >= nums[left]` 判定左半边有序，这是旋转数组二分的核心
- **边界条件要带等号**：`target >= nums[left]`、`target <= nums[right]` 必须包含端点；`target < nums[mid]`、`target > nums[mid]` 严格不等（因为 `== mid` 的情况已在前面返回）
- **mid 计算防溢出**：`left + (right - left) / 2` 而非 `(left + right) / 2`
- **元素互不相同**是前提：若含重复元素（#81），`nums[mid] >= nums[left]` 判断会失效，需额外去重

---

## 关键点总结

1. **旋转数组二分的关键**：`nums[mid] >= nums[left]` 判断左半边有序，否则右半边有序
2. **在有序半段内判断 target**：落在有序半段的区间内就收缩该半段，否则收缩另一半段
3. **区间边界**：左闭右开 / 左开右闭的等号位置要精确，否则漏判端点
4. **无重复元素是前提**：#81 含重复时需先处理 `nums[left] == nums[mid] == nums[right]` 的退化情况

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #81 | 搜索旋转排序数组 II | 含重复元素，需去重处理 |
| #153 | 寻找旋转排序数组的最小值 | 找最小值而非 target |
| #704 | 二分查找 | 旋转数组二分的基础（无旋转） |
