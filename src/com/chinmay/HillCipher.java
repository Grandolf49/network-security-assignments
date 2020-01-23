package com.chinmay;

import java.util.Scanner;

/**
 * A program to encrypt a message using Hill Cipher
 *
 * @author grandolf49
 */
public class HillCipher {

    public static final int KEY_SIZE = 2;
    public static final int ALPHABET_LIMIT = 26;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Accept the message
        System.out.println("Enter the message (only 2 chars):");
        String message = scanner.nextLine();

        // Accept the key matrix
        System.out.println("Enter the key matrix (only 4 elements):");
        int[][] keyMatrix = new int[KEY_SIZE][KEY_SIZE];

        for (int i = 0; i < KEY_SIZE; i++) {
            for (int j = 0; j < KEY_SIZE; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }
        scanner.nextLine();

        // Encrypt the message using Hill Cipher
        String encryptedMessage = hillCipherEncryption(message, keyMatrix);
        System.out.println("Encrypted message is: " + encryptedMessage);

        // Decrypt the encrypted string
        String decryptedMessage = hillCipherDecryption(encryptedMessage, keyMatrix);
        System.out.println("Decrypted message is: " + decryptedMessage);
    }

    /**
     * A function to encrypt a message using HilL Cipher Encryption
     */
    private static String hillCipherEncryption(String message, int[][] keyMatrix) {
        StringBuilder encryptedMessage = new StringBuilder();
        int[] messageMatrix = new int[message.length()];
        int[] encryptedMatrix = new int[message.length()];

        // Initialize message matrix
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            messageMatrix[i] = c - 'a';
        }

        // Multiply messageMatrix and KeyMatrix
        for (int i = 0; i < KEY_SIZE; i++) {
            int element = 0;
            for (int j = 0; j < KEY_SIZE; j++) {
                element += messageMatrix[j] * keyMatrix[j][i];
            }
            encryptedMatrix[i] = element % ALPHABET_LIMIT;
            char new_c = (char) (encryptedMatrix[i] + 'a');
            encryptedMessage.append(new_c);
        }

        return encryptedMessage.toString();
    }

    /**
     * A function to decrypt an encrypted message using HilL Cipher Encryption
     */
    private static String hillCipherDecryption(String encryptedMessage, int[][] keyMatrix) {
        String decryptedMessage = "";
        int[] encrpytedMatrix = new int[encryptedMessage.length()];

        // Initialize encrypted matrix
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char c = encryptedMessage.charAt(i);
            encrpytedMatrix[i] = c - 'a';
        }


        return decryptedMessage;
    }

}
