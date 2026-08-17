package gldailycode.dp;

public class Leetcode_62 {
    public int uniquePaths(int m, int n) {
        // dp[i][j] = 到达 (i,j) 的路径数
        int[][] dp = new int[m][n];

        // 第一行和第一列只有一种走法
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;

        // 递推：上方位 + 左侧位
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }
}
