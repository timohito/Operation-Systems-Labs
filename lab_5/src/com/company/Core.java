package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Core {
    private final ArrayList<Process> processes = new ArrayList<>();

    private int cycle;
    private int timeWithBlocks;
    private boolean end;
    private int timeWithoutBlocks;

    private final int qTime = 20;
    private final UsingInputOutput usingInputOutput;
    private final ArrayList<PropertiesInputOutput> dataProcessesWithUsingInputOutput;

    Core() {
        usingInputOutput = new UsingInputOutput();
        dataProcessesWithUsingInputOutput = usingInputOutput.getDataProcessesWithUsingInputOutput();
        int numOfProcesses = getRandomNumber(5, 10);
        System.out.println(numOfProcesses + " процессов требуется выполнить");
        createProcesses(numOfProcesses);
        System.out.println("Работа без блокировок");
        planWithNoBlock();
        end = false;
        cycle = 1;
        timeWithBlocks = 0;
        System.out.println("Работа с блокировками");
        planWithBlock();
    }

    void createProcesses(int numOfProcesses) {
        for (int i = 0; i < numOfProcesses; i++) {
            boolean isUsingInputOutput = getRandomNumber(0, 1) == 0;
            int timeProcess = getRandomNumber(20, 100);
            int ID = processes.size();
            Process process;
            if (isUsingInputOutput) {
                int timeUsingIO = getRandomNumber(20, 100);
                dataProcessesWithUsingInputOutput.add(new PropertiesInputOutput(timeUsingIO, getRandomNumber(0, timeProcess - 1)));
            }
            process = new Process(ID, timeProcess);
            System.out.println("Процесс " + ID + " создан. Время: " + timeProcess + " Использование Input/Output: " + isUsingInputOutput);
            processes.add(process);
        }
    }

    void planWithNoBlock() {
        ArrayList<Process> processes = new ArrayList<>(this.processes.size());
        for (Process item : this.processes)
            processes.add(item.clone());
        ArrayList<PropertiesInputOutput> dataProcessesWithUsingIO = new ArrayList<>();
        for (int i = 0; i < this.dataProcessesWithUsingInputOutput.size(); i++) {
            dataProcessesWithUsingIO.add(i, this.dataProcessesWithUsingInputOutput.get(i));
        }

        while (!end) {
            end = true;
            System.out.println(cycle + "-й цикл");
            for (Process process : processes) {
                if (process.isWorking()) {
                    process.launch(qTime);
                    timeWithBlocks += qTime;
                    if (dataProcessesWithUsingIO.get(process.getId()) != null) {
                        PropertiesInputOutput propertiesInputOutput = dataProcessesWithUsingIO.get(process.getId());
                        while (propertiesInputOutput.startUsing) {
                            process.launchUsingInputOutput(qTime, propertiesInputOutput);
                            timeWithBlocks += qTime;
                        }
                    }
                    end = false;
                }
            }
            cycle++;
        }
        System.out.println("Полное время работы без блокировок: " + timeWithBlocks);
        timeWithoutBlocks = timeWithBlocks;
    }


    private void planWithBlock() {
        ArrayList<Process> processesBlocked = usingInputOutput.getProcsBlocked();
        while (!end) {
            end = true;
            System.out.println(cycle + "-й цикл");
            for (int k = 0; k < processes.size(); k++) {
                Process process = processes.get(k);
                if (process.isWorking()) {
                    for (int i = 0; i < qTime; i++) {
                        Process readyProcess = usingInputOutput.checkBlockedProcesses();
                        if (readyProcess != null) {
                            process = readyProcess;
                        }
                        if (!processesBlocked.contains(process)) {
                            if (dataProcessesWithUsingInputOutput.get(process.getId()) != null) {
                                process.launchWithBlockAndInputOutputChecking(processesBlocked, dataProcessesWithUsingInputOutput.get(process.getId()));
                            } else {
                                process.launchWithoutBlockChecking();
                            }
                            timeWithBlocks++;
                        } else {
                            System.out.println("Р " + process.id + " заблокирован");
                            if (processes.size() > k + 1) {
                                process = processes.get(k + 1);
                            } else {
                                break;
                            }
                        }
                    }
                    if (process.timeProcess != 0) {
                        System.out.println("Р: " + process.id);
                        System.out.println("\tОсталось " + process.timeProcess);
                    }
                    end = false;
                }
            }
            cycle++;
        }
        System.out.println("Время работы с блокировками: " + timeWithBlocks);
        System.out.println("Время работы без блокировок: " + timeWithoutBlocks);
    }
    private int getRandomNumber(int min, int max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}