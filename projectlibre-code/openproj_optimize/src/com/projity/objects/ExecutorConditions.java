package com.projity.objects;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ExecutorConditions {
    private HashMap<Work, Double> conditionOfWork = new HashMap<>();

    public ExecutorConditions() {}
    public ExecutorConditions(HashMap<Work, Double> conditions) {
        this.conditionOfWork = conditions;
    }

    public Double getConditionOfWork(Work work) {
        return conditionOfWork.get(work);
    }

    public void setConditionOfWork(Work work, Double time) {
        conditionOfWork.put(work, time);
    }
}
