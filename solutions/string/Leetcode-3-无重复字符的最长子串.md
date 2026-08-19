# LeetCode 3 - Longest Substring Without Repeating Characters（无重复字符的最长子串）

| 项目 | 内容 |
|------|------|
| 难度 | Medium |
| 链接 | https://leetcode.cn/problems/longest-substring-without-repeating-characters/ |
| 类别 | 滑动窗口 · 哈希表 |
| 关联题目 | #159 至多包含两个不同字符的最长子串, #76 最小覆盖子串, #567 字符串的排列 |

---

## 题目描述

给定一个字符串 `s`，请你找出其中**不含有重复字符**的最长子串的长度。

### 示例

```
输入: s = "abcabcbb"
输出: 3
解释: 无重复字符的最长子串是 "abc"，长度为 3

输入: s = "bbbbb"
输出: 1

输入: s = "pwwkew"
输出: 3
解释: 最长子串是 "wke"，长度为 3（"pwke" 是子序列，不是子串）
```

### 约束

- `0 <= s.length <= 5 * 10^4`
- `s` 由英文字母、数字、符号和空格组成

---

## 解题思路

### 核心思想：滑动窗口 + HashMap 记录字符最近位置

用 `startIndex` 标记窗口左边界，`index` 遍历作为右边界。HashMap 记录**每个字符最近一次出现的下标**。

遍历时判断当前字符：

```
1. 字符未出现过 → 记录下标，继续
2. 字符出现过，但上次出现位置在 startIndex 左边（窗口外）→ 覆盖为当前下标，继续
3. 字符出现过，且位置在窗口内 → 遇到重复：
   - 结算当前窗口长度 index - startIndex
   - 左边界右移到 lastIndex + 1
```

循环结束后，**最后一段子串**还要再结算一次。

### 复杂度分析

| 维度 | 复杂度 |
|------|--------|
| 时间 | O(n) — 每个字符访问一次 |
| 空间 | O(min(n, 字符集大小)) — HashMap 存字符位置 |

---

## 代码实现

### Java

```java
public class Leetcode_3 {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int startIndex = 0;
        int result = 0;
        HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int index = 0; index < s.length(); index++) {
            // 如果没有相同的字符，继续
            if (!hashMap.containsKey(s.charAt(index))) {
                hashMap.put(s.charAt(index), index);
                continue;
            }
            // 如果出现过，但位置在窗口外，直接覆盖并继续
            int lastIndex = hashMap.get(s.charAt(index));
            if (lastIndex < startIndex) {
                hashMap.put(s.charAt(index), index);
                continue;
            }

            // 遇到窗口内的重复字符：结算窗口长度，左边界右移
            result = Math.max(result, index - startIndex);
            hashMap.put(s.charAt(index), index);
            startIndex = lastIndex + 1;
        }

        // 循环结束，最后一段子串也要参与比较
        result = Math.max(result, s.length() - startIndex);

        return result;
    }
}
```

**写法要点**：
- **窗口边界**：`startIndex` 是左边界，遇到窗口内重复字符时才右移到 `lastIndex + 1`
- **窗口外的重复不算重复**：`lastIndex < startIndex` 说明该字符上次出现已在窗口外，直接覆盖下标即可
- **最后一段要单独结算**：循环结束后 `s.length() - startIndex` 是最后一段无重复子串长度，必须再比一次
- **HashMap 存下标**：key 是字符、value 是最近出现的下标，用于快速判断重复并定位

---

## 关键点总结

1. **滑动窗口**：`startIndex`（左）+ `index`（右）维护无重复子串窗口
2. **HashMap 记录最近位置**：O(1) 判断重复并定位上次位置
3. **窗口外重复忽略**：`lastIndex < startIndex` 的重复不影响窗口，直接覆盖
4. **最后一段别漏**：循环结束后的尾部子串必须参与最终比较

---

## 延伸思考

| 题号 | 题目 | 变化点 |
|------|------|--------|
| #159 | 至多包含两个不同字符的最长子串 | 允许两个不同字符 |
| #567 | 字符串的排列 | 判断是否包含排列，固定窗口 |
| #76 | 最小覆盖子串 | 找覆盖目标的最短窗口 |
