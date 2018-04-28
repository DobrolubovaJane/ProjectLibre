package src.com.projity;

import functions.FunctionWithRate;
import genetic.OptimizationOfTheWorkPlan;
import genetic.OvertimePlanHromosome;
import objects.Project;
import org.xml.sax.SAXException;
import xml.XMLUtils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;

public class Main {
	private Main() {}
	
    public static void HromosomeisGenerate() {

    }

    public static void main(String[] args) {
        int step = 1;
        int countOfHromosome = 10;
        String input = "src/com/projity/xml/Little Project.xml";
        Double newTime = Double.valueOf(args[0]);
        Double maxCash = 9.0;

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
