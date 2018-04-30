package com.projity.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.projity.functions.FunctionWithRate;
import com.projity.objects.Executor;
import com.projity.objects.Project;
import com.projity.objects.Work;

public class OptimizationOfTheWorkPlan extends AGeneticAlgorithm{
    private int N;
    private FunctionWithRate function;
    final Random random = new Random();

    public OptimizationOfTheWorkPlan(int N, FunctionWithRate function) {
        this.N = N;
        this.function = function;
    }

    @Override
    public List<OvertimePlanHromosome> generateHromosomes(Project project) {
        List<OvertimePlanHromosome> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            OvertimePlanHromosome h = new OvertimePlanHromosome(project);
            h.generateHromosome(function, random);
            list.add(h);
        }
        return list;
    }

    @Override
    public List<OvertimePlanHromosome> chooseParents(List<OvertimePlanHromosome> listOfParents) {
        List<OvertimePlanHromosome> listOfChildren = new ArrayList<>();

        int size = listOfParents.size();
        OvertimePlanHromosome parent1 = listOfParents.get(random.nextInt(size));
        OvertimePlanHromosome parent2 = listOfParents.get(random.nextInt(size));
        listOfChildren.add(parent1);
        listOfChildren.add(parent2);
        return listOfChildren;
    }

    @Override
    public OvertimePlanHromosome mutation(OvertimePlanHromosome hromosome) {

        OvertimePlanHromosome newH = new OvertimePlanHromosome(hromosome.getProject());
        newH.generateHromosome(function, random);
        return newH;
    }

    @Override
    public List<OvertimePlanHromosome> selection(List<OvertimePlanHromosome> listOfParents, List<OvertimePlanHromosome> listOfChildren) {
        HromosomeUtils.getInstance();
        OvertimePlanHromosome worstParent = listOfParents.get(0);
        for (OvertimePlanHromosome parent : listOfParents) {
            worstParent = HromosomeUtils.getWorst(worstParent, parent);
        }

        OvertimePlanHromosome bestChild = listOfChildren.get(0);
        for (OvertimePlanHromosome child : listOfChildren) {
            bestChild = HromosomeUtils.getBest(bestChild, child);
        }

        listOfParents.remove(worstParent);
        listOfParents.add(bestChild);

        return listOfParents;
    }
    @Override
    public OvertimePlanHromosome result(List<OvertimePlanHromosome> list) {
        HromosomeUtils.getInstance();
        OvertimePlanHromosome bestChild = list.get(0);
        for (OvertimePlanHromosome child : list) {
            bestChild = HromosomeUtils.getBest(bestChild, child);
        }
        for (HashMap.Entry pair : bestChild.getTimePlan().entrySet()) {
            Project project = bestChild.getProject();
            Work work = (Work) pair.getKey();
            Executor executor = project.getExecutorOfWork(work);
            double oldTime = executor.getTimeOfWork(work);
            double newTime = (double) pair.getValue();
            if (oldTime == newTime) {
                bestChild.editCashPlan(work, 0.0);
            }

        }
        return bestChild;
    }
}
