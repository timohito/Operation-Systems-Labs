package com.company;

import javax.swing.*;

public class File implements Cloneable{
    private final String name;
    private int size;
    private JTree tree;

    public File(String name, int size){
        this.name = name;
        this.size = size;
    }

    public void setSize(int size){
        this.size = size;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public JTree getFileManagerTree() {
        return tree;
    }

}
