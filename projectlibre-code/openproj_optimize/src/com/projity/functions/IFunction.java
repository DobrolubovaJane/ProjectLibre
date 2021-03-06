package com.projity.functions;

public interface IFunction {
    public double solve(double x, double rate, double currentTime);
    public double solveMaxCash(double rate, double currentTime);
}
