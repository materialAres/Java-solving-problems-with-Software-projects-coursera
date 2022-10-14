import edu.duke.FileResource;

import java.util.ArrayList;

public class CharactersInPlay {
    private ArrayList<String> characters;
    private ArrayList<Integer> speakingFrequency;

    CharactersInPlay() {
        characters = new ArrayList<String>();
        speakingFrequency = new ArrayList<Integer>();
    }

    void update(String person){
        person = person.toLowerCase();
        int index = characters.indexOf(person);

        if (index == -1) {
            characters.add(person);
            speakingFrequency.add(1);
        }
        else {
            int freq = speakingFrequency.get(index);
            speakingFrequency.set(index,freq + 1);
        }
    }

    void findAllCharacters() {
        FileResource resource = new FileResource();

        for(String line : resource.lines()) {
            if (line.contains(".")) {
                line = line.substring(0, line.indexOf("."));
                update(line);
            }
        }
    }

    void charactersWithNumParts(int firstNumber, int secondNumber) {
        int number = 3;

        if (number >= firstNumber && number <= secondNumber) {
            System.out.println(characters.get(speakingFrequency.get(number)));
        }
    }

    void tester() {
        findAllCharacters();

        for (int i = 0; i < characters.size(); i++) {
            if (speakingFrequency.get(i) > 9)
                System.out.println(characters.get(i) + " occurred " + speakingFrequency.get(i) + " times\n");
        }

        charactersWithNumParts(1, 5);
    }

    public static void main(String[] args) {
        CharactersInPlay sampleProgram = new CharactersInPlay();
        sampleProgram.tester();
    }
}
