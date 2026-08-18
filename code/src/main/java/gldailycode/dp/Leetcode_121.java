package gldailycode.dp;

public class Leetcode_121 {
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

    public int maxProfit1(int[] prices) {
        // dp[i][0] 表示第i天持有股票的收益
        // dp[i][1] 表示第i天不持有股票的收益
        
        int[][] dp = new int[prices.length][2];
        dp[0][0] = -prices[0];
        
        // dp[i][0] = Math.max(dp[i - 1][0], -prices[i]);
        // dp[i][1] = Math.max(dp[i - 1][1], prices[i] + dp[i - 1][0]);

        for (int index = 1; index < prices.length; index++) {
            dp[index][0] = Math.max(dp[index - 1][0], -prices[index]);
            dp[index][1] = Math.max(dp[index - 1][1], prices[index] + dp[index - 1][0]);
        }

        return dp[prices.length - 1][1];
    }
}
