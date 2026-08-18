package gldailycode.backtrack;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_78 {
    private final List<List<Integer>> resultList = new ArrayList<>();
     public List<List<Integer>> subsets(int[] nums) {
        traversal(nums, 0, new ArrayList<>());
        return resultList;
    }

    public void traversal(int[] nums, int step, List<Integer> pathList) {
        resultList.add(new ArrayList<Integer>(pathList));

        for (int index = step; index < nums.length; index++) {
            pathList.add(nums[index]);
            traversal(nums, index + 1, pathList);
            pathList.remove(pathList.size() - 1);
        }
    }
}
