package edu.ithaca.dragonlab.ckc.suggester;

import edu.ithaca.dragonlab.ckc.conceptgraph.ConceptGraph;
import edu.ithaca.dragonlab.ckc.conceptgraph.ConceptNode;
import edu.ithaca.dragonlab.ckc.learningobject.LearningObject;
import edu.ithaca.dragonlab.ckc.learningobject.LearningObjectResponse;

import java.util.*;

/**
 * Created by home on 5/19/17.
 */
public class LearningObjectSuggester {
    public static double max = .75;
    public static double min = .55;
    public static double wrongMax = .59;


    /**
     * Creates a list of ConceptNodes that are between the knowledge range and are not ancestors with children with high knowledgeEstimates
     * @param graph
     * @return
     */
    public static List<ConceptNode> conceptsToWorkOn(ConceptGraph graph){
        List<ConceptNode> suggestedConceptList = new ArrayList<ConceptNode>();
        for (String key : graph.getAllNodeIds()) {
            ConceptNode node = graph.findNodeById(key);

            if (node.getKnowledgeEstimate() >= min && node.getKnowledgeEstimate() <= max) {
                //if false, then the node isn't an ancestor or the compare node is high THEREFORE you can add it to the list
                boolean anc = graph.canIgnoreNode(node);
                if (!anc) {
                    suggestedConceptList.add(node);
                }
            }
        }

        return suggestedConceptList;
    }


    /**
     *goes through the graph and creates a map of ConceptNodes that are between 55%-75% and are not ancestors (unless the child node does better than 75%)
     *then it sorts the list of LearningObjectSuggestions and takes the incomplete learningObjects. (The list is ordered by incomplete, wrong, and right and
     *within each of those categories based on the highest importance value to lowest).
     *@param graph Concept graph
     *@param choice: 1= incomplete, 0 = wrong
     *@return the map of incomplete learningObjectSuggestions in order of highest importance value to lowest
     */
    public static HashMap<String, List<LearningObjectSuggestion>> buildSuggestionMap(List<ConceptNode> suggestedConceptList, Integer choice, ConceptGraph graph){

        HashMap<String, List<LearningObjectSuggestion>> suggestedConceptNodeMap = new HashMap<>();


        for (int x =0; x< suggestedConceptList.size(); x++) {
            ConceptNode concept = suggestedConceptList.get(x);


            List<LearningObjectSuggestion> testList = new ArrayList<>();

            HashMap<String, Integer> map = graph.buildLearningObjectSummaryList(concept.getID());
            List<LearningObjectSuggestion> list = buildLearningObjectSuggestionList(map, graph.getLearningObjectMap(), concept.getID());
            sortSuggestions(list);
            for (int i = 0; i < list.size(); i++) {
                //if it is incomplete
                if (choice.equals(1)) {
                    if (list.get(i).getLevel().equals(LearningObjectSuggestion.Level.INCOMPLETE)) {
                        //then add it
                        testList.add(list.get(i));
                    }
                } else {
                    if (list.get(i).getLevel().equals(LearningObjectSuggestion.Level.WRONG)) {
                        //then add it
                        testList.add(list.get(i));
                    }
                }

            }
            suggestedConceptNodeMap.put(concept.getID(), testList);

        }

        return suggestedConceptNodeMap;
    }



    public static void sortSuggestions(List<LearningObjectSuggestion> myList){
        Collections.sort(myList, new LearningObjectSuggestionComparator());
    }

    /**
    *takes a map of strings and creates a list of learningObjectSuggestion that hold if the learningObject was incomplete, wrong, or right, the pathNum, and the concept that caused the LearningObject to be suggested
    *@param summaryList- map of the summaryList (map of the LearningObjects and the pathNum from a certain start)
    *@param  learningObjectMap- map of all of the learningObjects
    *@param causedConcept- the ID of ConceptNode that the learningObject came from
    *@returns a list of the created LearningObjectSuggestions
    */
    public static List<LearningObjectSuggestion> buildLearningObjectSuggestionList(Map<String, Integer> summaryList, Map<String, LearningObject> learningObjectMap, String causedConcept){

        List<LearningObjectSuggestion> myList = new ArrayList<LearningObjectSuggestion>();
        for (String key : summaryList.keySet()){
            int lineNum = summaryList.get(key);
            LearningObject node = learningObjectMap.get(key);
            double estimate = node.calcKnowledgeEstimate();
            LearningObjectSuggestion.Level level;
            //fix to fit preconditions
            LearningObjectSuggestion.Level levelIn;
            List<LearningObjectResponse> resList = node.getResponses();
            if(resList.size()==0){
                levelIn = LearningObjectSuggestion.Level.INCOMPLETE;
                LearningObjectSuggestion suggestionNode = new LearningObjectSuggestion(key,lineNum,levelIn, causedConcept);
                myList.add(suggestionNode);

            }else{
                if(estimate> 0 && estimate<= wrongMax){
                    level = LearningObjectSuggestion.Level.WRONG;
                }else{
                    level = LearningObjectSuggestion.Level.RIGHT;
                }
                LearningObjectSuggestion suggestionNode = new LearningObjectSuggestion(key,lineNum,level,causedConcept);
                myList.add(suggestionNode);

            }


        }
        return myList;
    }
}
