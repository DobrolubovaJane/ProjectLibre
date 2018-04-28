package genetic;

import functions.FunctionWithRate;
import objects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class OvertimePlanHromosome {
    private HashMap<Work, Double> cashPlan = new HashMap<>();
    private HashMap<Work, Double> timePlan = new HashMap<>();
    private Project project;
    private List<Work> criticalPath = new ArrayList<>();

    public OvertimePlanHromosome(Project p) {
        this.project = p;
    }

    public OvertimePlanHromosome generateHromosome(FunctionWithRate function, Random rnd) {

        for (Work work : project.getListOfWorks()) {
            Executor executor = project.getExecutorOfWork(work);
            ExecutorConditions executorConditions = executor.getExecutorConditions();
            double random = rnd.nextDouble();
            double cash = 0.0;
            double time = 0.0;
            System.out.println("work "+ work.getName());
            if (executorConditions == null) {
                cash = random * executor.getRate() * 4;
                time = function.solve(cash, executor.getRate(), executor.getTimeOfWork(work));
            } else {
                Double condition = executorConditions.getConditionOfWork(work);
                System.out.println("condition "+ condition);
                if (condition == null) {
                    System.out.println("executor.getRate() " +executor.getRate());
                    cash = random * executor.getRate() * 4;
                    time = function.solve(cash, executor.getRate(), executor.getTimeOfWork(work));
                    System.out.println("cash " + cash+ " time " +time);
                } else {
                    if (condition == 0) {
                        System.out.println("condition == 0");
                        time = executor.getTimeOfWork(work);
                        System.out.println("cash " + cash+ " time " +time);
                    } else if (condition > 0) {
                        System.out.println("condition > 0");
                        double maxCash = function.solveMaxCash(executor.getTimeOfWork(work)-condition, executor.getRate(), executor.getTimeOfWork(work));
                        System.out.println("maxCash " +maxCash);
                        cash = random * maxCash;
                        time = function.solve(cash, executor.getRate(), executor.getTimeOfWork(work));
                        System.out.println("cash " + cash+ " time " +time);
                    }
                }
            }

            cashPlan.put(work, cash);
            timePlan.put(work, time);

        }
        System.out.println("cashPlan " + cashPlan+ " timePlan " +timePlan);
        setCriticalPath();
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

    public String toString() {
        String str = "\n TIME ";
        double allTime = 0.0;
        for (HashMap.Entry pair : this.getTimePlan().entrySet()) {
            Work work = (Work) pair.getKey();
            String name = work.getName();
            double time = (double) pair.getValue();
            str += name + " = " + time + ", ";
        }
        for (Work work : criticalPath) {
            if (!work.getName().equals("START_OPTIMIZATION") && !work.getName().equals("END_OPTIMIZATION")) {
                double time = timePlan.get(work);
                allTime += time;
            }
        }
        str += "\n New Time = " + allTime;
        str += "\n CASH ";
        double allCash  = 0.0;
        for (HashMap.Entry pair : this.getCashPlan().entrySet()) {
            Work work = (Work) pair.getKey();
            String name = work.getName();
            double cash = (double) pair.getValue();
            str += name + " = " + cash + ", ";
        }
        for (Work work : cashPlan.keySet()) {
            double cash = cashPlan.get(work);
            allCash += cash;
        }
        str += "\n Cash = " + allCash;
        return str;
    }

}
