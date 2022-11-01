import edu.duke.*;

import java.io.File;
import java.util.*;

public class VigenereCipher {
    NewCaesarCipher[] ciphers;

    public VigenereCipher(int[] key) {
        ciphers = new NewCaesarCipher[key.length];
        for (int i = 0; i < key.length; i++) {
            ciphers[i] = new NewCaesarCipher(key[i]);
        }
    }

    public String encrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            NewCaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.encryptLetter(c));
            i++;
        }
        return answer.toString();
    }

    public String decrypt(String input) {
        StringBuilder answer = new StringBuilder();
        int i = 0;
        for (char c : input.toCharArray()) {
            int cipherIndex = i % ciphers.length;
            NewCaesarCipher thisCipher = ciphers[cipherIndex];
            answer.append(thisCipher.decryptLetter(c));
            i++;
        }
        return answer.toString();
    }

    public String toString() {
        return Arrays.toString(ciphers);
    }

    public static void main(String[] args) {
        int[] romeKey = {17, 14, 12, 4};
        VigenereCipher cipher = new VigenereCipher(romeKey);
        String text = new FileResource().asString();
        String decrypted = cipher.encrypt(text);

        System.out.println(decrypted + "\n------------------------------------------");
        System.out.println("Decrypted message: ");
        System.out.println(cipher.decrypt(decrypted));
    }

}
