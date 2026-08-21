package gldailycode.link_list;

import gldailycode.common.ListNode;

public class Leetcode_206 {
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode preNode = null;
        ListNode curNode = head;
        while (curNode != null) {
            ListNode tempNode = curNode.next;
            curNode.next = preNode;

            preNode = curNode;
            curNode = tempNode;
        }

        return preNode;
    }
}
