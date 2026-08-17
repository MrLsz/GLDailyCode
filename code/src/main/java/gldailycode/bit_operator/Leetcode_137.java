package gldailycode.bit_operator;

public class Leetcode_137 {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int bitCount = 0;
            for (int num : nums) {
                bitCount += (num >> i) & 1;
            }
            // 该位上 1 的个数不是 3 的倍数 → 目标数字该位为 1
            if (bitCount % 3 != 0) {
                result |= (1 << i);
            }
        }
        return result;
    }
}
