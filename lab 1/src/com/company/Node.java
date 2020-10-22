package com.company;

public class Node {

    private Node nextNode;
    private int index;
    private Object[] object;

    public Node(int index, Object[] object) {
        this.index = index;
        this.object = object;
    }

    public Node getNextNode(){ return nextNode; }

    public void setNextNode(Node nextNode){ this.nextNode = nextNode; }

    public int getIndex(){ return index; }

    public void setId(int id){ this.index = id; }

    public Object[] getObject(){ return object; }
}