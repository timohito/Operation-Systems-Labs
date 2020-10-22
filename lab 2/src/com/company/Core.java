package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Core {
    private final Random random = new Random();
    private int cycle = 1;
    private final ArrayList<SystemProcess> systemProcesses = new ArrayList<>();
    private final int[] prioritiesTime = {20, 30, 40, 60};
    private ArrayList<Integer> priorities = new ArrayList<>(); 

    void routine() { // приоритетный алгоритм
        while (!systemProcesses.isEmpty()) {
            System.out.println(cycle + "-й цикл");
            for (int i = 0; i < systemProcesses.size(); i++) {
                SystemProcess systemProcess = systemProcesses.get(i);
                while (systemProcess.getPriority() > 0) { // пока приоритет процесса не снизится до 0
                    systemProcess.launch(prioritiesTime[systemProcess.getPriority()]); // запускаем проц, выдаем время по приоритету
                    systemProcess.setPriority(systemProcess.getPriority() - 1); 
                    if (systemProcess.isEmpty()) {
                        systemProcesses.remove(i);
                        i--;
                        break;
                    }
                }
                systemProcess.setPriority(priorities.get(systemProcess.getID()));
                if (!systemProcess.isEmpty()) {
                    systemProcess.launch(prioritiesTime[0]);
                    if (systemProcess.isEmpty()) {
                        systemProcesses.remove(i);
                        i--;
                    }
                }
            }
            cycle++;
        }
    }

    void createProcesses(int numOfProcesses) { 
        for (int i = 0; i < numOfProcesses; i++) {
            int priority = getRandomNumber(0, 3);
            int ID = systemProcesses.size();
            SystemProcess systemProcess = new SystemProcess(ID, priority);           
            priorities.add(priority);  
            System.out.println("Процесс " + ID + " создан. Приоритет: " + priority);
            systemProcesses.add(systemProcess);
        }
    }

    public void launch() {
        int numOfProcesses = 5 + random.nextInt(5); // from 5 to 10
        System.out.println(numOfProcesses + " процессов требуется выполнить");
        createProcesses(numOfProcesses);
        routine();
    }

    private int getRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}