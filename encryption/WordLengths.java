import edu.duke.FileResource;

import java.util.Arrays;

public class WordLengths {
    int[] countWordLengths(FileResource resource, int[] counts) {
        for (String word : resource.words()) {
            int charactersCounter = 0;

            for (int i = 0; i < word.length(); i++) {
                if (Character.isLetter(word.charAt(i)))
                    charactersCounter++;
                }

                if (charactersCounter < counts.length - 1)
                    counts[charactersCounter]++;
                else
                    counts[counts.length - 1]++;
        }
        return counts;
    }

    int indexOfMax(int[] values) {
        FileResource resource = new FileResource("data/smallHamlet.txt");
        int numberOfWordsWithLength = Integer.MIN_VALUE;
        int mostFrequentWordLength = 0;
        values = countWordLengths(resource, values);

        for (int i = 0; i < values.length; i++) {
            if (values[i] > numberOfWordsWithLength) {
                numberOfWordsWithLength = values[i];
                mostFrequentWordLength = i;
            }
        }
        return mostFrequentWordLength;
    }

    void testCountWordLengths() {
        FileResource resource = new FileResource("data/lotsOfWords.txt");
        int[] counts = new int[31];
        counts = countWordLengths(resource, counts);

        for (int i = 0; i < counts.length; i++) {
            if (counts[i] != 0)
                if (counts[i] == 1)
                    System.out.println("There is " + counts[i] + " word of length " + i);
                else
                    System.out.println("There are " + counts[i] + " words of length " + i);
        }
        int mostFrequentWordLength = indexOfMax(counts);
        System.out.println("\nThe most frequent word length is " + mostFrequentWordLength);
    }

    public static void main(String[] args) {
        WordLengths wl = new WordLengths();
        wl.testCountWordLengths();
    }
}
