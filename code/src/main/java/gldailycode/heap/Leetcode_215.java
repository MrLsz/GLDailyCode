package gldailycode.heap;

import java.util.PriorityQueue;

public class Leetcode_215 {
    public int findKthLargest(int[] nums, int k) {
        // 小顶堆——堆顶始终是堆中最小的
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();     // 弹出最小值，保持堆大小为 k
            }
        }
        return minHeap.peek();      // 堆中剩 k 个最大元素，堆顶即第 k 大
    }
}
