import edu.duke.FileResource;

public class TestCaesarCipher {
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

    public String breakCaesarCipher(String encrypted) {
        int[] frequencies = countLetters(encrypted);
        int maxDex = maxIndex(frequencies);
        int dKey = maxDex - 4;

        if (maxDex < 4)
            dKey = 26 - (4 - maxDex);

        CaesarCipher cipher = new CaesarCipher(26 - dKey);
        System.out.println("Key" + (26 - dKey));

        return cipher.getEncryptedMessage(encrypted);
    }

    public static void main(String[] args) {
        /*FileResource file = new FileResource();
        String message = file.asString();
        CaesarCipher cipherMessage = new CaesarCipher(18);
        TestCaesarCipher testing = new TestCaesarCipher();
        String encryptedMessage = cipherMessage.getEncryptedMessage(message);

        System.out.println("- The encrypted message is:" + "\n" + encryptedMessage + "\n");
        System.out.println("- The decrypted message is:" + "\n" + testing.breakCaesarCipher(encryptedMessage));*/
        CaesarCipher cipherMessage = new CaesarCipher(15);
        System.out.println(cipherMessage.getEncryptedMessage("Can you imagine life WITHOUT the internet AND computers in your pocket?"));

    }
}
