# LeetCode 283 - Move Zeroes（移动零）

| 项目 | 内容 |
|------|------|
| 难度 | Easy |
| 链接 | https://leetcode.cn/problems/move-zeroes/ |
| 类别 | 双指针 · 数组 |
| 关联题目 | #26 删除有序数组中的重复项, #27 移除元素, #80 删除有序数组中的重复项 II |

---

## 题目描述

给定一个数组 `nums`，编写一个函数将所有 `0` 移动到数组的**末尾**，同时保持非零元素的**相对顺序**。

请注意：必须在不复制数组的情况下**原地**对数组进行操作。

### 示例

```
输入: nums = [0,1,0,3,12]
输出: [1,3,12,0,0]

输入: nums = [0]
输出: [0]
```

### 约束

- `1 <= nums.length <= 10^4`
- `-2^31 <= nums[i] <= 2^31 - 1`

---

## 解题思路

### 核心思想：快慢双指针

慢指针 `slowIndex` 指向「下一个非零元素应该放的位置」，快指针 `index` 遍历整个数组。

```
遍历：
  遇到非零元素 → 移到 slowIndex 处，slowIndex 右移
  遇到 0 → 跳过（留给后续非零元素覆盖）
```

遍历结束后，`slowIndex` 之后的元素都被覆盖过或原本就是 0，即所有非零元素已前移、0 都在末尾。

### 关键：`slowIndex != index` 时才能置 0

```java
nums[slowIndex] = nums[index];   // 前移
if (slowIndex != index) {
    nums[index] = 0;             // 原位置补 0
}
slowIndex++;
```

**为什么必须判断 `slowIndex != index`**：当 `slowIndex == index` 时，说明当前位置前面没有 0 需要填补（如 `[1,2,3]` 开头），此时若无条件执行 `nums[index] = 0`，会把原本的非零元素错误清零。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n) — 一次遍历 |
| 空间 | O(1) — 原地操作 |

---

## 代码实现

### Java

```java
public class Leetcode_283 {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int slowIndex = 0;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != 0) {
                nums[slowIndex] = nums[index];

                if (slowIndex != index) {
                    nums[index] = 0;
                }

                slowIndex++;
            }
        }
    }
}
```

**写法要点**：
- **慢指针记录非零位置**：`slowIndex` 始终指向下一个非零元素应放置的位置
- **`slowIndex != index` 判断**：只有慢指针落后于快指针（前面有 0）时，才把原位置补 0，避免误清非零元素
- **原地操作**：不复制数组，用覆盖 + 补 0 完成移动

---

## 关键点总结

1. **快慢指针**：快指针遍历，慢指针记录非零放置位置
2. **`slowIndex != index` 判空**：这是易错点，无条件置 0 会把开头的非零元素误清
3. **原地 O(1)**：覆盖式移动，无额外空间
4. **同类模板**：本题是「移除指定元素」类题目的变体（#26/#27/#80 同套路）

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #27 | 移除元素 | 移除指定值，无需补 0 |
| #26 | 删除有序数组中的重复项 | 移除重复元素 |
| #80 | 删除有序数组中的重复项 II | 重复元素最多保留两个 |
