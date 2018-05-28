package com.projity.objects;

import java.util.HashMap;

public class ExecutorConditions {
    private HashMap<Work, Boolean> isWorkConstant = new HashMap<>();

    public ExecutorConditions() {}
    public ExecutorConditions(HashMap<Work, Boolean> conditions) {
        this.isWorkConstant = conditions;
    }

    public Boolean isWorkConstant(Work work) {
        return isWorkConstant.get(work);
    }

    public void workIsConstantOrNot(Work work, Boolean isConst) {
    	isWorkConstant.put(work, isConst);
    }
}
