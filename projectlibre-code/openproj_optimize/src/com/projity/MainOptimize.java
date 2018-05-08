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
	 static List <OvertimePlanHromosome> parentList = new ArrayList<>();
	 static List<OvertimePlanHromosome> childrenList = new ArrayList<>();
	 static List<OvertimePlanHromosome> listToAdd = new ArrayList<>();
	 static String input = "src/com/projity/xml/Little_Project_1.xml";
	private MainOptimize() {}
	
    public static void HromosomeisGenerate() {

    }

    public static void execute(String[] args) {
        int step = 200;
        int countOfHromosome = 10;
        
        Double newTime = Double.valueOf(args[0]);
        Double maxCash = Double.valueOf(args[1]);

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
       
        parentList = optim.generateHromosomes(project);
        for (int i = 0; i < step; i++) {
            
            childrenList = optim.chooseParents(parentList);

            
            for (OvertimePlanHromosome h : childrenList) {
                OvertimePlanHromosome m = optim.mutation(h);
                listToAdd.add(m);
            }
            childrenList.addAll(listToAdd);
            parentList = optim.selection(parentList, childrenList);

        }
        OvertimePlanHromosome result = optim.result(parentList);
        System.out.println("RESULT " + result.toString());
        try {
            XMLUtils.updateXML(result, input);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
