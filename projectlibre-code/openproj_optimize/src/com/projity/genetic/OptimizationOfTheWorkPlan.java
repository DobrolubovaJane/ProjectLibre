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
    public List<OvertimePlanChromosome> generateChromosomes(Project project) {
        List<OvertimePlanChromosome> list = new ArrayList<>();
        list.clear();
		for (int i = 0; i < N; i++) {
            OvertimePlanChromosome h = new OvertimePlanChromosome(project);
            h.generateChromosome(function, random);
            list.add(h);
        }
        return list;
    }

    @Override
    public List<OvertimePlanChromosome> chooseParents(List<OvertimePlanChromosome> listOfParents) {
    	List<OvertimePlanChromosome> copyListOfParents = new ArrayList<>();
    	copyListOfParents.addAll(listOfParents);
        List<OvertimePlanChromosome> listOfChildren = new ArrayList<>();
           
        OvertimePlanChromosome bestParent = null;
        bestParent = copyListOfParents.get(0);
        for (OvertimePlanChromosome child : copyListOfParents) {
          bestParent = ChromosomeUtils.getBest(bestParent, child);
        }
        OvertimePlanChromosome parent1 = bestParent;
        copyListOfParents.remove(parent1);
        bestParent = copyListOfParents.get(0);
        for (OvertimePlanChromosome child : copyListOfParents) {
            bestParent = ChromosomeUtils.getBest(bestParent, child);
          }
       OvertimePlanChromosome parent2 = bestParent;
       listOfChildren.add(parent1);
       listOfChildren.add(parent2);
       return listOfChildren;
    }

    @Override
    public OvertimePlanChromosome mutation(OvertimePlanChromosome hromosome) {

        OvertimePlanChromosome newH = new OvertimePlanChromosome(hromosome.getProject());
        newH.generateChromosome(function, random);
        return newH;
    }

    @Override
    public List<OvertimePlanChromosome> selection(List<OvertimePlanChromosome> listOfParents, List<OvertimePlanChromosome> listOfChildren) {
        ChromosomeUtils.getInstance();
        OvertimePlanChromosome worst = null;

      
        listOfParents.addAll(listOfChildren);
        worst = listOfParents.get(0);
        for (OvertimePlanChromosome parent : listOfParents) {
        	worst = ChromosomeUtils.getWorst(worst, parent);
        }
//        System.out.println("WORST " + worst.toString());
//        System.out.println();
        listOfParents.remove(worst);

        return listOfParents;
    }
    
    @Override
    public OvertimePlanChromosome result(List<OvertimePlanChromosome> list) {
//    	System.out.println("result" );
        ChromosomeUtils.getInstance();
        OvertimePlanChromosome bestChild = null;
        bestChild = list.get(0);
//        System.out.println("bestChild" + bestChild);
        for (OvertimePlanChromosome child : list) {
            bestChild = ChromosomeUtils.getBest(bestChild, child);
        }
        for (HashMap.Entry pair : bestChild.getTimePlan().entrySet()) {
            Project project = bestChild.getProject();
            Work work = (Work) pair.getKey();
            Executor executor = project.getExecutorOfWork(work);
           // System.out.println("work " + work);
            double oldTime = executor.getTimeOfWork(work);
            double newTime = (double) pair.getValue();
            if (oldTime <= newTime) {
                bestChild.editCashPlan(work, 0.0);
            }

        }
        return bestChild;
    }

	@Override
	public List<OvertimePlanChromosome> crossingover(OvertimePlanChromosome first, OvertimePlanChromosome second) {
//		System.out.println("CROSSINGOVER");
		List<OvertimePlanChromosome> result = new ArrayList<>();
		Project project = first.getProject();
		OvertimePlanChromosome child = new OvertimePlanChromosome(project); 
		
		for (Work work : project.getListOfWorks()) {
			double firstWorkCash = first.getCashOfWork(work);
			double secondWorkCash = second.getCashOfWork(work);
			double newWorkCash = (firstWorkCash + secondWorkCash) / 2;

			
			child.editCashPlan(work, newWorkCash);
			child.updateTimeOfWork(work, newWorkCash, function);

		}
		child.setCriticalPath();
		if (child.getAllTime() > project.getNewTime()) {
			//System.out.println("More than new time");
			return result;
		}
//		System.out.println("newWorkTime " + child.getAllTime());
//		System.out.println("newWorkCash " + child.getAllCash());
		
		result.add(child);
		return result;
	}
}
