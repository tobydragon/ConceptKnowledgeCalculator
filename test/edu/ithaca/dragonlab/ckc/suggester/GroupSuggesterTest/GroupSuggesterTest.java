package edu.ithaca.dragonlab.ckc.suggester.GroupSuggesterTest;

import edu.ithaca.dragonlab.ckc.ConceptKnowledgeCalculator;
import edu.ithaca.dragonlab.ckc.ConceptKnowledgeCalculatorAPI;
import edu.ithaca.dragonlab.ckc.conceptgraph.CohortConceptGraphs;
import edu.ithaca.dragonlab.ckc.suggester.GroupSuggester.KnowedgeEstimateGroupSuggester;
import edu.ithaca.dragonlab.ckc.suggester.GroupSuggester.GroupSuggester;
import edu.ithaca.dragonlab.ckc.suggester.GroupSuggester.RandomGroupSuggester;
import edu.ithaca.dragonlab.ckc.suggester.GroupSuggester.SuggestionGroupSuggester;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by mkimmitchell on 7/31/17.
 */
public class GroupSuggesterTest {


    @Test
    public void randomGroupTestOddStudents() {
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {
            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/researchConceptGraph.json", "test/testresources/ManuallyCreated/researchResource2.json", "test/testresources/ManuallyCreated/researchAssessment2.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new RandomGroupSuggester();

        //groups of two
        List<List<String>> groupings = obj.suggestGroup(graphs, 2);

        Assert.assertEquals(groupings.size(), 3);
        Assert.assertEquals(groupings.get(0).size(),2);
        Assert.assertEquals(groupings.get(1).size(), 2);
        Assert.assertEquals(groupings.get(2).size(), 1);

        Assert.assertNotEquals(groupings.get(0).get(0), groupings.get(0).get(1), groupings.get(1).get(0));

        //groups of three
        List<List<String>> groupings2 = obj.suggestGroup(graphs, 3);

        Assert.assertEquals(groupings2.size(), 2);
        Assert.assertEquals(groupings2.get(0).size(),3);
        Assert.assertNotEquals(groupings2.get(0).get(0), groupings2.get(0).get(1), groupings2.get(0).get(2));


    }


    @Test
    public void randomGroupTestLessStudents() {
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {
            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/simpleConceptGraph.json", "test/testresources/ManuallyCreated/simpleResource.json", "test/testresources/ManuallyCreated/simpleAssessment.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new RandomGroupSuggester();

        //groups of two
        List<List<String>> groupings = obj.suggestGroup(graphs, 2);

        Assert.assertEquals(groupings.size(), 1);
        Assert.assertEquals(groupings.get(0).size(),1);


//        //groups of three
        List<List<String>> groupings2 = obj.suggestGroup(graphs, 3);

        Assert.assertEquals(groupings2.size(), 1);
        Assert.assertEquals(groupings2.get(0).size(),1);


    }



    @Test
    public void randomGroupTestEvenStudents() {
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {
            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/researchConceptGraph.json", "test/testresources/ManuallyCreated/researchResource1.json", "test/testresources/ManuallyCreated/researchAssessment1.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new RandomGroupSuggester();

        //groups of two
        List<List<String>> groupings = obj.suggestGroup(graphs, 2);

        Assert.assertEquals(groupings.size(), 3);
        Assert.assertEquals(groupings.get(0).size(),2);
        Assert.assertEquals(groupings.get(1).size(), 2);
        Assert.assertEquals(groupings.get(2).size(), 2);

        List<String> test = new ArrayList<>();
        test.add(groupings.get(0).get(0));
        test.add( groupings.get(0).get(1));
        test.add(groupings.get(1).get(0));
        test.add( groupings.get(1).get(1));
        test.add(groupings.get(2).get(0));
        test.add(groupings.get(2).get(1));

        Assert.assertEquals(test.size(),6);
        Assert.assertEquals(test.contains("s1"), true);
        Assert.assertEquals(test.contains("s2"), true);
        Assert.assertEquals(test.contains("s3"), true);
        Assert.assertEquals(test.contains("s4"), true);
        Assert.assertEquals(test.contains("s5"), true);
        Assert.assertEquals(test.contains("s6"), true);

        //groups of three
        List<List<String>> groupings2 = obj.suggestGroup(graphs, 3);

        Assert.assertEquals(groupings2.size(), 2);
        Assert.assertEquals(groupings2.get(0).size(),3);
        Assert.assertEquals(groupings2.get(1).size(),3);
        Assert.assertNotEquals(groupings2.get(0).get(0), groupings2.get(0).get(1), groupings2.get(0).get(2));
        Assert.assertNotEquals(groupings2.get(1).get(0), groupings2.get(1).get(1), groupings2.get(1).get(2));


    }


    @Test
    public void groupTestRealData() {
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {
            ckc = new ConceptKnowledgeCalculator("resources/comp220/comp220Graph.json","resources/comp220/comp220Resources.json","localresources/comp220/comp220ExampleDataPortion.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new RandomGroupSuggester();

        //groups of two
        List<List<String>> groupings = obj.suggestGroup(graphs, 3);

        Assert.assertEquals(groupings.size(), 13);


        List<List<String>> groupings2 = obj.suggestGroup(graphs, 2);
        Assert.assertEquals(groupings2.size(), 19);


//
        GroupSuggester group = new SuggestionGroupSuggester();

        List<List<String>> groupings3 = group.suggestGroup(graphs, 3);
        Assert.assertEquals(groupings3.size(), 13);


        List<List<String>> groupings4 = group.suggestGroup(graphs, 2);
        Assert.assertEquals(groupings4.size(), 19);

    }



    @Test
    public void suggestionGroupTestOddStudents() {
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {

            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/researchConceptGraph.json", "test/testresources/ManuallyCreated/researchResource2.json", "test/testresources/ManuallyCreated/researchAssessment2.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new SuggestionGroupSuggester();

        //groups of two
        List<List<String>> groupings = obj.suggestGroup(graphs, 2);

        Assert.assertEquals(groupings.size(),3);

        Assert.assertEquals(groupings.get(0).size(), 3);
        Assert.assertEquals(groupings.get(1).size(), 3);

        Assert.assertEquals(groupings.get(0).get(0),"s4" );
        Assert.assertEquals(groupings.get(0).get(1),"s5" );
        Assert.assertEquals(groupings.get(0).get(2),"something challenging" );

        Assert.assertEquals(groupings.get(1).get(0),"s3" );
        Assert.assertEquals(groupings.get(1).get(1),"s2" );
        Assert.assertEquals(groupings.get(1).get(2),"What are values are accessed by?" );

        Assert.assertEquals(groupings.get(2).get(0),"s1" );
        Assert.assertEquals(groupings.get(2).get(1),"No other students" );


        List<List<String>> groupings2 = obj.suggestGroup(graphs, 3);

        Assert.assertEquals(groupings2.size(),2);
        Assert.assertEquals(groupings2.get(0).size(), 4);
        Assert.assertEquals(groupings2.get(0).get(0), "s4");
        Assert.assertEquals(groupings2.get(0).get(1), "s5");
        Assert.assertEquals(groupings2.get(0).get(2), "s1");
        Assert.assertEquals(groupings2.get(0).get(3), "something challenging");

        Assert.assertEquals(groupings2.get(1).size(), 3);
        Assert.assertEquals(groupings2.get(1).get(0), "s3");
        Assert.assertEquals(groupings2.get(1).get(1), "s2");
        Assert.assertEquals(groupings2.get(1).get(2), "No other students");

    }

    @Test
    public void suggestionGroupTestEvenStudents() {
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {
            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/researchConceptGraph.json", "test/testresources/ManuallyCreated/researchResource1.json", "test/testresources/ManuallyCreated/researchAssessment1.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new SuggestionGroupSuggester();

        //groups of two
        List<List<String>> groupings = obj.suggestGroup(graphs, 2);

        Assert.assertEquals(groupings.size(),3);
        Assert.assertEquals(groupings.get(0).get(0), "s3");
        Assert.assertEquals(groupings.get(0).get(1), "s5");
        Assert.assertEquals(groupings.get(1).get(0), "s1");
        Assert.assertEquals(groupings.get(1).get(1), "s2");
        Assert.assertEquals(groupings.get(2).get(0), "s6");
        Assert.assertEquals(groupings.get(2).get(1), "s4");

        List<List<String>> groupings2 = obj.suggestGroup(graphs, 3);

        Assert.assertEquals(groupings2.size(),2);

        Assert.assertEquals(groupings2.get(0).size(), 4);
        Assert.assertEquals(groupings2.get(0).get(0), "s3");
        Assert.assertEquals(groupings2.get(0).get(1), "s5");
        Assert.assertEquals(groupings2.get(0).get(2), "s2");
        Assert.assertEquals(groupings2.get(0).get(3), "How are while loops and booleans related?");

        Assert.assertEquals(groupings2.get(1).size(), 4);
        Assert.assertEquals(groupings2.get(1).get(0), "s1");
        Assert.assertEquals(groupings2.get(1).get(1), "s6");
        Assert.assertEquals(groupings2.get(1).get(2), "s4");
        Assert.assertEquals(groupings2.get(1).get(3), "Random pairing");
    }

    @Test
    public void suggestionGroupTestLessLeftOverStudents() {
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {
            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/basicRealisticConceptGraph.json", "test/testresources/ManuallyCreated/basicRealisticResource.json", "test/testresources/ManuallyCreated/basicRealisticAssessment.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new SuggestionGroupSuggester();

        //groups of two
        List<List<String>> groupings = obj.suggestGroup(graphs, 2);

        Assert.assertEquals(groupings.size(),1);
        Assert.assertEquals(groupings.get(0).size(), 2);
        Assert.assertEquals(groupings.get(0).get(0), "bspinache1");
        Assert.assertEquals(groupings.get(0).get(1), "No other students");


        List<List<String>> groupings2 = obj.suggestGroup(graphs, 3);
        Assert.assertEquals(groupings2.size(), 1);
        Assert.assertEquals(groupings2.get(0).size(), 2);
        Assert.assertEquals(groupings2.get(0).get(0), "bspinache1");
    }



    @Test
    public void graphGroupSuggestion(){
        ConceptKnowledgeCalculatorAPI ckc = null;

        try {
            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/researchConceptGraph.json", "test/testresources/ManuallyCreated/researchResource1.json", "test/testresources/ManuallyCreated/researchAssessment1.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        CohortConceptGraphs graphs = ckc.getCohortConceptGraphs();
        Assert.assertNotEquals(graphs, null);


        GroupSuggester obj = new KnowedgeEstimateGroupSuggester();
        List<List<String>> groupings = obj.suggestGroup(graphs, 2);

        Assert.assertEquals(groupings.size(),3 );
//        Assert.assertEquals(groupings.get(0).size(), );
//        Assert.assertEquals(groupings.get(0).size(), );
//        Assert.assertEquals(groupings.get(0).size(), );


    }
}