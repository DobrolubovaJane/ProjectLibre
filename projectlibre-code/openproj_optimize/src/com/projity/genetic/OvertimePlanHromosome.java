package com.projity.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.projity.functions.FunctionWithRate;
import com.projity.objects.Executor;
import com.projity.objects.ExecutorConditions;
import com.projity.objects.Project;
import com.projity.objects.Work;

public class OvertimePlanHromosome {
    private HashMap<Work, Double> cashPlan = new HashMap<>();
    private HashMap<Work, Double> timePlan = new HashMap<>();
    private Project project;
    private List<Work> criticalPath = new ArrayList<>();

    public OvertimePlanHromosome(Project p) {
        this.project = p;
    }

    public OvertimePlanHromosome generateHromosome(FunctionWithRate function, Random rnd) {
    	double allTime = project.getNewTime();
    	double allCash = project.getMaxCash();
        for (Work work : project.getListOfWorks()) {
            Executor executor = project.getExecutorOfWork(work);
            ExecutorConditions executorConditions = executor.getExecutorConditions();
            double random = rnd.nextDouble();
            double cash = 0.0;
            double time = 0.0;
            if (executorConditions == null) {
                cash = random * executor.getRate() * 4;
                time = function.solve(cash, executor.getRate(), executor.getTimeOfWork(work));
            } else {
                Double condition = executorConditions.getConditionOfWork(work);
                if (condition == null) {
                    cash = random * executor.getRate() * 4;
                    time = function.solve(cash, executor.getRate(), executor.getTimeOfWork(work));
                } else {
                    if (condition == 0) {
                        time = executor.getTimeOfWork(work);
                    } else if (condition > 0) {
                        double maxCash = function.solveMaxCash(executor.getRate(), executor.getTimeOfWork(work));
                    	cash = random * maxCash;
                        time = function.solve(cash, executor.getRate(), executor.getTimeOfWork(work));
                    }
                }
            }
            

            cashPlan.put(work, cash);
            timePlan.put(work, time);
            double oldTime = executor.getTimeOfWork(work);
            if (oldTime <= time) {
                editCashPlan(work, 0.0);
            }
        }
        
        setCriticalPath();
        System.out.println("GENERATE cashPlan" + this.getAllCash() + "  timePlan "+ this.getAllTime());
        if (this.getAllTime() > this.getProject().getNewTime() || this.getAllCash() > this.getProject().getMaxCash()) {
        	
        	System.setErr(null);
        }
        return this;
    }
    public Project getProject() {
        return project;
    }

    public HashMap<Work, Double> getCashPlan() {
        return cashPlan;
    }
    public void editCashPlan(Work work, Double cash) {
        cashPlan.put(work, cash);
    }
    public HashMap<Work, Double> getTimePlan() {
        return timePlan;
    }

    public double getCashOfWork(Work work) {
    	return cashPlan.get(work);
    }
    
    public double getTimeOfWork(Work work) {
    	return timePlan.get(work);
    }
        
    public void updateTimeOfWork(Work work, double cash, FunctionWithRate function) {
    	Executor executor = project.getExecutorOfWork(work);
    	double time = function.solve(cash, executor.getRate(), executor.getTimeOfWork(work));
        timePlan.put(work, time);
    }
    
    public List<Work> getCriticalPath() {
        return criticalPath;
    }

    public void setCriticalPath() {
        Work firstWork = project.getFirstWork();
        criticalPath.add(firstWork);
        List<Work> works = firstWork.getChildren();
        if (!works.isEmpty()) {
            setCriticalList(works);
        }
    }

    private void setCriticalList(List<Work> works) {
        double pathTime = timePlan.get(works.get(0));
        Work pathWork = works.get(0);
        for (Work work : works) {
            if (timePlan.get(work) > pathTime) {
                pathTime = timePlan.get(work);
                pathWork = work;
            }
        }
        criticalPath.add(pathWork);
        works = pathWork.getChildren();
        if (!works.isEmpty()) {
            setCriticalList(works);
        } else {
            return;
        }
    }
    
    public double getAllCash() {
    	double allCash  = 0.0;
        
        for (Work work : cashPlan.keySet()) {
            double cash = cashPlan.get(work);
            allCash += cash;
        }
        return allCash;
    }
    
    public double getAllTime() {
    	double allTime = 0.0;
        for (Work work : criticalPath) {
            if (!work.getName().equals("START_OPTIMIZATION") && !work.getName().equals("END_OPTIMIZATION")) {
                double time = timePlan.get(work);
                allTime += time;
            }
        }
        return allTime;
    }

    public String toString() {
        String str = "\n TIME ";
        for (HashMap.Entry pair : this.getTimePlan().entrySet()) {
            Work work = (Work) pair.getKey();
            String name = work.getName();
            double time = (double) pair.getValue();
            str += name + " = " + time + ", ";
        }
        str += "\n New Time = " + getAllTime();
        str += "\n CASH ";
        for (HashMap.Entry pair : this.getCashPlan().entrySet()) {
            Work work = (Work) pair.getKey();
            String name = work.getName();
            double cash = (double) pair.getValue();
            str += name + " = " + cash + ", ";
        }
        str += "\n Cash = " + getAllCash();
        return str;
    }

}