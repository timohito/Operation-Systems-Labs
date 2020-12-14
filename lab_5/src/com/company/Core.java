package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Core {
    private final ArrayList<Process> processes = new ArrayList<>();

    private int cycle;
    private int timeWithBlocks;
    private boolean end;
    private int timeWithoutBlocks;
    public int timeUsingIO;
    public int timePointWhenUsingIO;
    public boolean startUsing = false;

    private final int qTime = 20;
    private ArrayList<Core> dataProcessesWithUsingInputOutput = new ArrayList<>();

    Core() {
        dataProcessesWithUsingInputOutput = this.getDataProcessesWithUsingInputOutput();
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

    Core(int timeUsingInputOutput, int timePointWhenUsingIO){
        this.timeUsingIO = timeUsingInputOutput;
        this.timePointWhenUsingIO = timePointWhenUsingIO;
    }

    Core(int timeUsingInputOutput, int timePointWhenUsingIO, boolean startUsing){
        this.timeUsingIO = timeUsingInputOutput;
        this.timePointWhenUsingIO = timePointWhenUsingIO;
        this.startUsing = startUsing;
    }

    void createProcesses(int numOfProcesses) {
        for (int i = 0; i < numOfProcesses; i++) {
            boolean isUsingInputOutput = getRandomNumber(0, 1) == 0;
            int timeProcess = getRandomNumber(20, 100);
            int ID = processes.size();
            Process process;
            if (isUsingInputOutput) {
                int timeUsingIO = getRandomNumber(20, 100);
                dataProcessesWithUsingInputOutput.add(new Core(timeUsingIO, getRandomNumber(0, timeProcess - 1)));
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
        ArrayList<Core> dataProcessesWithUsingIO = new ArrayList<>();
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
                        Core propertiesInputOutput = dataProcessesWithUsingIO.get(process.getId());
                        while (this.startUsing) {
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
        ArrayList<Process> processesBlocked = this.getProcsBlocked();
        while (!end) {
            end = true;
            System.out.println(cycle + "-й цикл");
            for (int k = 0; k < processes.size(); k++) {
                Process process = processes.get(k);
                if (process.isWorking()) {
                    for (int i = 0; i < qTime; i++) {
                        Process readyProcess = this.checkBlockedProcesses();
                        if (readyProcess != null) {
                            process = readyProcess;
                        }
                        if (!processesBlocked.contains(process)) {
                            if (dataProcessesWithUsingInputOutput.get(process.getId()) != null) {
                                process.launchWithBlockAndInputOutputChecking(processesBlocked, this.dataProcessesWithUsingInputOutput.get(process.getId()));
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

    private final ArrayList<Process> procsBlocked = new ArrayList<>();
    //private final ArrayList<PropertiesInputOutput> dataProcessesWithUsingInputOutput = new ArrayList<>();

    public Process checkBlockedProcesses(){
        Process readyProc = null;
        for (int j = 0; j < procsBlocked.size(); j++) {
            Process p = procsBlocked.get(j);
            if (dataProcessesWithUsingInputOutput.get(p.getId()).startUsing) {
                decreaseTimeUsingInputOutput(p.getId());
            } else {
                procsBlocked.remove(p);
                j--;
                System.out.println("Прерывание: " + p.id + " вернулся в работу");
                readyProc = p;
            }
        }
        return readyProc;
    }

    public void decreaseTimeUsingInputOutput(int ID){
        Core propertiesInputOutput = dataProcessesWithUsingInputOutput.get(ID);
        propertiesInputOutput.timeUsingIO--;
        if(propertiesInputOutput.timeUsingIO == 0){
            propertiesInputOutput.startUsing = false;
        }
    }

    public ArrayList<Process> getProcsBlocked() {
        return procsBlocked;
    }

    public ArrayList<Core> getDataProcessesWithUsingInputOutput() {
        return dataProcessesWithUsingInputOutput;
    }

    public Core clone() {
        return new Core(timeUsingIO, timePointWhenUsingIO, startUsing);
    }
}