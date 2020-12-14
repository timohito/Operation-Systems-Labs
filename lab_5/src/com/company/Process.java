package com.company;

import java.util.ArrayList;

public class Process implements Cloneable {
    protected final int id;
    protected int timeProcess;
    protected boolean ready = false;

    Process(int id, int timeProcess) {
        this.id = id;
        this.timeProcess = timeProcess;
    }

    void launch(int time) {
        if (timeProcess <= time) {
            ready = true;
            System.out.println("Процесс " + id + " завершён");
            return;
        }
        timeProcess -= time;
        System.out.println("Р: " + id);
        System.out.println("\tОсталось " + timeProcess);
    }

    public void launchWithoutBlockChecking() {
        if (timeProcess == 0) {
            return;
        }
        timeProcess--;
        if (timeProcess == 0) {
            ready = true;
            System.out.println("Процесс " + id + " завершён");
        }
    }

    public void launchWithBlockAndInputOutputChecking(ArrayList<Process> procsBlocked, Core propertiesInputOutput) {
        if (timeProcess == 0) {
            return;
        }
        if (propertiesInputOutput.timePointWhenUsingIO == timeProcess && propertiesInputOutput.timeUsingIO != 0) {
            propertiesInputOutput.startUsing = true;
            procsBlocked.add(this);
            System.out.println("P: " + id + "\n\tВыполняется взаимодействие с устройством I/O");
            System.out.println("\tБлокировка на " + propertiesInputOutput.timeUsingIO);
            return;
        }
        timeProcess--;
        if (timeProcess == 0) {
            ready = true;
            System.out.println("Процесс " + id + " завершён");
        }
    }

    public void launchUsingInputOutput(int time, Core propertiesInputOutput) {
        for (; time > 0; time--) {
            if (propertiesInputOutput.timePointWhenUsingIO == timeProcess) {
                propertiesInputOutput.startUsing = true;
                if (time <= propertiesInputOutput.timeUsingIO) {
                    propertiesInputOutput.timeUsingIO -= time;
                    System.out.println("P: " + id + "\n\tВыполняется взаимодействие с устройством I/O в течение: " + time);
                    System.out.println("\tОсталось до ответа " + propertiesInputOutput.timeUsingIO);
                    return;
                }
                time -= propertiesInputOutput.timeUsingIO;
                System.out.println("P: " + id + "\n\tВыполняется взаимодействие с устройством I/O в течение: " + propertiesInputOutput.timeUsingIO);
                propertiesInputOutput.timeUsingIO = 0;
                propertiesInputOutput.startUsing = false;
                System.out.println("\tВзаимодействие завершено!");
            }
            timeProcess--;
            if (timeProcess == 0) {
                ready = true;
                System.out.println("Процесс " + id + " завершён");
                return;
            }
        }
        System.out.println("Р: " + id);
        System.out.println("\tОсталось " + timeProcess);
    }

    public boolean isWorking() {
        return !ready;
    }

    public int getId() {
        return id;
    }

    @Override
    public Process clone() {
        return new Process(id, timeProcess);
    }
}