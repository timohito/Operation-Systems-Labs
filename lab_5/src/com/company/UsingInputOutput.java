package com.company;

import java.util.ArrayList;

public class UsingInputOutput {
    private final ArrayList<Process> procsBlocked = new ArrayList<>();
    private final ArrayList<PropertiesInputOutput> dataProcessesWithUsingInputOutput = new ArrayList<>();

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
        PropertiesInputOutput propertiesInputOutput = dataProcessesWithUsingInputOutput.get(ID);
        propertiesInputOutput.timeUsingIO--;
        if(propertiesInputOutput.timeUsingIO == 0){
            propertiesInputOutput.startUsing = false;
        }
    }

    public ArrayList<Process> getProcsBlocked() {
        return procsBlocked;
    }

    public ArrayList<PropertiesInputOutput> getDataProcessesWithUsingInputOutput() {
        return dataProcessesWithUsingInputOutput;
    }
}
