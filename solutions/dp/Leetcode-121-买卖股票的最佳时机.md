# LeetCode 121 - Best Time to Buy and Sell Stock（买卖股票的最佳时机）

| 项目 | 内容 |
|------|------|
| 难度 | Easy |
| 链接 | https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/ |
| 类别 | DP · 贪心 |
| 关联题目 | #122 买卖股票 II, #123 买卖股票 III, #309 最佳买卖股票时机含冷冻期 |

---

## 题目描述

给定一个数组 `prices`，它的第 `i` 个元素是一支股票第 `i` 天的价格。

你只能选择**某一天买入**这只股票，并选择在**未来的某一个不同的日子**卖出。返回你能获得的最大利润；如果不能获得任何利润，返回 `0`。

### 示例

```
输入：prices = [7,1,5,3,6,4]
输出：5
解释：在第 2 天（价格=1）买入，第 5 天（价格=6）卖出，利润 6-1=5

输入：prices = [7,6,4,3,1]
输出：0
解释：任何买入卖出都亏损，返回 0
```

### 约束

- `1 <= prices.length <= 10^5`
- `0 <= prices[i] <= 10^4`

---

## 解题思路

### 解法一：贪心（一次遍历）

只买卖一次，最大利润 = `max(prices[j] - prices[i])`，其中 `i < j`。

一次遍历维护「历史最低价」`minValue`，每天用 `prices[i] - minValue` 更新最大利润：

```
遍历 prices：
  result = max(result, prices[i] - minValue)   // 今天卖出能赚多少
  minValue = min(minValue, prices[i])           // 更新历史最低价
```

### 解法二：DP（状态机）

定义两个状态：

- `dp[i][0]`：第 i 天**持有**股票的最大收益
- `dp[i][1]`：第 i 天**不持有**股票的最大收益

状态转移（只能买一次，所以「持有」的买入成本就是 `-prices[i]`）：

```
dp[i][0] = max(dp[i-1][0], -prices[i])           // 继续持有 或 今天买入
dp[i][1] = max(dp[i-1][1], prices[i] + dp[i-1][0]) // 继续空仓 或 今天卖出
```

初始：`dp[0][0] = -prices[0]`，`dp[0][1] = 0`。答案 `dp[n-1][1]`。

### 复杂度分析

| 解法 | 时间 | 空间 |
|------|------|------|
| 贪心 | O(n) | O(1) |
| DP | O(n) | O(n)，可滚动优化到 O(1) |

---

## 代码实现

### Java — 贪心

```java
public int maxProfit(int[] prices) {
    if (prices == null || prices.length <= 1) {
        return 0;
    }

    int minValue = prices[0];
    int resultValue = 0;
    for (int index = 1; index < prices.length; index++) {
        resultValue = Math.max(resultValue, prices[index] - minValue);
        if (prices[index] < minValue) {
            minValue = prices[index];
        }
    }

    return resultValue;
}
```

### Java — DP（状态机）

```java
public int maxProfit1(int[] prices) {
    // dp[i][0] 表示第i天持有股票的收益
    // dp[i][1] 表示第i天不持有股票的收益

    int[][] dp = new int[prices.length][2];
    dp[0][0] = -prices[0];

    for (int index = 1; index < prices.length; index++) {
        dp[index][0] = Math.max(dp[index - 1][0], -prices[index]);
        dp[index][1] = Math.max(dp[index - 1][1], prices[index] + dp[index - 1][0]);
    }

    return dp[prices.length - 1][1];
}
```

**写法要点**：
- 贪心版**先更新利润、再更新最低价**：若先更新最低价，会把「当天买入当天卖出」算进去（利润恒 0，虽不影响结果但语义不严谨）
- DP 版 `dp[0][1]` 默认 0（不持有、不交易），无需显式初始化
- `dp[i][0]` 买入成本是 `-prices[i]`（不是 `dp[i-1][1]-prices[i]`），因为本题**只能买一次**

---

## 关键点总结

1. **只买卖一次**：贪心只需维护历史最低价，一次遍历 O(n)/O(1)
2. **先算利润后更新最低价**：保证买入日早于卖出日
3. **DP 状态机**：`持有` vs `不持有` 两状态，是后续 #122/#123 系列题的基础框架
4. **买入成本**：本题是 `-prices[i]`；#122 可多次买卖时变为 `dp[i-1][1] - prices[i]`

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #122 | 买卖股票 II | 可多次买卖，买入成本变 `dp[i-1][1]-prices[i]` |
| #123 | 买卖股票 III | 最多两笔，状态翻倍 |
| #309 | 含冷冻期 | 卖出后需隔一天才能买 |
