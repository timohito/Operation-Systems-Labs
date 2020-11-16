package com.company;

public class PageTable {
	private final Page[] pageTable;
    private final int maxPages;
    

    public PageTable(int maxPages) {
        this.maxPages = maxPages;
        pageTable = new Page[maxPages];
    }

    public int getMaxPages() {
        return maxPages;
    }

    public Page[] getPageTable() {
        return pageTable;
    }
    
    public boolean isReadyForSwapping() {
    	int percent = pageTable.length / 10 * 8;
    	
    	if(pageTable[percent] == null) {
    		return false;
    	}
    	
    	return true;
    }
}
