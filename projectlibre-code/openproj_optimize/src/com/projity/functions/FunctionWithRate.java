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
    public double solveMaxCash(double rate, double currentTime) {
    	return (currentTime / 2) * rate;
    }
}
