package com.chinmay;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A program to encrypt a message using Vigenere Cipher and decrypt using Brute Force Algorithm
 *
 * @author grandolf49
 */
public class VigenereCipher {

    static ArrayList<String> stringArrayList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Accept the message as input
        System.out.println("Enter a message string:");
        String message = scan.nextLine();

        // Accept the key to use for encryption
        System.out.println("Enter a key for encryption [a-z]:");
        String key = scan.nextLine();

        // Encrypt the message using the key
        String encryptedString = vigenereCipherEncryption(message, key);
        System.out.println("Encrypted message is: " + encryptedString);

        // Decrypt the message using Brute Force
        System.out.println("Applying Brute Force for Decryption...");
        System.out.println("Generating all combinations of keys...");
        generateAllStrings("", key.length() % message.length());
        System.out.println();
        String decryptedString = bruteForceVigenereCipher(encryptedString, message);
        System.out.println("Decrypted message is: " + decryptedString);

    }

    private static String bruteForceVigenereCipher(String encryptedString, String message) {
        String decryptedMessage = "-1";
        for (String key : stringArrayList) {
            String tempMessage = vigenereCipherDecryption(encryptedString, key);
            System.out.println(tempMessage);
            if (tempMessage.equals(message)) {
                System.out.println("Cipher decrypted successfully!");
                System.out.println("Encryption key detected: " + key);
                decryptedMessage = tempMessage;
                break;
            }
        }
        return decryptedMessage;
    }

    /**
     * A function to decrypt a message with a key
     */
    private static String vigenereCipherDecryption(String encryptedString, String key) {
        StringBuilder decryptedMessage = new StringBuilder();
        char a = 'a';
        for (int i = 0; i < encryptedString.length(); i++) {
            char cur_char = encryptedString.charAt(i);
            int cur_key = key.charAt(i % key.length()) - a + 1;
            char enc_char = (char) ((cur_char - a - cur_key + 26) % 26 + a);
            decryptedMessage.append(enc_char);
        }
        return decryptedMessage.toString();
    }

    /**
     * A function to encrypt a message with a key
     */
    private static String vigenereCipherEncryption(String message, String key) {
        StringBuilder encryptedString = new StringBuilder();
        char a = 'a';
        if (key.length() > message.length()) {
            key = key.substring(0, message.length());
        }
        for (int i = 0; i < message.length(); i++) {
            char cur_char = message.charAt(i);
            int cur_key = key.charAt(i % key.length()) - a + 1;
            char enc_char = (char) ((cur_char - a + cur_key) % 26 + a);
            encryptedString.append(enc_char);
        }
        return encryptedString.toString();
    }

    /**
     * A function to generate all possible combinations of strings of length n
     */
    private static void generateAllStrings(String prefix, int n) {
        if (prefix.length() == n) {
            stringArrayList.add(prefix);
        } else {
            for (char a = 'a'; a < 'z'; a++) {
                generateAllStrings(prefix + a, n);
            }
        }
    }
}
