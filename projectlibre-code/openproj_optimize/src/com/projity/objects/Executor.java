package com.projity.objects;

import java.util.HashMap;

public class Executor {
    String name;
    private HashMap<Work, Double> timesForWork = new HashMap<>();
    private double rate;
    private ExecutorConditions executorConditions;

    public Executor(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }
    public void setWork(Work work, double time) {
        timesForWork.put(work, time);
    }
    public double getTimeOfWork(Work work) {
        return timesForWork.get(work);
    }
    public double getRate() {
        return rate;
    }

    public ExecutorConditions getExecutorConditions() {
        return executorConditions;
    }

    public void setExecutorConditions(ExecutorConditions executorConditions) {
        this.executorConditions = executorConditions;
    }
}
