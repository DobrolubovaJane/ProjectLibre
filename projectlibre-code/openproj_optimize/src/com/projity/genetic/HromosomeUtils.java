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
        double firstTime = first.getAllTime();
        double firstCash = first.getAllCash();

        double secondTime = second.getAllTime();
        double secondCash = second.getAllCash();

        if (firstCash < secondCash) {
          return first;
	    } else if (firstCash > secondCash) {
	    	return second;
	    } else if (firstTime >= secondTime) {
  	    	return second;
  	    } else return first;
		
    }
    public static OvertimePlanHromosome getWorst(OvertimePlanHromosome first, OvertimePlanHromosome second) {
        double firstTime = first.getAllTime();
        double firstCash = first.getAllCash();

        double secondTime = second.getAllTime();
        double secondCash = second.getAllCash();

        if (firstCash > secondCash) {
            return first;
  	    } else if (firstCash < secondCash) {
  	    	return second;
  	    } else if (firstTime >= secondTime) {
  	    	return first;
  	    } else return second;
  		
    }
}
