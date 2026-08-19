package gldailycode.string;

import java.util.HashMap;

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
            // 如果有相同的字符，但是比 startIndex 小，直接覆盖并继续
            int lastIndex = hashMap.get(s.charAt(index));
            if (lastIndex < startIndex) {
                hashMap.put(s.charAt(index), index);
                continue;
            }

            // 说明遇到了重复字符，计算最大值，然后更新开始的下标为 lastIndex + 1
            result = Math.max(result, index - startIndex);
            hashMap.put(s.charAt(index), index);
            startIndex = lastIndex + 1;
        }

        // 循环结束，最后一段子串也要参与比较
        result = Math.max(result, s.length() - startIndex);

        return result;
    }
}
