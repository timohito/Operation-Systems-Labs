package com.company;

public class PropertiesInputOutput implements Cloneable {
    public int timeUsingIO;
    public final int timePointWhenUsingIO;
    public boolean startUsing = false;

    PropertiesInputOutput(int timeUsingInputOutput, int timePointWhenUsingIO){
        this.timeUsingIO = timeUsingInputOutput;
        this.timePointWhenUsingIO = timePointWhenUsingIO;
    }

    PropertiesInputOutput(int timeUsingInputOutput, int timePointWhenUsingIO, boolean startUsing){
        this.timeUsingIO = timeUsingInputOutput;
        this.timePointWhenUsingIO = timePointWhenUsingIO;
        this.startUsing = startUsing;
    }

    public PropertiesInputOutput clone() {
        return new PropertiesInputOutput(timeUsingIO, timePointWhenUsingIO, startUsing);
    }
}
