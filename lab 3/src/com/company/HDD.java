package com.company;

import java.util.LinkedList;

public class HDD {
	private LinkedList<Page> swappedPages = new LinkedList<>();
	
	public void add(Page page) {
		swappedPages.add(page);
	}
	
	public Page isInHDD(Page page) {
		for(int i = 0; i < swappedPages.size(); i++) {
			if(page.getProcessID() == swappedPages.get(i).getProcessID() && page.getID() == swappedPages.get(i).getID()) {
				return swappedPages.get(i);
			}
		}
		return null;
	}
	
}
