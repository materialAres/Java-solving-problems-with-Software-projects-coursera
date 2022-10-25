import edu.duke.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GladLibMap {
    private HashMap<String, ArrayList<String>> libMap;
    private ArrayList<String> alreadyUsedWords = new ArrayList<>();
    private ArrayList<String> usedLabels = new ArrayList<>();

    private Random myRandom;

    private static final String dataSourceURL = "http://dukelearntoprogram.com/course3/data";

    public GladLibMap() {
        this.libMap = new HashMap<>();
        String dataSourceDirectory = "data";
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }

    private void initializeFromSource(String source) {
        String[] labels = {
                "country", "noun", "verb", "adjective",
                "color", "fruit", "name", "animal", "timeframe"
        };

        for (String label : labels) {
            ArrayList<String> labelList = readIt(source + "/" + label + ".txt");
            libMap.put(label, labelList);
        }
    }

    private String randomFrom(ArrayList<String> source){
        int randomIndex = myRandom.nextInt(source.size());
        return source.get(randomIndex);
    }

    private String getSubstitute(String label) {
        if (label.equals("number"))
            return "" + myRandom.nextInt(50) + 5;
        if (libMap.containsKey(label)) {
            if (!usedLabels.contains(label)) {
                usedLabels.add(label);
            }
            return randomFrom(libMap.get(label));
        }
        return "**UNKNOWN**";
    }

    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }

        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        while (true) {
            if (alreadyUsedWords.contains(sub)) {
                sub = getSubstitute(w.substring(first+1,last));
            } else {
                alreadyUsedWords.add(sub);
                break;
            }
        }
        return prefix+sub+suffix;
    }

    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }

    private String fromTemplate(String source){
        StringBuilder story = new StringBuilder();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story.append(processWord(word)).append(" ");
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story.append(processWord(word)).append(" ");
            }
        }
        return story.toString();
    }

    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }

    public void makeStory(){
        alreadyUsedWords.clear();
        usedLabels.clear();
        // FileResource file = new FileResource();
        System.out.println("\n");
        String story = fromTemplate("randomStory/data/madtemplate3.txt");
        printOut(story, 60);
        System.out.println("\n\nTotal number of replaced words: " + alreadyUsedWords.size() + "\n");
        System.out.println(">>> Total number of words in files: " + totalWordsInMap());
        System.out.println(">>> Total number of words for used labels: " + totalWordsConsidered());
    }

    int totalWordsInMap() {
        int totalWords = 0;

        for (ArrayList<String> words : libMap.values()) {
            totalWords += words.size();
        }
        return totalWords;
    }

    int totalWordsConsidered() {
        // variable which stores the amount
        // iterate over map using labels from usedLabels
        int totalConsideredWords = 0;

        for (Map.Entry<String, ArrayList<String>> entry : libMap.entrySet()) {
            if (usedLabels.contains(entry.getKey()))
                totalConsideredWords += entry.getValue().size();
        }
        return totalConsideredWords;
    }

    public static void main(String[] args) {
        GladLibMap gladLib = new GladLibMap();
        gladLib.makeStory();
    }
}
