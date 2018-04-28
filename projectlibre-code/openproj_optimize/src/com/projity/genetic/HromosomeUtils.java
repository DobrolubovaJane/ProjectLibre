package genetic;

import functions.FunctionWithRate;
import objects.Executor;
import objects.Work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HromosomeUtils {
    private static HromosomeUtils instance;

    private HromosomeUtils() {}

    public static HromosomeUtils getInstance() {
        if (instance == null) {
            instance = new HromosomeUtils();
        }
        return instance;
    }

    public static OvertimePlanHromosome getBest(OvertimePlanHromosome first, OvertimePlanHromosome second) {
        double newTime = first.getProject().getNewTime();

        double firstTime = 0;
        double firstCash = 0;

        List<Work> firstCriticalPath = first.getCriticalPath();

        HashMap firstTimePlan = first.getTimePlan();
        for (Work work : firstCriticalPath) {
            firstTime += (double) firstTimePlan.get(work);
        }

        HashMap firstCashPlan = first.getCashPlan();
        for (Work work : firstCriticalPath) {
            firstCash += (double) firstCashPlan.get(work);
        }

        double secondTime = 0;
        double secondCash = 0;
        List<Work> secondCriticalPath = second.getCriticalPath();

        HashMap secondTimePlan = second.getTimePlan();
        for (Work work : secondCriticalPath) {
            secondTime += (double) secondTimePlan.get(work);
        }

        HashMap secondCashPlan = second.getCashPlan();
        for (Work work : secondCriticalPath) {
            secondCash += (double) secondCashPlan.get(work);
        }

        if (firstTime < secondTime  && secondTime > newTime) {
            return first;
        } else if (firstTime > secondTime && firstTime > newTime) {
            return second;
        } else if (firstCash < secondCash) {
            return first;
        } else {
            return second;
        }
    }
    public static OvertimePlanHromosome getWorst(OvertimePlanHromosome first, OvertimePlanHromosome second) {
        double newTime = first.getProject().getNewTime();
        double firstTime = 0;
        double firstCash = 0;

        List<Work> firstCriticalPath = first.getCriticalPath();

        HashMap firstTimePlan = first.getTimePlan();
        for (Work work : firstCriticalPath) {
            firstTime += (double) firstTimePlan.get(work);
        }
        HashMap<Work, Double> firstCashPlan = first.getCashPlan();
        for (Work work : firstCashPlan.keySet()) {
            firstCash += firstCashPlan.get(work);
        }
        for (Work work : firstCriticalPath) {
            firstCash += (double) firstCashPlan.get(work);
        }

        double secondTime = 0;
        double secondCash = 0;
        List<Work> secondCriticalPath = second.getCriticalPath();

        HashMap secondTimePlan = second.getTimePlan();
        for (Work work : secondCriticalPath) {
            secondTime += (double) secondTimePlan.get(work);
        }
        HashMap<Work, Double> secondCashPlan = second.getCashPlan();
        for (Work work : secondCashPlan.keySet()) {
            secondCash += secondCashPlan.get(work);
        }

        if (firstTime < secondTime && secondTime > newTime) {
            return second;
        } else if (firstTime > secondTime && firstTime > newTime) {
            return first;
        } else if (firstCash < secondCash) {
            return second;
        } else {
            return first;
        }
    }
}