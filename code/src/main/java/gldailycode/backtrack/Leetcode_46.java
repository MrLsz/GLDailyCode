package gldailycode.backtrack;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_46 {
    private List<List<Integer>> resultList = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) {
            return resultList;
        }

        traversal(nums, 0, new int[nums.length], new ArrayList<>());
        return resultList;
    }

    private void traversal(int[] nums, int currentIndex,
                           int[] usedFlags, List<Integer> pathList) {
        // 终止条件：路径长度 == 数组长度，找到一个完整排列
        if (pathList.size() == nums.length) {
            resultList.add(new ArrayList<>(pathList)); // 深拷贝
            return;
        }

        // 遍历选择列表，每次都从 0 开始（排列需考虑所有未使用元素）
        for (int index = 0; index < nums.length; index++) {
            if (usedFlags[index] == 1) {
                continue;   // 已使用，跳过
            }

            // 做选择
            pathList.add(nums[index]);
            usedFlags[index] = 1;

            // 递归进入下一层
            traversal(nums, index + 1, usedFlags, pathList);

            // 撤销选择（回溯核心）
            pathList.remove(pathList.size() - 1);
            usedFlags[index] = 0;
        }
    }
}
