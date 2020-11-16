package com.company;

import java.util.LinkedList;
import java.util.Random;

public class Process {
	private final LinkedList<Page> virtualMemory = new LinkedList<>();
    private final int ID;
    private Random rand = new Random();

    Process(int ID) {
        this.ID = ID;
        int pagesCount = rand.nextInt(4) + 2;
        for (int i = 1; i <= pagesCount; i++) {
            virtualMemory.add(new Page(i, ID));
        }
        System.out.println("В процессе " + ID + " содержится " + pagesCount + " страниц");
    }
  
    public int getID() {
        return ID;
    }
    
    public LinkedList<Page> getVirtualMemory() {
        return virtualMemory;
    }
    
    public Page getPageThroughID(int ID){
    	
		for(int i = 0; i < getPageCount(); i++) {
			if(ID == virtualMemory.get(i).getID()) {
				return virtualMemory.get(i);
			}			
		}
		return null;
    }
    
    public int getPageCount() {
    	return virtualMemory.size();
    }

}
