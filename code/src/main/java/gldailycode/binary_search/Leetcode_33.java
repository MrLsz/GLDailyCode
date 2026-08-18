package gldailycode.binary_search;

public class Leetcode_33 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int leftIndex = 0;
        int rightIndex = nums.length - 1;
        while (leftIndex <= rightIndex) {
            int midIndex = leftIndex + (rightIndex - leftIndex) / 2;
            if (nums[midIndex] == target) {
                return midIndex;
            }

            // 左半边有序
            if (nums[midIndex] >= nums[leftIndex]) {
                if (target >= nums[leftIndex] && target < nums[midIndex]) {
                    rightIndex = midIndex - 1;
                } else {
                    leftIndex = midIndex + 1;
                }
            } else {
                // 右半边有序
                if (target > nums[midIndex] && target <= nums[rightIndex]) {
                    leftIndex = midIndex + 1;
                } else {
                    rightIndex = midIndex - 1;
                }
            }
        }

        return -1;
    }
}
