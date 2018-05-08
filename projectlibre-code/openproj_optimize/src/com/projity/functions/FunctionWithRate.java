package com.projity.functions;

public class FunctionWithRate implements IFunction {
    @Override
    public double solve(double x, double rate, double currentTime) {
        if (x < rate) {
            return currentTime;
        } else if (x >= rate && x <= 4*rate) {
            return (currentTime*8/(8+x/rate));
        } else {
            return (currentTime*8/12);
        }
    }

    @Override
    public double solveMaxCash(double minTime, double rate, double currentTime) {
    	System.out.println("currentTime " + currentTime + " rate " + rate + " minTime " + minTime + " maxSolve " + (currentTime - minTime) * rate);
        if (minTime == 0) {
        	minTime = currentTime / 2;
        } 	return ((currentTime * 8 / minTime) - 8) * rate;
    	//return (currentTime - minTime) * rate;
    }
}
