package gldailycode.prefix_sum;

public class Leetcode_303 {
    private int[] prefix;

    public Leetcode_303(int[] nums) {
        prefix = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            prefix[i] = sum;
        }
    }

    public int sumRange(int left, int right) {
        return prefix[right] - (left > 0 ? prefix[left - 1] : 0);
        //                          ↑ 关键：减的是 left-1，不是 left
    }
}
