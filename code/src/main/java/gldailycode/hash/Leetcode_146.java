package gldailycode.hash;

import java.util.HashMap;

public class Leetcode_146 {
    private HashMap<Integer, Node> hashMap;
    private Node headNode;   // 虚拟头节点
    private Node lastNode;   // 虚拟尾节点
    private int capacity;

    public Leetcode_146(int capacity) {
        this.capacity = capacity;
        this.hashMap = new HashMap<>();
        this.headNode = new Node(0, 0);
        this.lastNode = new Node(0, 0);
        headNode.nextNode = lastNode;
        lastNode.preNode = headNode;
    }

    public int get(int key) {
        if (!hashMap.containsKey(key)) {
            return -1;
        }

        Node curNode = hashMap.get(key);
        this.moveToHead(curNode);
        return curNode.value;
    }

    public void moveToHead(Node curNode) {
        // 1. 从原位置摘除
        curNode.preNode.nextNode = curNode.nextNode;
        curNode.nextNode.preNode = curNode.preNode;

        // 2. 插入头部
        curNode.nextNode = headNode.nextNode;
        curNode.nextNode.preNode = curNode;   // 修复：原首节点的 preNode 指向 curNode
        headNode.nextNode = curNode;
        curNode.preNode = headNode;
    }

    public void put(int key, int value) {
        if (this.capacity <= 0) {
            return;
        }

        if (hashMap.containsKey(key)) {
            Node curNode = hashMap.get(key);
            curNode.value = value;
            this.moveToHead(curNode);
            return;
        }

        if (hashMap.size() == this.capacity) {
            this.removeLast();
        }

        // 新节点直接插入头部
        Node curNode = new Node(key, value);
        curNode.nextNode = headNode.nextNode;
        curNode.nextNode.preNode = curNode;
        headNode.nextNode = curNode;
        curNode.preNode = headNode;
        hashMap.put(key, curNode);
    }

    public void removeLast() {
        if (hashMap.isEmpty()) {
            return;
        }

        Node tailNode = lastNode.preNode;   // 最后一个真实节点（最久未使用）
        tailNode.preNode.nextNode = lastNode;
        lastNode.preNode = tailNode.preNode;
        hashMap.remove(tailNode.key);
    }
}

class Node {
    public int key;
    public int value;
    public Node nextNode;
    public Node preNode;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}
