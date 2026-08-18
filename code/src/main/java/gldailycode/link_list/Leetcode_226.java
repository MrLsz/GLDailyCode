package gldailycode.link_list;

import gldailycode.common.TreeNode;

public class Leetcode_226 {
    public TreeNode invertTree(TreeNode root) {
        traversal(root);
        return root;
    }

    public void traversal(TreeNode node) {
        if (node == null) {
            return;
        }

        TreeNode tempNode = node.left;
        node.left = node.right;
        node.right = tempNode;
        traversal(node.left);
        traversal(node.right);
    }
}
