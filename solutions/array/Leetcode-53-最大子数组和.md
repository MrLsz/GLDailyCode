# LeetCode 53 - Maximum Subarray（最大子数组和）

| 项目 | 内容 |
|------|------|
| 难度 | Medium |
| 链接 | https://leetcode.cn/problems/maximum-subarray/ |
| 类别 | DP · 贪心 · 数组 |
| 关联题目 | #152 乘积最大子数组, #918 环形子数组的最大和, #121 买卖股票的最佳时机 |

---

## 题目描述

给定一个整数数组 `nums`，找出一个具有**最大和的连续子数组**（子数组最少包含一个元素），返回其最大和。

### 示例

```
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6

输入：nums = [1]
输出：1

输入：nums = [5,4,-1,7,8]
输出：23
```

### 约束

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`

---

## 解题思路

### 核心思想：Kadane 算法（贪心一次遍历）

维护两个变量：

- `tempTotalCount`：**以当前元素结尾**的连续子数组和
- `result`：全局最大子数组和

遍历数组，每一步：

```
1. tempTotalCount += nums[index]      // 累加当前元素
2. result = max(result, tempTotalCount) // 更新全局最大
3. 若 tempTotalCount < 0 → 置 0         // 负数前缀不可能让后续和更大，丢弃
```

> **为什么负数前缀要丢弃**：若当前累加和为负，它对后续子数组只会「拖后腿」，从下一个元素重新开始必然更优。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n) — 一次遍历 |
| 空间 | O(1) — 常数变量 |

---

## 代码实现

### Java

```java
public class Leetcode_53 {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int result = Integer.MIN_VALUE;
        int tempTotalCount = 0;
        for (int index = 0; index < nums.length; index++) {
            tempTotalCount += nums[index];
            result = Math.max(result, tempTotalCount);
            if (tempTotalCount < 0) {
                tempTotalCount = 0;
            }
        }

        return result;
    }
}
```

**写法要点**：
- **`result` 初始化为 `Integer.MIN_VALUE`**：处理全负数数组（如 `[-2,-1]`）时，最大子数组和是 -1 而非 0
- **先更新 result 再判断负数**：保证单个负数元素也能被正确计入（先 `max` 后重置）
- **`tempTotalCount < 0` 才重置**：等于 0 时保留（0 也可能是答案的一部分）

---

## 关键点总结

1. **Kadane 算法**：贪心思想，一次遍历 O(n)/O(1)
2. **负数前缀丢弃**：累加和为负时从下一个元素重新开始
3. **result 用 MIN_VALUE 初始化**：否则全负数数组会错误返回 0
4. **先更新后重置的顺序**：保证单个负数元素也能参与比较

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #152 | 乘积最大子数组 | 同时维护最大/最小乘积（负负得正） |
| #918 | 环形子数组的最大和 | 数组首尾相连，需考虑跨边界情况 |
| #121 | 买卖股票的最佳时机 | 转化为「最大连续差值」问题 |
