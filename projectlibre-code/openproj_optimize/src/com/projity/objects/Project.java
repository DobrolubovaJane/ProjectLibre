package com.projity.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Project {
    private HashMap<Work, Executor> executorOfWork = new HashMap<>();
    private double newTime;
    private int numberOfWorks;
    private double maxCash;
    private Work firstWork;


    public Project(Work firstWork, HashMap<Work, Executor> map, double newTime, double maxCash) {
        this.firstWork = firstWork;
        this.executorOfWork = map;
        this.newTime = newTime;
        this.numberOfWorks = this.executorOfWork.size();
        this.maxCash = maxCash;
    }

    public Executor getExecutorOfWork(Work work) {
        return executorOfWork.get(work);
    }
    public int getNumberOfWorks() {
        return numberOfWorks;
    }
    public double getNewTime() {
        return newTime;
    }
    public double getMaxCash() {
        return maxCash;
    }
    public List<Work> getListOfWorks() {
        ArrayList<Work> works = new ArrayList<>();
         for (HashMap.Entry pair : executorOfWork.entrySet()) {
            works.add((Work)pair.getKey());
        }
        return works;
    }
    public Work getFirstWork() {
        return firstWork;
    }

}
