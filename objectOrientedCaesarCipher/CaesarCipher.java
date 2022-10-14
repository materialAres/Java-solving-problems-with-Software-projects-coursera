public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;

    CaesarCipher(int key) {
        this.mainKey = key;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
    }

    String encrypt(StringBuilder encrypted, String alphabet, int index, String shiftedAlphabet) {
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

    String getEncryptedMessage(String input) {
        StringBuilder encrypted = new StringBuilder(input);

        for(int i = 0; i < encrypted.length(); i++) {
            encrypt(encrypted, alphabet, i, shiftedAlphabet);
        }
        return encrypted.toString();
    }

    String decrypt(String encrypted) {
        CaesarCipher cipher = new CaesarCipher(26 - mainKey);
        return cipher.getEncryptedMessage(encrypted);
    }
}
