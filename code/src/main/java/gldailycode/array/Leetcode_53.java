package gldailycode.array;

public class Leetcode_53 {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int result = Integer.MIN_VALUE;
        int tempTotalCount = 0;
        for (int index = 0; index < nums.length; index++) {
            tempTotalCount += nums[index];
            result = Math.max(result, tempTotalCount);
            if (tempTotalCount < 0) {
                tempTotalCount = 0;
            }
        }

        return result;
    }
}
