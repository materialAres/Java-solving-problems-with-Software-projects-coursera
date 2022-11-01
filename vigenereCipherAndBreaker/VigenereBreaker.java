import java.io.File;
import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slicedMessage = new StringBuilder();

        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slicedMessage.append(message.charAt(i));
        }

        return slicedMessage.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        // Finds the keys used to encrypt the message and store them in an array
        // decrypting each slice separately
        int[] key = new int[klength];
        CaesarCracker cracker = new CaesarCracker();

        for (int i = 0; i < klength; i++) {
            String slicedMessage = sliceString(encrypted, i, klength);
            int currentKey = cracker.getKey(slicedMessage);
            key[i] = currentKey;
        }

        return key;
    }

    public HashSet<String> readDictionary(FileResource file) {
        HashSet<String> dictionary = new HashSet<>();

        for (String word : file.lines()) {
            dictionary.add(word.toLowerCase());
        }
        return dictionary;
    }

    public HashMap<String, HashSet<String>> readMultipleDictionaries(DirectoryResource files) {
        HashMap<String, HashSet<String>> languages = new HashMap<>();

        for (File file : files.selectedFiles()) {
            FileResource dictionary = new FileResource(file);
            String language = file.getName();

            languages.put(language, readDictionary(dictionary));
        }
        return languages;
    }

    public int countWords(String message, HashSet<String> dictionary) {
        String[] wordsInMessage = message.split("\\W+");
        int realWordsCounter = 0;

        for (String word : wordsInMessage) {
            if (dictionary.contains(word.toLowerCase())) {
                realWordsCounter++;
            }
        }
        return realWordsCounter;
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {
        // returns the most common character in a dictionary of a chosen language
        HashMap<Character, Integer> charactersOccurrence = new HashMap<>();
        char mostFrequentCharacter = ' ';
        int maxFrequencies = Integer.MIN_VALUE;

        for (String word : dictionary) {
            word = word.toLowerCase();

            for (int i = 0; i < word.length(); i++) {
                char currentChar = word.charAt(i);
                if (!charactersOccurrence.containsKey(currentChar)) {
                    charactersOccurrence.put(currentChar, 1);
                } else {
                    charactersOccurrence.put(currentChar, charactersOccurrence.get(currentChar) + 1);
                }
            }
        }

        for (Map.Entry<Character, Integer> entry : charactersOccurrence.entrySet()) {
            char currentCharacter = entry.getKey();
            int currentFrequency = entry.getValue();

            if (currentFrequency > maxFrequencies) {
                maxFrequencies = currentFrequency;
                mostFrequentCharacter = currentCharacter;
            }
        }
        return mostFrequentCharacter;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        VigenereBreaker breaker = new VigenereBreaker();
        int largestCountOfRealWords = Integer.MIN_VALUE;
        int decryptionKeyLength = 0;
        String decryptedMessage = "";
        char mostFrequentCharacterInLanguage = breaker.mostCommonCharIn(dictionary);

        for (int i = 1; i <= 100; i++) {
            int[] keys = breaker.tryKeyLength(encrypted, i, mostFrequentCharacterInLanguage);
            VigenereCipher cipher = new VigenereCipher(keys);
            String possibleDecrypted = cipher.decrypt(encrypted);

            int realWordsCounter = breaker.countWords(possibleDecrypted, dictionary);

            if (realWordsCounter > largestCountOfRealWords) {
                largestCountOfRealWords = realWordsCounter;
                decryptedMessage = possibleDecrypted;
                decryptionKeyLength = i;
            }
        }
        return decryptedMessage + "\n--------------------\nThe decryption key length is: " + decryptionKeyLength
                + "\n--------------------\nValid words: " + largestCountOfRealWords;
    }

    public String breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages) {
        VigenereBreaker breaker = new VigenereBreaker();
        int largestCountOfRealWords = Integer.MIN_VALUE;
        String decryptedMessage = null;
        String decryptionLanguage = null;

        for (String language : languages.keySet()) {
            HashSet<String> currentDictionary = languages.get(language);
            String possibleDecrypted = breaker.breakForLanguage(encrypted, currentDictionary);
            int realWordsCounter = breaker.countWords(possibleDecrypted, currentDictionary);

            if (realWordsCounter > largestCountOfRealWords) {
                largestCountOfRealWords = realWordsCounter;
                decryptedMessage = possibleDecrypted;
                decryptionLanguage = language;
            }
        }
        return decryptedMessage + "\n--------------------\n"
                + "Original language of the decrypted file: " + decryptionLanguage;
    }

    public void breakVigenere () {
        VigenereBreaker breaker = new VigenereBreaker();

        FileResource file = new FileResource();
        String text = file.asString();

        FileResource dictionary = new FileResource("dictionaries/English");
        HashSet<String> dictionarySet = breaker.readDictionary(dictionary);

        int[] keys = breaker.tryKeyLength(text, 4, 'e');

        String decrypted = breaker.breakForLanguage(text, dictionarySet);

        //VigenereCipher cipher = new VigenereCipher(keys);
        //System.out.println(cipher.decrypt(text));
        System.out.println(decrypted);
    }

    public static void main(String[] args) {
        VigenereBreaker breaker = new VigenereBreaker();
        FileResource file = new FileResource();
        DirectoryResource files = new DirectoryResource();
        String text = file.asString();
        //HashSet<String> dictionary = breaker.readDictionary(fileDic);
        //String text = file.asString();

        //System.out.println(breaker.sliceString("abcdefghijklm", 2, 4));
        //breaker.breakVigenere();
        //System.out.println(breaker.mostCommonCharIn(dictionary));
        System.out.println(breaker.breakForAllLanguages(text, breaker.readMultipleDictionaries(files)));
    }
}
