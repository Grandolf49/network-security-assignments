package com.chinmay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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
        return encryptedString.toString();
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
