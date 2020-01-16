package com.chinmay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A program to encrypt a message using PlayFair Cipher
 *
 * @author grandolf49
 */
public class PlayFairCipher {

    private static final int SIZE = 5;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the message:");
        String message = scan.nextLine();

        System.out.println("Enter the keyword");
        String keyword = scan.nextLine();

        String encryptedString = playFairCipherEncryption(message, keyword);
        System.out.println("Encrypted string is: " + encryptedString);
    }

    /**
     * A function to encrypt the message using key
     */
    private static String playFairCipherEncryption(String message, String keyword) {
        StringBuilder encryptedString = new StringBuilder();
        char[][] matrix = new char[SIZE][SIZE];
        int key_idx = 0;
        HashMap<Character, Integer> hashMap = new HashMap<>();

        // Initialize matrix with null char
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = ' ';
            }
        }

        // Initialize matrix with keyword
        for (int i = 0; i < keyword.length(); i++) {
            char key = keyword.charAt(i);
            if (!hashMap.containsKey(key)) {
                hashMap.put(key, 1);
                int r = key_idx / SIZE, c = key_idx % SIZE;
                matrix[r][c] = key;
                key_idx++;
            }
        }

        // Initialize remaining matrix with alphabets
        for (char a = 'a'; a < 'z'; a++) {
            if (!hashMap.containsKey(a)) {
                int r = key_idx / SIZE, c = key_idx % SIZE;
                matrix[r][c] = a;
                key_idx++;
            }
        }
        System.out.println("Cipher Matrix:");
        displayMatrix(matrix);

        // Modify the message so its length is even and no repeated letters in group of 2
        String newMessage = constructMessage(message);
        System.out.println("New message: " + newMessage);

        return encryptedString.toString();
    }

    /**
     * A function to make sure that there are no repeated letters in group of 2 and the length of the message is even
     */
    private static String constructMessage(String message) {
        StringBuilder stringBuilder = new StringBuilder(message);

        for (int i = 0; i < stringBuilder.length(); i += 2) {
            int next_idx = i + 1;
            if (next_idx == stringBuilder.length()) {
                if (stringBuilder.charAt(i) == 'x')
                    stringBuilder.append('y');
                else
                    stringBuilder.append('x');
            } else {
                if (stringBuilder.charAt(i) == stringBuilder.charAt(next_idx)) {
                    if (stringBuilder.charAt(i) == 'x')
                        stringBuilder.insert(next_idx, 'y');
                    else
                        stringBuilder.insert(next_idx, 'x');
                }
            }
        }
        return stringBuilder.toString();
    }

    private static void displayMatrix(char[][] matrix) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
