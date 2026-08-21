package gldailycode.double_point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();

        Arrays.sort(nums);
        for (int index = 0; index < nums.length; index++) {
            if (index > 0 && nums[index] == nums[index - 1]) {
                continue;
            }

            int leftIndex = index + 1;
            int rightIndex = nums.length - 1;
            while (leftIndex < rightIndex) {
                int sum = nums[leftIndex] + nums[rightIndex] + nums[index];
                if (sum > 0) {
                    rightIndex--;
                } else if (sum < 0) {
                    leftIndex++;
                } else {
                    resultList.add(Arrays.asList(nums[index], nums[leftIndex], nums[rightIndex]));
                    while (leftIndex < rightIndex && nums[rightIndex - 1] == nums[rightIndex]) {
                        rightIndex--;
                    }

                    while (leftIndex < rightIndex && nums[leftIndex] == nums[leftIndex + 1]) {
                        leftIndex++;
                    }
                    rightIndex--;
                    leftIndex++;
                }
            }
        }
        return resultList;
    }
}
