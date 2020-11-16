package com.company;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class MemoryManager {
    private final PageTable physMemory;
    private static final int memoryCap = 256;
    private static final int pageCap = 32;
    private final LinkedList<Process> processList = new LinkedList<>();
    private Random rand = new Random();
    private HDD hdd = new HDD();
    

    public void routine() {
        for (int loop = 0; loop < 30; loop++) {
            for (Process process : processList) {
                if(physMemory.isReadyForSwapping()){
                    swapping(process);
                    continue;
                }
                int index = rand.nextInt(process.getVirtualMemory().size());
                Page currentPage = process.getVirtualMemory().get(index);
                int action = rand.nextInt(2);
                Page[] pmem = physMemory.getPageTable();
                if (currentPage.isInPhysMemory()) {
                    if (action == 0) { //обращение
                        currentPage.setRef(1);
                        System.out.println("Страница: " + currentPage.getID() + " Процесс: " + process.getID() +
                                " Физ. память: " + currentPage.getPhysPageID() + " Обращение");
                    } else { //модификация
                        currentPage.setMod(1);
                        System.out.println("Страница: " + currentPage.getID() + " Процесс: " + process.getID() +
                                " Физ. память: " + currentPage.getPhysPageID() + " Модификация");
                    }
                } 
                	
                
                	
                 else if (pmem[physMemory.getMaxPages() - 1] == null) {
                    for (int i = 0; i < physMemory.getMaxPages(); i++) {
                        if (pmem[i] == null) {
                            currentPage.setInPhysMemory(true);
                            currentPage.setPhysPageID(i);
                            pmem[i] = currentPage;
                            System.out.println("Страница: " + currentPage.getID() + " Процесс: " + process.getID() + " теперь находится в физической памяти: " + i);
                            break;
                        }
                    }
                } 
                 
                 else {                	 
                	 if(hdd.isInHDD(currentPage) != null) 
                     {
                    	 currentPage = hdd.isInHDD(currentPage);
                     }
                    System.out.println("\nВыполняется страничное прерывание");
                    Arrays.sort(pmem);
                    for (Page page : pmem) {
                        System.out.println(" Физ. память: " + page.getPhysPageID() + " Процесс: " + page.getProcessID() + " Страница: " + currentPage.getID() + " Класс: " + (page.getRef() * 2 + page.getMod()));
                    }
                    System.out.println("Выгрузка страницы " + pmem[0].getPhysPageID());
                    currentPage.setInPhysMemory(true);
                    currentPage.setRef(1);
                    currentPage.setPhysPageID(pmem[0].getPhysPageID());
                    pmem[0].setInPhysMemory(false);
                    pmem[0] = currentPage;
                    System.out.println("Загрузка страницы " + currentPage.getID() + " процесса " + process.getID());
                    System.out.println("Страничное прерывание завершено");
                }
            }
            System.out.println("\nСнижение приоритета всех страниц\n");
            for (Page page : physMemory.getPageTable()) {
                if (page != null) {
                    page.setRef(0);
                    page.setMod(0);
                }
            }
        }
    }

    public void swapping(Process process) {
        System.out.println("Выполенение свопинга...");
        Page[] pm = physMemory.getPageTable();
        for (int i = 0; i < process.getVirtualMemory().size(); i++) {
            Page badPage = pm[i];
            if (badPage != null) {
            	System.out.println("Перенос на диск");
            	hdd.add(badPage);            
            pm[i] = process.getVirtualMemory().get(i);
            System.out.println("Добавление страницы " + pm[i].getID() + " процесса " + pm[i].getProcessID());
            }
        }
        
    }

    MemoryManager() {
        physMemory = new PageTable(memoryCap / pageCap);
        for (int i = 0; i < rand.nextInt(8) + 5; i++) {
            processList.add(new Process(i));
        }
    }
    
   
}
