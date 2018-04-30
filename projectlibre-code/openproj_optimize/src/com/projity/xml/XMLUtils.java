package com.projity.xml;

import com.projity.genetic.OvertimePlanHromosome;
import com.projity.objects.Executor;
import com.projity.objects.ExecutorConditions;
import com.projity.objects.Project;
import com.projity.objects.Work;
import com.sun.org.apache.xerces.internal.dom.ChildNode;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XMLUtils {
    static Document document;
    static Map<String, Work> worksId = new HashMap<>();
    static Map<String, Executor> executorsId = new HashMap<>();
    static Map<String, ExecutorConditions> executorConditionsId = new HashMap<>();
    static HashMap<Work, Executor> workAndExecutor = new HashMap<>();
    private static Work startOptimization;

    private XMLUtils() {}

    public static Project init(String input, Double newTime, Double maxCash) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = documentBuilder.parse(input);

        initWorks();
        initDependencies();
        initExecutors();
        initAssigment();
//        initConditions();
        return initProject(newTime, maxCash);
    }



    private static void initWorks() {
        NodeList works = document.getElementsByTagName("Task");

        for (int i = 0; i < works.getLength(); i++) {

            Node work = works.item(i);
            NodeList attributes = work.getChildNodes();
            if (work.getNodeType() == Node.ELEMENT_NODE) {

                Element eWork = (Element) work;
                String id = eWork.getElementsByTagName("UID").item(0).getTextContent();

                String name = eWork.getElementsByTagName("Name").item(0).getTextContent();

                Work workObj = new Work(name);
                if (name.equals("START_OPTIMIZATION")) {
                    startOptimization = workObj;
                }
                worksId.put(id, workObj);
            }
        }
    }

    private static void initDependencies() {
        NodeList works = document.getElementsByTagName("Task");

        for (int i = 0; i < works.getLength(); i++) {

            Node work = works.item(i);
            NodeList attributes = work.getChildNodes();
            if (work.getNodeType() == Node.ELEMENT_NODE) {

                Element eWork = (Element) work;

                NodeList dependencies = eWork.getElementsByTagName("PredecessorLink");
                for (int j = 0; j < dependencies.getLength(); j++) {
                    Node dependency = dependencies.item(j);
                    if (dependency.getNodeType() == Node.ELEMENT_NODE) {

                        String id = eWork.getElementsByTagName("UID").item(0).getTextContent();

                        Element eDep = (Element) dependency;
                        String work_id = eDep.getElementsByTagName("PredecessorUID").item(0).getTextContent();
                        Work depWork = worksId.get(work_id);
                        Work workObj = worksId.get(id);
                        workObj.setDependenciesToParent(Arrays.asList(depWork));
                    }
                }
            }
        }
    }

    private static void initExecutors() {
        NodeList executors = document.getElementsByTagName("Resource");

        for (int i = 0; i < executors.getLength(); i++) {

            Node executor = executors.item(i);

            if (executor.getNodeType() == Node.ELEMENT_NODE) {

                Element eEx = (Element) executor;

                String id = eEx.getElementsByTagName("UID").item(0).getTextContent();
                String name = eEx.getElementsByTagName("Name").item(0).getTextContent();
                Double rate = Double.valueOf(eEx.getElementsByTagName("OvertimeRateFormat").item(0).getTextContent());
                executorsId.put(id, new Executor(name, rate));


            }
        }
        executorsId.put("-65535", new Executor("admin", 0));
    }

    private static void initAssigment() {
        NodeList assignments = document.getElementsByTagName("Assignment");
        for (int i = 0; i < assignments.getLength(); i++) {

            Node assignent = assignments.item(i);

            if (assignent.getNodeType() == Node.ELEMENT_NODE) {

                Element eAss = (Element) assignent;

                String work_id = eAss.getElementsByTagName("TaskUID").item(0).getTextContent();

                Double time;

                if (eAss.getElementsByTagName("Work").item(0) == null) {
                    time = 0.0;
                } else {

                    time = TimeUtils.getTimeInHours(eAss.getElementsByTagName("Work").item(0).getTextContent());
                }

                Work work = worksId.get(work_id);
                String ExecutorId = eAss.getElementsByTagName("ResourceUID").item(0).getTextContent();

                Executor executorObj = executorsId.get(ExecutorId);
                executorObj.setWork(work, time);
                workAndExecutor.put(work, executorObj);
                HashMap<Work, Double> conditionMap = new HashMap<>();
                conditionMap.put(work, Double.valueOf("3"));
                executorObj.setExecutorConditions(new ExecutorConditions(conditionMap));



            }
        }
    }


    private static void initConditions() {
//        NodeList conditions = document.getElementsByTagName("condition");
//
//        for (int i = 0; i < conditions.getLength(); i++) {
//
//            Node condition = conditions.item(i);
//
//            if (condition.getNodeType() == Node.ELEMENT_NODE) {
//
//                Element eCond = (Element) condition;
//
//                String work_id = eCond.getAttribute("work_id");
//                String executor_id = eCond.getAttribute("executor_id");
//                Double value = Double.valueOf(eCond.getAttribute("value"));
//
//                Work workObj = worksId.get(work_id);
//
//                ExecutorConditions executorConditions;
//                if (!executorConditionsId.containsValue(executor_id)) {
//                    executorConditions = new ExecutorConditions();
//                    executorConditionsId.put(executor_id, executorConditions);
//                } else {
//                    executorConditions = executorConditionsId.get(executor_id);
//                }
//                executorConditions.setConditionOfWork(workObj, value);
//            }
//
//        }
//        for (Map.Entry<String, ExecutorConditions> entry : executorConditionsId.entrySet()) {
//
//            Executor executorObj = executorsId.get(entry.getKey());
//            executorObj.setExecutorConditions(entry.getValue());
//        }

    }
    private static Project initProject( Double newTime, Double maxCash) {

        Project projectObj = new Project(startOptimization, workAndExecutor, newTime, maxCash);

        return projectObj;
    }
     public static void getXMLresult(OvertimePlanHromosome plan) throws ParserConfigurationException, TransformerException {
         HashMap<Work, Double> cashPlan = plan.getCashPlan();
         Map timePlan  = plan.getTimePlan();
         Double allCash = 0.0;
         Double allTime = 0.0;
         for (Work work : plan.getCriticalPath()) {
             double time = (double) timePlan.get(work);
             allTime += time;
         }
         for (Work work : cashPlan.keySet()) {
             double cash = cashPlan.get(work);
             allCash += cash;
         }

         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         factory.setNamespaceAware(true);
         Document doc = factory.newDocumentBuilder().newDocument();

         Element root = doc.createElement("project");
         root.setAttribute("cash", allCash.toString());
         root.setAttribute("new_time", allTime.toString());
         doc.appendChild(root);

         Element works = doc.createElement("works");
         root.appendChild(works);

         for (HashMap.Entry pair : worksId.entrySet()) {
             Work work = (Work) pair.getValue();
            Element eWork = doc.createElement("work");
             eWork.setAttribute("id", pair.getKey().toString());
             eWork.setAttribute("name", work.getName());
             eWork.setAttribute("new_time", TimeUtils.getTimeForXML((double)timePlan.get(work)));
             eWork.setAttribute("cash", cashPlan.get(work).toString());
             works.appendChild(eWork);
         }

         File file = new File("source/src/xml/result.xml");

         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         transformerFactory.setAttribute("indent-number", 10);
         Transformer transformer = transformerFactory.newTransformer();
         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
         transformer.transform(new DOMSource(doc), new StreamResult(file));
     }
}
