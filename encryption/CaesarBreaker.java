import edu.duke.*;
public class CaesarBreaker {
    int maxIndex(int[] values) {
        int maximumIndex = 0;

        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maximumIndex])
                maximumIndex = i;
        }
        return maximumIndex;
    }

    int[] countLetters(String message) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int[] counts = new int[26];

        for (int i = 0; i < message.length(); i++) {
            char character = Character.toUpperCase(message.charAt(i));
            int index = alphabet.indexOf(character);

            if (index != -1)
                counts[index]++;
        }
        return counts;
    }

    int getKey(String message) {
        int[] letterFrequencies = countLetters(message);
        int maxIndex = maxIndex(letterFrequencies);

        int dKey = maxIndex - 4;

        if (maxIndex < 4)
            dKey = 26 - (4 - maxIndex);

        return dKey;
    }

    String halfOfString(String message, int startIndex) {
        StringBuilder halfString = new StringBuilder();

        for (int i = startIndex; i < message.length(); i += 2) {
            halfString.append(message.charAt(i));
        }

        return halfString.toString();
    }

    String decrypt(String encrypted) {
        int[] frequencies = countLetters(encrypted);
        int maxIndex = maxIndex(frequencies);
        int dKey = maxIndex - 4;

        if (maxIndex < 4)
            dKey = 26 - (4 - maxIndex);

        SimpleCaesarCipher cipher = new SimpleCaesarCipher(26 - dKey);

        return cipher.encrypt(encrypted);
    }

    String decryptTwoKeys(String encrypted) {

        String firstHalfString = halfOfString(encrypted, 0);
        String secondHalfString = halfOfString(encrypted, 1);

        int firstHalfKey = getKey(firstHalfString);
        int secondHalfKey = getKey(secondHalfString);

        SimpleCaesarCipher doubleCipher = new SimpleCaesarCipher(firstHalfKey, secondHalfKey);

        System.out.println("Key 1 is " + firstHalfKey);
        System.out.println("Key 2 is " + secondHalfKey);
        System.out.println("\nThe decrypted message is:");

        return doubleCipher.encryptTwoKeys(encrypted, 26 - firstHalfKey, 26 - secondHalfKey);
    }

    public static void main(String[] args) {
        FileResource file = new FileResource();
        String message = file.asString();

        CaesarBreaker breaker = new CaesarBreaker();
        System.out.println(breaker.decryptTwoKeys(message));
    }
}
