package com.company;

public class SystemCall {

    private int ID;
    private Object[] args;
    private String description;

    public SystemCall(int ID, Object[] args, String description) {
        this.ID = ID;
        this.args = args;
        this.description = description;
    }

    public SystemCall(int ID, Object[] args) {
        this.ID = ID;
        this.args = args;
    }

    public int getID() {
        return ID;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDescription() {
        return description;
    }
}
