public class SimpleCaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;

    SimpleCaesarCipher(int key) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
    }

    SimpleCaesarCipher(int firstKey, int secondKey) {
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabetFromFirstKey = alphabet.substring(firstKey) + alphabet.substring(0, firstKey);
        String shiftedAlphabetFromSecondKey = alphabet.substring(secondKey) + alphabet.substring(0, secondKey);
    }

    String caesarCipher(StringBuilder encrypted, String alphabet, int index, String shiftedAlphabet ) {
        char currChar = encrypted.charAt(index);

        if (Character.isLowerCase(currChar)) {
            int idx = alphabet.toLowerCase().indexOf(currChar);

            if (idx != -1) {
                char newChar = shiftedAlphabet.toLowerCase().charAt(idx);
                encrypted.setCharAt(index, newChar);
            }
        } else {
            int idx = alphabet.indexOf(currChar);

            if (idx != -1) {
                char newChar = shiftedAlphabet.charAt(idx);
                encrypted.setCharAt(index, newChar);
            }
        }
        return encrypted.toString();
    }

    String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder(input);

        for(int i = 0; i < encrypted.length(); i++) {
            caesarCipher(encrypted, alphabet, i, shiftedAlphabet);
        }
        return encrypted.toString();
    }

    String encryptTwoKeys(String input, int firstKey, int secondKey) {
        StringBuilder encrypted = new StringBuilder(input);
        String shiftedAlphabetFromFirstKey = alphabet.substring(firstKey) + alphabet.substring(0, firstKey);
        String shiftedAlphabetFromSecondKey = alphabet.substring(secondKey) + alphabet.substring(0, secondKey);

        for (int i = 0; i < encrypted.length(); i++) {
            if (i % 2 == 0)
                caesarCipher(encrypted, alphabet, i, shiftedAlphabetFromFirstKey);
            else
                caesarCipher(encrypted, alphabet, i, shiftedAlphabetFromSecondKey);
        }
        return encrypted.toString();
    }

    public static void main(String[] args) {
        int key = 15;
        SimpleCaesarCipher cipher = new SimpleCaesarCipher(15);
        String message = "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        String encrypted = cipher.encrypt(message);
        System.out.println(encrypted);
        System.out.println();
        System.out.println(cipher.encryptTwoKeys("At noon be in the conference room with your hat on for a " +
                                                "surprise party. YELL LOUD!", 8, 21));
    }
}