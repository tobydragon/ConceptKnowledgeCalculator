package edu.ithaca.dragonlab.ckc.stats;

import edu.ithaca.dragonlab.ckc.ConceptKnowledgeCalculator;
import edu.ithaca.dragonlab.ckc.ConceptKnowledgeCalculatorAPI;
import edu.ithaca.dragonlab.ckc.conceptgraph.CohortConceptGraphs;
import edu.ithaca.dragonlab.ckc.conceptgraph.KnowledgeEstimateMatrix;
import edu.ithaca.dragonlab.ckc.io.CSVReader;
import edu.ithaca.dragonlab.ckc.learningobject.LearningObject;
import edu.ithaca.dragonlab.ckc.ui.ConsoleUI;
import org.junit.Assert;
import org.junit.Test;
import stats.RFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bleblanc2 on 6/19/17.
 */
public class RFunctionsTest {

    @Test
    public void main(){

    }


/**
    @Test
    public void LearningObjectAvgTest(){
        String file = "test/testresources/partialComplexRealitsticAssessment.csv";
        CSVReader data = new CSVReader(file);
        ArrayList<LearningObject> gotoMatrix = data.getManualGradedLearningObjects();
        KnowledgeEstimateMatrix newMatrix = new KnowledgeEstimateMatrix(gotoMatrix);
        double avg = RFunctions.LearningObjectAvg(newMatrix, 2);
        Assert.assertEquals(0.88166, avg, 0.001);
        avg = RFunctions.LearningObjectAvg(newMatrix, 0);
        Assert.assertEquals(.666, avg, 0.001);
        avg = RFunctions.LearningObjectAvg(newMatrix, 8);
        Assert.assertEquals(1, avg, 0.001);
    }

 */
    @Test
    public void studentKnowledgeEstAvgTest(){
        String file = "test/testresources/ManuallyCreated/partialComplexRealitsticAssessment.csv";
        try {
            CSVReader data = new CSVReader(file);
            List<LearningObject> gotoMatrix = data.getManualGradedLearningObjects();
            KnowledgeEstimateMatrix newMatrix = new KnowledgeEstimateMatrix(gotoMatrix);
            double avg = RFunctions.StudentKnowledgeEstAvg(newMatrix, "stu1");
            Assert.assertEquals(.7238, avg, 0.001);
            avg = RFunctions.StudentKnowledgeEstAvg(newMatrix, "stu2");
            Assert.assertEquals(.861, avg, 0.001);
            avg = RFunctions.StudentKnowledgeEstAvg(newMatrix, "stu3");
            Assert.assertEquals(.995, avg, 0.001);
        }catch (Exception e){
            Assert.fail();
        }
    }



    //These two functions below, findFactorCount and getFactorMatrix, are commented out because they are forced to print through R's methods.
    //These tests should be used if getFactorMatrix in ConsoleUI or ConceptKnowledgeCalculator need debugging
    /**
    @Test
    public void findFactorCountTest(){
        try {
            CSVReader data = new CSVReader("test/testresources/ManuallyCreated/complexRealisticAssessment.csv");
            List<LearningObject> gotoMatrix = data.getManualGradedLearningObjects();
            KnowledgeEstimateMatrix newMatrix = new KnowledgeEstimateMatrix(gotoMatrix);

            int factorCount = RFunctions.findFactorCount(newMatrix);
            Assert.assertEquals(2, factorCount, 0);
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }
*/

    //TODO: Make into actual test. Currently commented out in order to not to print output every test
/**
    @Test
    public void getFactorMatrixTest(){

        try{
            CSVReader data = new CSVReader("test/testresources/ManuallyCreated/complexRealisticAssessment.csv");
            List<LearningObject> gotoMatrix = data.getManualGradedLearningObjects();
            KnowledgeEstimateMatrix newMatrix = new KnowledgeEstimateMatrix(gotoMatrix);

            RFunctions.getFactorMatrix(newMatrix);

            /**
            double[][] factorMatrix = RFunctions.getFactorMatrix(newMatrix);


            double newArray[] = new double[factorMatrix.length*factorMatrix[0].length];
            for(int i = 0; i < factorMatrix.length; i++) {
                double[] row = factorMatrix[i];
                for(int j = 0; j < row.length; j++) {
                    double number = factorMatrix[i][j];
                    newArray[i*row.length+j] = number;
                }
             }
            System.out.println(Arrays.toString(newArray));

            double[] arr1 = new double[7];
            double[] arr2 = new double[7];

            for (int i = 0; i < 7; i++) {
                arr1[i] = factorMatrix[i][0];
            }
            //System.out.println(Arrays.toString(arr1));
            //System.out.println("----------");
            for (int i = 0; i < 7; i++) {
                arr2[i] = factorMatrix[i][1];
            }
            System.out.println(Arrays.toString(arr2));

            double[] exArr1 = new double[]{0.6808680, 0.5922147, 0.6842057, 0.8361693, 0.9434352, 0.1443587, 0.2874747};
            double[] exArr2 = new double[]{4648499, 0.5747219, 0.5261558, 0.1391935, 0.2398479, 0.7946735, 0.7936599};

            //Assert.assertArrayEquals(exArr1, arr1, 0.0001);
            //Assert.assertArrayEquals(exArr2, arr2, 0.0001);

        }catch (Exception e){
            e.printStackTrace();
            //Assert.fail();
        }
    }
*/

/**
    @Test
    public void returnFactorMatrixTest(){
        try {
            CSVReader data = new CSVReader("test/testresources/ManuallyCreated/complexRealisticAssessment.csv");
            List<LearningObject> gotoMatrix = data.getManualGradedLearningObjects();
            KnowledgeEstimateMatrix newMatrix = new KnowledgeEstimateMatrix(gotoMatrix);

            double[][] statsMatrix = RFunctions.returnFactorMatrix(newMatrix);
/**
    double newArray[] = new double[factorMatrix.length*factorMatrix[0].length];
    for(int i = 0; i < factorMatrix.length; i++) {
    double[] row = factorMatrix[i];
    for(int j = 0; j < row.length; j++) {
    double number = factorMatrix[i][j];
    newArray[i*row.length+j] = number;
    }
    }
    //System.out.println(Arrays.toString(newArray));

            /**
    int learningObjectCount = RFunctions.getColumnCount(newMatrix);
    int factorCount = RFunctions.findFactorCount(newMatrix);
    int arrayIndex = 0;
    double[][] statsMatrix = new double[learningObjectCount][factorCount];
    for(int i = 0; i < factorCount; i++){
        for(int j = 0; j <learningObjectCount; j++){
            statsMatrix[j][i] = newArray[arrayIndex];
            arrayIndex++;
        }
    }

    double[] arr1 = new double[7];
    double[] arr2 = new double[7];

    for (int i = 0; i < 7; i++) {
    arr1[i] = statsMatrix[i][0];
    }
    //System.out.println("Factor 1: "+ Arrays.toString(arr1));
    //System.out.println("----------");
    for (int i = 0; i < 7; i++) {
    arr2[i] = statsMatrix[i][1];
    }
    //System.out.println("Factor 2: "+ Arrays.toString(arr2));

    double[] exArr1 = new double[]{0.6808680, 0.5922147, 0.6842057, 0.8361693, 0.9434352, 0.1443587, 0.2874747};
    double[] exArr2 = new double[]{0.4648499, 0.5747219, 0.5261558, 0.1391935, 0.2398479, 0.7946735, 0.7936599};

    Assert.assertArrayEquals(exArr1, arr1, 0.0001);
    Assert.assertArrayEquals(exArr2, arr2, 0.0001);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

*/

//TODO: Finish Test
/**
    @Test
    public void modelMakerTest(){
        ConceptKnowledgeCalculatorAPI ckc = null;
        try {
            ckc = new ConceptKnowledgeCalculator("test/testresources/ManuallyCreated/simpleConceptGraph.json",
                    "test/testresources/ManuallyCreated/simpleResource.json",
                    "test/testresources/ManuallyCreated/simpleAssessmentMoreUsers.csv");
        } catch (IOException e) {
            Assert.fail("Unable to load default files");
        }
        CohortConceptGraphs ccg = ckc.getCohortConceptGraphs();
        String modelString = RFunctions.modelMaker(ccg);
        Assert.assertEquals("Actual should not be empty", modelString);
    }
*/

    @Test
    public void getColumnCountTest(){
        try {
            CSVReader data = new CSVReader("test/testresources/ManuallyCreated/complexRealisticAssessment.csv");
            List<LearningObject> gotoMatrix = data.getManualGradedLearningObjects();
            KnowledgeEstimateMatrix newMatrix = new KnowledgeEstimateMatrix(gotoMatrix);
            Assert.assertEquals(7, RFunctions.getColumnCount(newMatrix), 0);
        }catch(Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

}