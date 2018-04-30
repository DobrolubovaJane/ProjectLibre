package com.projity.genetic;

import java.util.List;

import com.projity.objects.Project;

public abstract class AGeneticAlgorithm {
    public abstract List<OvertimePlanHromosome> generateHromosomes(Project project);
    public abstract List<OvertimePlanHromosome> chooseParents(List<OvertimePlanHromosome> listOfParents);
    public abstract OvertimePlanHromosome mutation(OvertimePlanHromosome hromosome);
    public abstract List<OvertimePlanHromosome> selection(List<OvertimePlanHromosome> listOfParents,
                                                             List<OvertimePlanHromosome> listOfChildren);
    public abstract OvertimePlanHromosome result(List<OvertimePlanHromosome> hromosomes);
}
