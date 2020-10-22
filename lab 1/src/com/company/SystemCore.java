package com.company;

import java.util.HashMap;

public class SystemCore {

    private final int amountOfSystemCalls = 5;
    private HashMap<Integer, SystemCall> systemCallPatterns = new HashMap<>();
    private Stack stack;


    public SystemCore(Stack stack) {
        systemCallPatterns.put(0, new SystemCall(0, new Object[]{1, ""}, "����� ����� � ������"));
        systemCallPatterns.put(1, new SystemCall(1, new Object[]{"", 1.5}, "������ � �����"));
        systemCallPatterns.put(2, new SystemCall(2, new Object[]{"", "", ""}, "3 ������"));
        systemCallPatterns.put(3, new SystemCall(3, new Object[]{9.0, 1.5}, "��� �����"));
        systemCallPatterns.put(4, new SystemCall(4, new Object[]{'k', 1.5}, "������ � �����"));
    	


        this.stack = stack;
    }

    public void showDescriptions() {
        for (int i = 0; i < amountOfSystemCalls; i++) {
            SystemCall call = systemCallPatterns.get(i);
            System.out.println("ID: " + call.getID() + ", ������� ���������: " + call.getDescription());
        }
    }

    public void call(int ID) {
        if (!systemCallPatterns.containsKey(ID)) {
            System.out.println("��� ���������� ������ � ����� ID " + ID);
            System.out.println();
            return;
        }
        Object[] args = new Object[systemCallPatterns.get(ID).getArgs().length];
        for (int i = 0; i < args.length; i++) {
            args[i] = stack.pop();
            if (args[i] == null) {
                System.out.println("�������� ���� ���������� � ����� ID " + ID);
                System.out.println();
                return;
            }
        }
        SystemCall systemCall = new SystemCall(ID, args);
        for (int i = 0; i < systemCallPatterns.get(systemCall.getID()).getArgs().length; i++) {
            if (systemCall.getArgs()[i].getClass() != systemCallPatterns.get(systemCall.getID()).getArgs()[i].getClass()) {
                System.out.println("���������� ������ �� ID " + systemCall.getID() + " ���� �������� �� �� ���������");
                System.out.println();
                return;
            }
        }
        System.out.println("���������� ��������� ����� �� ID " + systemCall.getID());
        System.out.print("��� ���������: ");
        for (int i = 0; i < systemCall.getArgs().length; i++) {
            System.out.print(systemCall.getArgs()[i] + " ");
        }
        System.out.println();
        System.out.println();
    }
}
