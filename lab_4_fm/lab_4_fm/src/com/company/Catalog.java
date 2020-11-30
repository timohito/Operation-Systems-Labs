package com.company;

import java.util.ArrayList;

public class Catalog extends File{
    private ArrayList<File> child;

    public Catalog(String name) {
        super(name, 1);
    }

    public ArrayList<File> getChild() {
        return child;
    }
}
