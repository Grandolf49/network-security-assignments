package com.chinmay;

import java.util.Scanner;

/**
 * A program to encrypt a message using Vigenere Cipher and decrypt using Brute Force Algorithm
 *
 * @author grandolf49
 */
public class VigenereCipher {

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
    }

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
}
