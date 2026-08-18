package gldailycode.dp;

public class Leetcode_322 {
    public int coinChange(int[] coins, int amount) {
        // dp[j] 表示凑够金额 j 所需的最少硬币数
        int[] dp = new int[amount + 1];

        // 初始化：置为无穷大，表示「无法凑成」
        for (int index = 0; index < dp.length; index++) {
            dp[index] = Integer.MAX_VALUE;
        }
        dp[0] = 0;  // 凑成 0 元需要 0 个硬币

        // 完全背包：正序遍历（每种硬币可用无限次）
        for (int i = 0; i < coins.length; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                // dp[j - coins[i]] 有效时才更新，避免溢出
                if (dp[j - coins[i]] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
