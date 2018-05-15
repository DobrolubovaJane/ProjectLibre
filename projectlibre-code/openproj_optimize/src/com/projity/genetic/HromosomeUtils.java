package com.projity.genetic;


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

        double firstTime = first.getAllTime();
        double firstCash = first.getAllCash();

        double secondTime = second.getAllTime();
        double secondCash = second.getAllCash();
        
        if (firstTime < secondTime) {
        	if (secondTime < newTime) {
        		if (firstCash < secondCash) {
                    return first;
                } else return second;
        	}
            return first;
        } else if (firstTime > secondTime) {
        	if (firstTime < newTime) {
        		if (firstCash < secondCash) {
                    return first;
                } else return second;
        	}
            return second;
        } else if (firstCash < secondCash) {
            return first;
        } else {
            return second;
        }

    }
    public static OvertimePlanHromosome getWorst(OvertimePlanHromosome first, OvertimePlanHromosome second) {
    	double newTime = first.getProject().getNewTime();

        double firstTime = first.getAllTime();
        double firstCash = first.getAllCash();

        double secondTime = second.getAllTime();
        double secondCash = second.getAllCash();

        if (firstTime < secondTime) {
        	if (secondTime < newTime) {
        		if (firstCash < secondCash) {
                    return second;
                } else return first;
        	}
            return second;
        } else if (firstTime > secondTime) {
        	if (firstTime < newTime) {
        		if (firstCash < secondCash) {
                    return second;
                } else return first;
        	}
            return first;
        } else if (firstCash < secondCash) {
            return second;
        } else {
            return first;
        }
	
    }
}
