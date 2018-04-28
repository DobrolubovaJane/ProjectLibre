package genetic;

import objects.Project;

import java.util.List;

public abstract class AGeneticAlgorithm {
    public abstract List<OvertimePlanHromosome> generateHromosomes(Project project);
    public abstract List<OvertimePlanHromosome> chooseParents(List<OvertimePlanHromosome> listOfParents);
    public abstract OvertimePlanHromosome mutation(OvertimePlanHromosome hromosome);
    public abstract List<OvertimePlanHromosome> selection(List<OvertimePlanHromosome> listOfParents,
                                                             List<OvertimePlanHromosome> listOfChildren);
    public abstract OvertimePlanHromosome result(List<OvertimePlanHromosome> hromosomes);
}
