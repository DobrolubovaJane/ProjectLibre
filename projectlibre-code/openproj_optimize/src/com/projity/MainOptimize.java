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
	private MainOptimize() {}
	
    public static void HromosomeisGenerate() {

    }

    public static void execute(String[] args) {
        int step = 1;
        int countOfHromosome = 10;
        String input = "src/com/projity/xml/Little_Project.xml";
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
        List <OvertimePlanHromosome> parentList = optim.generateHromosomes(project);
        for (int i = 0; i < step; i++) {
            List<OvertimePlanHromosome> childrenList = optim.chooseParents(parentList);

            List<OvertimePlanHromosome> listToAdd = new ArrayList<>();
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
            XMLUtils.getXMLresult(result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
