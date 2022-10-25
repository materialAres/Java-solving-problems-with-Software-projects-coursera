import edu.duke.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordsInFiles {
    HashMap<String, ArrayList<String>> wordsMap;

    WordsInFiles() {
        this.wordsMap = new HashMap<>();
    }

    private void addWordsFromFile(File file) {
        // iterate over words in file
        // if word not in wordsMap, then append the filename to arraylist, and do wordsMap -> key=word, value=arraylist
        // if word in wordsMap, and if the filename not in map, add filename to arraylist
        FileResource resource = new FileResource(file);
        String filename = file.getName();

        for (String word : resource.words()) {
            //if (Character.isLetter(word.charAt(word.length() - 1))) {
                if (!wordsMap.containsKey(word)) {
                    ArrayList<String> fileList = new ArrayList<>();
                    fileList.add(filename);
                    wordsMap.put(word, fileList);
                } else {
                    if (!wordsMap.get(word).contains(filename)) {
                        ArrayList<String> currentFileList = wordsMap.get(word);
                        currentFileList.add(filename);
                        wordsMap.put(word, currentFileList);
                    }
                }
           // }
        }
    }

    void buildWordFileMap() {
        wordsMap.clear();
        DirectoryResource directory = new DirectoryResource();

        for (File file : directory.selectedFiles()) {
            addWordsFromFile(file);
        }
    }

    int maxNumberOfFilesForAWord() {
        int maxNumber = Integer.MIN_VALUE;

        for (ArrayList<String> files : wordsMap.values()) {
            int currentNumberOfRepetitionsForWord = files.size();

            if (currentNumberOfRepetitionsForWord > maxNumber) {
                maxNumber = currentNumberOfRepetitionsForWord;
            }
        }

        return maxNumber;
    }

    ArrayList<String> wordsInNumFiles(int number) {
        // arraylist to save strings
        // iterate over map
        // if size of arraylist in map = number, then append its key to new arraylist
        ArrayList<String> words = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : wordsMap.entrySet()) {
            if (entry.getValue().size() == number) {
                words.add(entry.getKey());
            }
        }
        System.out.println();
        return words;
    }

    void printFilesIn(String word) {
        for (Map.Entry<String, ArrayList<String>> entry : wordsMap.entrySet()) {
            if (entry.getKey().equals(word)) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    System.out.println(entry.getValue().get(i));
                }
            }
        }
    }

    public static void main(String[] args) {
        WordsInFiles sampleProgram = new WordsInFiles();
        sampleProgram.buildWordFileMap();
        //System.out.println(sampleProgram.maxNumberOfFilesForAWord());
        //System.out.println(sampleProgram.wordsInNumFiles(4));
        sampleProgram.printFilesIn("tree");
    }
}
