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
        list.clear();
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
           
        OvertimePlanHromosome bestParent = null;
        bestParent = listOfParents.get(0);
        for (OvertimePlanHromosome child : listOfParents) {
          bestParent = HromosomeUtils.getBest(bestParent, child);
        }
        OvertimePlanHromosome parent1 = bestParent;
        listOfParents.remove(parent1);
        bestParent = listOfParents.get(0);
        for (OvertimePlanHromosome child : listOfParents) {
            bestParent = HromosomeUtils.getBest(bestParent, child);
          }
       OvertimePlanHromosome parent2 = bestParent;
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
        OvertimePlanHromosome worst = null;

        listOfParents.addAll(listOfChildren);
        worst = listOfParents.get(0);
        for (OvertimePlanHromosome parent : listOfParents) {
        	worst = HromosomeUtils.getWorst(worst, parent);
        }
        System.out.println("WORST " + worst.toString());
        System.out.println();
        listOfParents.remove(worst);

        return listOfParents;
    }
    
    @Override
    public OvertimePlanHromosome result(List<OvertimePlanHromosome> list) {
        HromosomeUtils.getInstance();
        OvertimePlanHromosome bestChild = null;
        bestChild = list.get(0);
        for (OvertimePlanHromosome child : list) {
            bestChild = HromosomeUtils.getBest(bestChild, child);
        }
        for (HashMap.Entry pair : bestChild.getTimePlan().entrySet()) {
            Project project = bestChild.getProject();
            Work work = (Work) pair.getKey();
            Executor executor = project.getExecutorOfWork(work);
            System.out.println("work " + work);
            double oldTime = executor.getTimeOfWork(work);
            double newTime = (double) pair.getValue();
            if (oldTime <= newTime) {
                bestChild.editCashPlan(work, 0.0);
            }

        }
        return bestChild;
    }

	@Override
	public List<OvertimePlanHromosome> crossingover(OvertimePlanHromosome first, OvertimePlanHromosome second) {
		System.out.println("CROSSINGOVER");
		List<OvertimePlanHromosome> result = new ArrayList<>();
		Project project = first.getProject();
		OvertimePlanHromosome child = new OvertimePlanHromosome(project); 
		
		for (Work work : project.getListOfWorks()) {
			double firstWorkCash = first.getCashOfWork(work);
			double secondWorkCash = second.getCashOfWork(work);
			double newWorkCash = (firstWorkCash + secondWorkCash) / 2;

			
			child.editCashPlan(work, newWorkCash);
			child.updateTimeOfWork(work, newWorkCash, function);

		}
		child.setCriticalPath();
		if (child.getAllTime() > project.getNewTime()) {
			System.out.println("More than new time");
			return result;
		}
		System.out.println("newWorkTime " + child.getAllTime());
		System.out.println("newWorkCash " + child.getAllCash());
		
		result.add(child);
		return result;
	}
}
