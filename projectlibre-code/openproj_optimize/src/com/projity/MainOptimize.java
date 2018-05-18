package com.projity;

import org.xml.sax.SAXException;

import com.projity.functions.FunctionWithRate;
import com.projity.genetic.OptimizationOfTheWorkPlan;
import com.projity.genetic.OvertimePlanChromosome;
import com.projity.objects.Project;
import com.projity.xml.XMLUtils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;

public class MainOptimize {
	 static List <OvertimePlanChromosome> population = new ArrayList<>();
	 static List<OvertimePlanChromosome> parents = new ArrayList<>();
	 static List<OvertimePlanChromosome> children = new ArrayList<>();
	 static String input = "src/com/projity/xml/Build_Plan.xml";
	private MainOptimize() {}
	
    public static void HromosomeisGenerate() {

    }

    public static void execute(String[] args) {
        
        Double newTime = Double.valueOf(args[0]);
        Double maxCash = Double.valueOf(args[1]);
        int step = Integer.valueOf(args[2]);
        int countOfChromosome = Integer.valueOf(args[3]);

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
        
//        for (int s = 0; s <= 1000; s+=100) {
//        	for (int h = 50; h <= 500; h +=50) {
//        		double resultCash = 0.0;
//        
//		        for (int j = 0; j < 100; j++) {
//		       
		        FunctionWithRate function = new FunctionWithRate();
		
		        OptimizationOfTheWorkPlan optim = new OptimizationOfTheWorkPlan(countOfChromosome, function);
		       
		        population = optim.generateChromosomes(project);
		//        System.out.println("POPULATION " + population.toString());
		//        System.out.println();
		        
		        
		        
			        for (int i = 0; i < step; i++) {
			            
			        	parents = optim.chooseParents(population);
		//	        	System.out.println("PARENTS " + parents.toString());
		//	        	System.out.println();
		//	
			        	children = optim.crossingover(parents.get(0), parents.get(1));
			        	if (children.isEmpty()) {
			        		
			        		OvertimePlanChromosome mutation = optim.mutation(population.get(0));
			        		population.add(mutation);
			        	}
		//	        	System.out.println("CHILDREN " + children.toString());
		//	        	System.out.println();
		//	
		//	        	 System.out.println("before cross POPULATION!!! " + population.toString());
		//	        
			            population = (optim.selection(population, children));
		//	            System.out.println("POPULATION!!! " + population.toString());
		//	            System.out.println("i = "+i+"   population.size() "+population.size());
			            if (population.size() == 1) {
			            	System.out.println("End " );
			            	break;
			            }
			        }
		//	        System.out.println("Before result");
			        OvertimePlanChromosome result = optim.result(population);
			       // resultCash += result.getAllCash();
			        System.out.println("RESULT " + result.toString());
			        try {
			            XMLUtils.updateXML(result, input);
			        } catch (TransformerException e) {
			            e.printStackTrace();
			        }
			    }
//		        System.out.println("_____s = " + s + "_______h = "+ h +"___________resultCash " + resultCash/100);
//        	}
//        }
//    }

}
