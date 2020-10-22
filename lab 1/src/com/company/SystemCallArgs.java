package com.company;

public class SystemCallArgs {
    
    private int ID;
    private Object[] args;

    public SystemCallArgs(int ID, Object[] args) {
        this.ID = ID;
        this.args = args;
    }

    public int getID(){ return ID; }

//    public void setIndex(int ID){ this.ID = ID; }

    public Object[] getArgs(){ return args; }
}