import edu.duke.FileResource;

import java.util.HashMap;
import java.util.Map;

public class CodonCount {
    HashMap<String, Integer> dnaCodons;

    CodonCount() {
        dnaCodons = new HashMap<>();
    }

    void buildCodonMap(int start, String dna) {
        for (int i = start; i < dna.length(); i += 3) {
            if (!(i + 3 > dna.length())) {
                String currentCodon = dna.substring(i, i + 3);

                if (Character.isLetter(currentCodon.charAt(2))) {
                    if (!dnaCodons.containsKey(currentCodon)) {
                        dnaCodons.put(currentCodon, 1);
                    } else {
                        dnaCodons.put(currentCodon, dnaCodons.get(currentCodon) + 1);
                    }
                }
            }
        }
    }

    String getMostCommonCodon() {
        StringBuilder mostCommonCodons = new StringBuilder();
        int largestCountOfCodons = Integer.MIN_VALUE;

        for (int countOfCodon : dnaCodons.values()) {
            if (countOfCodon > largestCountOfCodons)
                largestCountOfCodons = countOfCodon;
        }

        for (Map.Entry<String, Integer> entry : dnaCodons.entrySet()) {
            if (entry.getValue() == largestCountOfCodons) {
                mostCommonCodons.append(entry.getKey() + ", ");
            }
        }

        return mostCommonCodons.toString();
    }

    void printCodonCounts(int start, int end) {
        for (Map.Entry<String, Integer> entry : dnaCodons.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        FileResource file = new FileResource();
        String dna = file.asString();
        CodonCount sampleProgram = new CodonCount();
        sampleProgram.dnaCodons.clear();
        sampleProgram.buildCodonMap(0, dna);
        //sampleProgram.buildCodonMap(1, dna);
        //sampleProgram.buildCodonMap(2, dna);

        System.out.println(sampleProgram.getMostCommonCodon());
        sampleProgram.printCodonCounts(1, 5);
    }
}
