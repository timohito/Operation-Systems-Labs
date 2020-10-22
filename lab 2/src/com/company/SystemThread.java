package com.company;

public class SystemThread {
	private final int tid;
    private final int wholeTime;
    private int maxTime;

    SystemThread(int tid, int maxTime){
        this.wholeTime = maxTime;
        this.maxTime = maxTime;
        this.tid = tid;
    }

    public int getTID() {
        return tid;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void launch(int pid){
        System.out.println("����� "+ tid + " �������� " + pid + " ���������� ������� �� ����� "+ maxTime);
    }

    public void decreaseMaxTime(int time) {
        maxTime -= time;
    }

    public int getWholeTime() {
        return wholeTime;
    }
}
