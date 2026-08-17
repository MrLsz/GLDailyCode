package gldailycode.double_point;

public class Leetcode_11 {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int w = right - left;                        // 宽度
            int h = Math.min(height[left], height[right]); // 高度
            maxArea = Math.max(maxArea, w * h);

            // 移动较短的柱：期望找到更高的来增大面积
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
