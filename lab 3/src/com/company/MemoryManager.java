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
    

    public void routine() {
        for (int loop = 0; loop < 30; loop++) {
            for (Process process : processList) {
                int index = rand.nextInt(process.getVirtualMemory().size());
                Page currentPage = process.getVirtualMemory().get(index);
                int action = rand.nextInt(2);
                Page[] pmem = physMemory.getPageTable();
                if (currentPage.isInPhysMemory()) {
                    if (action == 0) { //���������
                        currentPage.setRef(1);
                        System.out.println("��������: " + currentPage.getID() + " �������: " + process.getID() +
                                " ���. ������: " + currentPage.getPhysPageID() + " ���������");
                    } else { //�����������
                        currentPage.setMod(1);
                        System.out.println("��������: " + currentPage.getID() + " �������: " + process.getID() +
                                " ���. ������: " + currentPage.getPhysPageID() + " �����������");
                    }
                } else if (pmem[physMemory.getMaxPages() - 1] == null) {
                    for (int i = 0; i < physMemory.getMaxPages(); i++) {
                        if (pmem[i] == null) {
                            currentPage.setInPhysMemory(true);
                            currentPage.setPhysPageID(i);
                            pmem[i] = currentPage;
                            System.out.println("��������: " + currentPage.getID() + " �������: " + process.getID() + " ������ ��������� � ���������� ������: " + i);
                            break;
                        }
                    }
                } else {
                    System.out.println("\n����������� ���������� ����������");
                    Arrays.sort(pmem);
                    for (Page page : pmem) {
                        System.out.println(" ���. ������: " + page.getPhysPageID() + " �������: " + page.getProcessID() + " ��������: " + currentPage.getID() + " �����: " + (page.getRef() * 2 + page.getMod()));
                    }
                    System.out.println("�������� �������� " + pmem[0].getPhysPageID());
                    currentPage.setInPhysMemory(true);
                    currentPage.setRef(1);
                    currentPage.setPhysPageID(pmem[0].getPhysPageID());
                    pmem[0].setInPhysMemory(false);
                    pmem[0] = currentPage;
                    System.out.println("�������� �������� " + currentPage.getID() + " �������� " + process.getID());
                    System.out.println("���������� ���������� ���������");
                }
            }
            System.out.println("\n�������� ���������� ���� �������\n");
            for (Page page : physMemory.getPageTable()) {
                if (page != null) {
                    page.setRef(0);
                    page.setMod(0);
                }
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
