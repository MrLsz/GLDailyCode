package gldailycode.array;

public class Leetcode_283 {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int slowIndex = 0;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] != 0) {
                nums[slowIndex] = nums[index];

                if (slowIndex != index) {
                    nums[index] = 0;
                }

                slowIndex++;
            }
        }
    }
}
