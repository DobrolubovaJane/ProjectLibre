package com.projity;

import org.xml.sax.SAXException;

import com.projity.functions.FunctionWithRate;
import com.projity.genetic.OptimizationOfTheWorkPlan;
import com.projity.genetic.OvertimePlanHromosome;
import com.projity.objects.Project;
import com.projity.xml.XMLUtils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;

public class MainOptimize {
	 static List <OvertimePlanHromosome> population = new ArrayList<>();
	 static List<OvertimePlanHromosome> parents = new ArrayList<>();
	 static List<OvertimePlanHromosome> children = new ArrayList<>();
	 static String input = "src/com/projity/xml/Little_Project_1.xml";
	private MainOptimize() {}
	
    public static void HromosomeisGenerate() {

    }

    public static void execute(String[] args) {
//        int step = 30;
//        int countOfHromosome = 100;
        
        Double newTime = Double.valueOf(args[0]);
        Double maxCash = Double.valueOf(args[1]);
        int step = Integer.valueOf(args[2]);
        int countOfHromosome = Integer.valueOf(args[3]);

        Project project = null;
        try {
            project = XMLUtils.init(input, newTime, maxCash);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        if (project == null) {
            System.out.println("Project is empty!");
            return;
        }

        FunctionWithRate function = new FunctionWithRate();

        OptimizationOfTheWorkPlan optim = new OptimizationOfTheWorkPlan(countOfHromosome, function);
       
        population = optim.generateHromosomes(project);
//        System.out.println("POPULATION " + population.toString());
//        System.out.println();
        for (int i = 0; i < step; i++) {
            
        	parents = optim.chooseParents(population);
//        	System.out.println("PARENTS " + parents.toString());
//        	System.out.println();

        	children = optim.crossingover(parents.get(0), parents.get(1));
        	if (children.isEmpty()) {
        		i--;
        		continue;
        	}
//        	System.out.println("CHILDREN " + children.toString());
//        	System.out.println();

        	
        
            population = (optim.selection(population, children));
//            System.out.println("POPULATION!!! " + population.toString());
//            System.out.println();
        }
        OvertimePlanHromosome result = optim.result(population);
        System.out.println("RESULT " + result.toString());
        try {
            XMLUtils.updateXML(result, input);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
