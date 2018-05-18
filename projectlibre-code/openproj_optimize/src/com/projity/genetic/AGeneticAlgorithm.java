package com.projity.genetic;

import java.util.List;

import com.projity.objects.Project;

public abstract class AGeneticAlgorithm {
    public abstract List<OvertimePlanChromosome> generateChromosomes(Project project);
    public abstract List<OvertimePlanChromosome> chooseParents(List<OvertimePlanChromosome> listOfParents);
    public abstract OvertimePlanChromosome mutation(OvertimePlanChromosome hromosome);
    public abstract List<OvertimePlanChromosome> crossingover(OvertimePlanChromosome first, OvertimePlanChromosome second);
    public abstract List<OvertimePlanChromosome> selection(List<OvertimePlanChromosome> listOfParents,
                                                             List<OvertimePlanChromosome> listOfChildren);
    public abstract OvertimePlanChromosome result(List<OvertimePlanChromosome> hromosomes);
}
