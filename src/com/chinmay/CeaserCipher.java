package com.chinmay;

import java.util.Scanner;

/**
 * A program to encrypt a message using Ceaser Cipher and decrypt using Brute Force Algorithm
 *
 * @author grandolf49
 */
public class CeaserCipher {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Accept the message as input
        System.out.println("Enter a message string:");
        String message = scan.nextLine();

        // Accept the key to use for encryption
        System.out.println("Enter a key for encryption:");
        int key = scan.nextInt();
        scan.nextLine();

        // Encrypt the message using the key
        String encryptedString = ceaserCipherEncryption(message, key);
        System.out.println("Encrypted string is: " + encryptedString);

        // Decrypt the encrypted string to generate original message

        /*System.out.println("Applying Brute Force for Decryption...");
        String decryptedString = bruteForceCeaserCipher(encryptedString, message);
        System.out.println("The original message is: " + decryptedString);
        */

    }

    private static String bruteForceCeaserCipher(String encryptedString, String message) {
        boolean isMessageDecrypted = false;
        int testKey = 0;
        String decryptedString = "";

        while (!isMessageDecrypted) {
            testKey++;
            decryptedString = ceaserCipherDecryption(encryptedString, testKey);
            if (decryptedString.equals(message)) {
                isMessageDecrypted = true;
            }
        }

        return decryptedString;
    }

    private static String ceaserCipherDecryption(String encryptedString, int testKey) {
        String decryptedString = "";

        return decryptedString;
    }

    private static String ceaserCipherEncryption(String message, int key) {
        StringBuilder encryptedString = new StringBuilder();
        char a = 'a', z = 'z';
        for (int i = 0; i < message.length(); i++) {
            char cur_char = message.charAt(i);
            char enc_char = (char)((cur_char - a + key) % 26 + a);

            encryptedString.append(enc_char);
        }
        return encryptedString.toString();
    }
}
