package gldailycode.hash;

import java.util.HashMap;
import java.util.Map;

public class Leetcode_1 {
    public int[] twoSum(int[] nums, int target) {
        // key = 数组值, value = 下标
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            // 先查：complement 是否已经在之前的元素中出现过
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }

            // 后存：当前值记录到 map
            map.put(nums[i], i);
        }

        return new int[0]; // 题目保证有答案，不会走到这里
    }
}
