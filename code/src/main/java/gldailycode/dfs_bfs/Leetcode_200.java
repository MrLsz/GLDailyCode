package gldailycode.dfs_bfs;

public class Leetcode_200 {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);     // 淹没整个岛屿
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j) {
        // 越界 或 已访问（水 / 已淹没）→ 终止
        if (i < 0 || j < 0
            || i >= grid.length || j >= grid[0].length
            || grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '0';      // 淹没当前位置，标记已访问

        dfs(grid, i - 1, j);   // 上
        dfs(grid, i + 1, j);   // 下
        dfs(grid, i, j - 1);   // 左
        dfs(grid, i, j + 1);   // 右
    }
}
