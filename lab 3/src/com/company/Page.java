package com.company;

public class Page implements Comparable<Page> {
    private final int ID;
    private boolean isInPhysMemory;
    private int physicalPageID;
    private final int processID;
    private int reference = 0;
    private int modification = 0;

    Page(int ID, int processID){
        this.ID = ID;
        this.processID = processID;
    }

    public int getMod(){
        return modification;
    }

    public void setMod(int mod) {
        modification = mod;
    }

    public int getRef(){
        return reference;
    }

    public void setRef(int ref) {
        reference = ref;
    }

    public int getPhysPageID() {
        return physicalPageID;
    }

    public void setPhysPageID(int physPageID) {
        this.physicalPageID = physPageID;
    }

    public boolean isInPhysMemory() {
        return isInPhysMemory;
    }

    public void setInPhysMemory(boolean inPhysMemory) {
        isInPhysMemory = inPhysMemory;
    }

    @Override
    public int compareTo(Page p) {
        if(reference * 2 + modification > p.getRef() * 2 + p.getMod()){
            return 1;
        }
        else if (reference * 2 + modification < p.getRef() * 2 + p.getMod()){
            return -1;
        }
        return 0;
    }
    
    public int getID() {
        return ID;
    }

    public int getProcessID() {
        return processID;
    }
}
