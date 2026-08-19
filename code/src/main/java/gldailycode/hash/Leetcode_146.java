package gldailycode.hash;

import java.util.HashMap;

public class Leetcode_146 {
}
class Node {
    public int key = 0;
    public int value = 0;
    public Node nextNode = null;
     public Node preNode = null;
     public Node(int key, int value) {
        this.key = key;
        this.value = value;
     }

}
class LRUCache {

    public HashMap<Integer, Node> hashMap = null;
    public Node headNode = null;
    public Node lastNode = null;
    public int capacity = 0;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.hashMap = new HashMap<>();
        this.headNode = new Node(0,0);
        this.lastNode = new Node(0,0);
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
        curNode.preNode.nextNode = curNode.nextNode;
        curNode.nextNode.preNode = curNode.preNode;

        curNode.nextNode = headNode.nextNode;
        curNode.nextNode.preNode = curNode;
        curNode.preNode = headNode;
        headNode.nextNode = curNode;
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

        Node curNode = new Node(key, value);
        curNode.preNode = lastNode.preNode;
        curNode.nextNode = lastNode;
        lastNode.preNode.nextNode = curNode;
        lastNode.preNode = curNode;
        hashMap.put(key, curNode);
        this.moveToHead(curNode);
    }

    public void removeLast() {
        if (hashMap.isEmpty()) {
            return;
        }

        Node preNode = lastNode.preNode;
        preNode.preNode.nextNode = lastNode;
        lastNode.preNode = preNode.preNode;
        hashMap.remove(preNode.key);
    }
}
