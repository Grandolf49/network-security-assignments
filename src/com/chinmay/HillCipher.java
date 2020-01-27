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
        System.out.println("Enter the message:");
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
     * A function to decrypt an encrypted message using HilL Cipher Decryption
     */
    private static String hillCipherDecryption(String encryptedMessage, int[][] keyMatrix) {
        StringBuilder decryptedString = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i += 2) {
            String dec_sub_str = hillCipherDecryptSubString(encryptedMessage.substring(i, i + 2), keyMatrix);
            decryptedString.append(dec_sub_str);
        }

        return decryptedString.toString();
    }

    /**
     * A function to encrypt a message using HilL Cipher Encryption
     */
    private static String hillCipherEncryption(String message, int[][] keyMatrix) {
        StringBuilder encryptedString = new StringBuilder();
        String modifiedMessage = PlayFairCipher.constructMessage(message);
        System.out.println("Modified message: " + modifiedMessage);

        for (int i = 0; i < modifiedMessage.length(); i += 2) {
            String enc_sub_str = hillCipherEncryptSubString(modifiedMessage.substring(i, i + 2), keyMatrix);
            encryptedString.append(enc_sub_str);
        }

        return encryptedString.toString();
    }

    /**
     * A function to encrypt strings of type [ab] 2 chars only
     */
    private static String hillCipherEncryptSubString(String message, int[][] keyMatrix) {
        StringBuilder encryptedMessage = new StringBuilder();
        int[] messageMatrix = new int[message.length()];
        int[] encryptedMatrix = new int[message.length()];

        // Initialize message matrix
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            messageMatrix[i] = c - 'a';
        }

        // Multiply messageMatrix and keyMatrix
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
     * A function to decrypt strings of type [ab]
     */
    private static String hillCipherDecryptSubString(String encryptedMessage, int[][] keyMatrix) {
        StringBuilder decryptedMessage = new StringBuilder();
        int[] encryptedMatrix = new int[encryptedMessage.length()];
        int[] decryptedMatrix = new int[encryptedMessage.length()];

        // Initialize encrypted matrix
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char c = encryptedMessage.charAt(i);
            encryptedMatrix[i] = c - 'a';
        }

        // Create inverse matrix
        int[][] inverseMatrix = new int[KEY_SIZE][KEY_SIZE];
        inverseMatrix[0][0] = keyMatrix[1][1];
        inverseMatrix[0][1] = -1 * keyMatrix[0][1];
        inverseMatrix[1][0] = -1 * keyMatrix[1][0];
        inverseMatrix[1][1] = keyMatrix[0][0];

        // Calculate determinant value
        int determinant = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
        if (determinant < 0) {
            determinant *= -1;
            for (int i = 0; i < KEY_SIZE; i++) {
                for (int j = 0; j < KEY_SIZE; j++) {
                    inverseMatrix[i][j] *= -1;
                }
            }
        }

        // Calculate multiplicative inverse wrt modulo 26
        int mul_inv = getMultiplicativeInverse(determinant);

        // Multiply the multiplicative inverse with the encrypted matrix
        for (int i = 0; i < KEY_SIZE; i++) {
            encryptedMatrix[i] = (encryptedMatrix[i] * mul_inv) % ALPHABET_LIMIT;
        }

        // Calculate final decrypted matrix
        for (int i = 0; i < KEY_SIZE; i++) {
            int element = 0;
            for (int j = 0; j < KEY_SIZE; j++) {
                element += encryptedMatrix[j] * inverseMatrix[j][i];
            }

            decryptedMatrix[i] = (element % ALPHABET_LIMIT + ALPHABET_LIMIT) % ALPHABET_LIMIT;
            char new_c = (char) (decryptedMatrix[i] + 'a');
            decryptedMessage.append(new_c);
        }
        return decryptedMessage.toString();
    }

    /**
     * A function to calculate multiplicative inverse of a number wrt another
     */
    private static int getMultiplicativeInverse(int determinant) {
        int a = determinant % ALPHABET_LIMIT;
        int result = 1;
        for (int i = 1; i < ALPHABET_LIMIT; i++) {
            if ((a * i) % ALPHABET_LIMIT == 1) {
                result = i;
                break;
            }
        }
        return result;
    }
}
