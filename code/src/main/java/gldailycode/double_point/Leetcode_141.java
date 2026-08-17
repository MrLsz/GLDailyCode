package gldailycode.double_point;

import gldailycode.common.ListNode;

public class Leetcode_141 {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;         // 慢指针走 1 步
            fast = fast.next.next;    // 快指针走 2 步

            if (slow == fast) {
                return true;          // 相遇 → 有环
            }
        }
        return false;                 // fast 到 null → 无环
    }
}
