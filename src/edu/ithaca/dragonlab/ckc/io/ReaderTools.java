package edu.ithaca.dragonlab.ckc.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import edu.ithaca.dragonlab.ckc.learningobject.LearningObject;
import edu.ithaca.dragonlab.ckc.learningobject.LearningObjectResponse;
import edu.ithaca.dragonlab.ckc.learningobject.ManualGradedResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;

/**
 * Created by Ryan on 10/25/2017.
 */
public class ReaderTools {


    public static ArrayList<ArrayList<String>> staticLineToList(String filename){
        ArrayList<ArrayList<String>> lineList = new ArrayList<ArrayList<String>>();
        try {
            String line;
            BufferedReader csvBuffer = new BufferedReader(new FileReader(filename));
            //Takes the file being read in and calls a function to convert each line into a list split at
            //every comma, then pust all the lists returned into a list of lists lineList[line][item in line]
            while ((line = csvBuffer.readLine()) != null) {
                lineList.add(lineToList(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

    public static List<LearningObject> learningObjectsFromList(int indexMark, List<String> singleList) {
        int i = indexMark;
        List<LearningObject> loList = new ArrayList<LearningObject>();
        while(i<singleList.size()){
            String question = singleList.get(i);
            //used to find the max score of a question (won't be affected if there are other brackets in the question title
            int begin = question.lastIndexOf('(');
            int end = question.lastIndexOf(')');

            if (indexMark == 5){
                begin = question.lastIndexOf('(');
                end = question.lastIndexOf(')');
            }
            if (indexMark == 2){
                begin = question.lastIndexOf('['); // important change for the outcome of the exercises
                end = question.lastIndexOf(']');
            }

            if (begin >= 0 && end >= 0) {
                String maxScoreStr = question.substring(begin + 1, end);
                double maxScore = Double.parseDouble(maxScoreStr);
                question = question.substring(0, begin - 1);
                LearningObject learningObject = new LearningObject(question);
                learningObject.setMaxPossibleKnowledgeEstimate(maxScore);
                loList.add(learningObject);
            }
            else {
                //logger.error("No max score found for string:"+question+"\t defaulting to 1, which is probably wrong");
                LearningObject learningObject = new LearningObject(question);
                learningObject.setMaxPossibleKnowledgeEstimate(1);
                loList.add(learningObject);
            }
            i++;
        }
        return loList;
    }

    /**
     * takes a list of csv files and creates a single list of LearningObjects from all files
     * @param csvfiles
     * @return a list of all LearningObjects across all files
     */
    public static List<LearningObject> learningObjectsFromCSVList(int indexMark, List<String> csvfiles){
        List<LearningObject> fullLoList = new ArrayList<LearningObject>();

        //Each csvfile has their LOs searched
        for(String file: csvfiles){
            ArrayList<ArrayList<String>> lineList = ReaderTools.staticLineToList(file);
            List<LearningObject> loList = new ArrayList<LearningObject>();
            loList = ReaderTools.learningObjectsFromList(indexMark,lineList.get(0));

            //adding current csvfile's LOs to the full list of LOs
            for(LearningObject learningObject: loList) {
                fullLoList.add(learningObject);
            }
        }
        return fullLoList;
    }


    public static String pullNumber(String object) {
        String numbers = "";
        int decimal = 0;
        char character = 'a';
        for (int i = 0; i < object.length(); i++){
            character = object.charAt(i);
            if (Character.isDigit(character) && decimal <= 1){
                numbers += character;
            }
            else if (character == '-' && Character.isDigit(object.charAt(i+1)))
                    numbers += character;
            else if (character == '.'){
                decimal += 1;
                numbers += character;
            }
            else if (decimal >= 2){
                return "";
            }
            // else that means there is a second decimal and should return an empty string
        }
        return numbers;
    }

    public static ArrayList<String> lineToList(String line) {
        ArrayList<String> returnlist = new ArrayList<String>();
        String item = "";
        Boolean betweenQuote = false;
        for (int i = 0; i < line.length(); i++){
            if (line.charAt(i) == '"' && betweenQuote == false){
                betweenQuote = true;
            }
            else if (line.charAt(i) == '"' && betweenQuote == true){
                betweenQuote = false;
            }
            else if (line.charAt(i) == ',' && !betweenQuote){
                returnlist.add(item.trim());
                item = "";
            }
            else{
                item += line.charAt(i);
            }
        }
        returnlist.add(item.trim());
        return returnlist;
    }
}