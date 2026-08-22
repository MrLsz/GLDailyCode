# LeetCode 5 - Longest Palindromic Substring（最长回文子串）

| 项目 | 内容 |
|------|------|
| 难度 | Medium |
| 链接 | https://leetcode.cn/problems/longest-palindromic-substring/ |
| 类别 | DP |
| 关联题目 | #647 回文子串, #516 最长回文子序列, #131 分割回文串 |

---

## 题目描述

给你一个字符串 `s`，找到 `s` 中最长的**回文子串**。

### 示例

```
输入：s = "babad"
输出："bab"（"aba" 同样是符合题意的答案）

输入：s = "cbbd"
输出："bb"
```

### 约束

- `1 <= s.length <= 1000`
- `s` 仅由数字和英文字母组成

---

## 解题思路

### 核心思想：区间 DP

定义 `dp[i][j]` 表示子串 `s[i..j]` 是否为回文。

```
1. 初始化：dp[i][i] = 1（单字符必是回文）
2. 按长度 L 从小到大遍历（先算短串，再算长串）
   对每个起点 i，终点 j = i + L - 1：
   - s[i] != s[j]  → dp[i][j] = 0
   - s[i] == s[j]：
     - j - i < 3（长度为 2 或 3）→ dp[i][j] = 1
     - 否则 → dp[i][j] = dp[i+1][j-1]（看内部子串）
3. 每算出一个回文就更新最长长度和起点
```

> **为什么按长度递增**：`dp[i][j]` 依赖 `dp[i+1][j-1]`（更短的内部子串），必须先把短串算完。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n²) — 枚举所有子串 |
| 空间 | O(n²) — dp 数组 |

---

## 代码实现

### Java

```java
public class Leetcode_5 {
    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        // dp[i][j] 表示 s[i..j] 的子串是否是回文
        int[][] dp = new int[s.length()][s.length()];
        for (int index = 0; index < s.length(); index++) {
            dp[index][index] = 1;
        }

        int start = 0;
        int maxLength = 1;
        for (int L = 2; L <= s.length(); L++) {
            for (int i = 0; i < s.length(); i++) {
                int j = L + i - 1;
                if (j >= s.length()) {
                    break;
                }

                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = 0;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                if (dp[i][j] == 1 && j - i + 1 > maxLength) {
                    maxLength = j - i + 1;
                    start = i;
                }
            }
        }

        return s.substring(start, start + maxLength);
    }
}
```

**写法要点**：
- **`j - i < 3` 特判**：长度为 2（两个相同字符 `aa`）或 3（`aba`）时，只要首尾相同就是回文，无需再查内部
- **按长度 L 遍历**：外层 L（长度）、内层 i（起点），保证 `dp[i+1][j-1]` 已先算好
- **`j >= s.length()` 提前 break**：起点 i 越界时终止内层循环
- **动态更新 start/maxLength**：每确认一个回文就尝试刷新最长记录

> 注：`dp` 数组也可用 `boolean[][]` 表示（语义更清晰），代码逻辑完全一致，见文件内的 `longestPalindrome1` 方法。

---

## 关键点总结

1. **区间 DP**：`dp[i][j]` 表示子串是否回文，依赖 `dp[i+1][j-1]`
2. **按长度递增**：短串先算，长串依赖短串结果
3. **长度为 2/3 的特判**：`j - i < 3` 时首尾相同即可判定回文
4. **中心扩展是更优解**：O(n²) 时间 O(1) 空间，比 DP 省空间

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #647 | 回文子串 | 统计回文子串的**个数** |
| #516 | 最长回文子序列 | 子序列（可不连续），不同递推 |
| #131 | 分割回文串 | 回溯枚举所有回文分割 |
