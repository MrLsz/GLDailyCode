package gldailycode.dp;

public class Leetcode_5 {
    public String longestPalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }

        // dp[i][j] 表示到s从[i, j]的子串，是否是回文子串
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


     public String longestPalindrome1(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }

        // dp[i][j] 表示从[i - j]的字符串是否是回文字符串
        boolean[][] dp = new boolean[len][len];
        int start = 0;
        int maxLength = 1;
        for (int index = 0; index < len; index++) {
            dp[index][index] = true;
        }

        for (int L = 2; L <= len; L++) {
            for (int i = 0; i < len; i++) {
                int j = i + L - 1;
                if (j >= s.length()) {
                    break;
                }

                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                if (dp[i][j] && j - i + 1 > maxLength) {
                    maxLength = j - i + 1;
                    start = i;
                }
            }
        }

        return s.substring(start, start + maxLength);
    }
}
