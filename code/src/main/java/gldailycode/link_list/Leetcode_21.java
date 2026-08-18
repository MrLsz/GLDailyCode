package gldailycode.link_list;

import gldailycode.common.ListNode;

public class Leetcode_21 {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }

        if (list2 == null) {
            return list1;
        }

        // 虚拟头节点，简化边界处理
        ListNode newHeadNode = new ListNode(0);

        ListNode rebuildNode = newHeadNode;
        ListNode node1 = list1;
        ListNode node2 = list2;
        while (node1 != null && node2 != null) {
            if (node1.val < node2.val) {
                rebuildNode.next = node1;
                node1 = node1.next;
            } else {
                rebuildNode.next = node2;
                node2 = node2.next;
            }

            rebuildNode = rebuildNode.next;
        }

        // 接上剩余的非空链表
        if (node1 != null) {
            rebuildNode.next = node1;
        } else {
            rebuildNode.next = node2;
        }

        return newHeadNode.next;
    }
}
