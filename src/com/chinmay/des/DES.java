package com.chinmay.des;

import com.sun.prism.shader.DrawEllipse_LinearGradient_REFLECT_AlphaTest_Loader;

import java.util.ArrayList;

import static com.chinmay.des.DESConstants.*;

public class DES {

    public static void main(String[] args) {

        System.out.println("Data Encryption Standard");
        System.out.println("Plain Text: " + PLAIN_TEXT);
        System.out.println("Key: " + KEY);

        String encryptedMessage = desEncryption(PLAIN_TEXT, KEY);
    }

    private static String desEncryption(String plainText, String key) {
        String encryptedMessage = "";

        // Apply initial permutation
        String initialPermutation = generatePermutation(plainText, PLAIN_TEXT_IP);
        System.out.println("Initial Permutation: " + initialPermutation);

        // Split the result into 2 equal parts
        String leftOperand = initialPermutation.substring(0, initialPermutation.length() / 2);
        String rightOperand = initialPermutation.substring(initialPermutation.length() / 2);
        System.out.println("Left: " + leftOperand + "\t" + "Right: " + rightOperand);

        // Applying extended permutation on right operand
        String extendedPermutation = generatePermutation(rightOperand, PLAIN_TEXT_EP);
        System.out.println("Extended Permutation: " + extendedPermutation);

        // Generate first key [Key 1]
        ArrayList<String> keys = generateKeys(key);

        return encryptedMessage;
    }

    private static ArrayList<String> generateKeys(String key) {
        ArrayList<String> keys = new ArrayList<>();
        StringBuilder permutation = new StringBuilder();

        // Applying 10 bit permutation
        for (int value : KEY_P10) {
            int idx = value - 1;
            permutation.append(key.charAt(idx));
        }
        System.out.println("Key P10: " + permutation.toString());

        // Circular Shift 1
        String leftBitsCircularShift = generateCircularShift(permutation.substring(0, permutation.length() / 2), 1);
        String rightBitsCircularShift = generateCircularShift(permutation.substring(permutation.length() / 2), 1);

        String circularShift_1 = leftBitsCircularShift + rightBitsCircularShift;
        System.out.println("1st Circular Shift: " + circularShift_1);

        // Apply P8 on circular shift 1 to get first key
        String key_1 = generatePermutation(circularShift_1, KEY_P8);
        System.out.println("Key 1: " + key_1);

        keys.add(key_1);

        leftBitsCircularShift = generateCircularShift(circularShift_1.substring(0, circularShift_1.length() / 2), 2);
        rightBitsCircularShift = generateCircularShift(circularShift_1.substring(circularShift_1.length() / 2), 2);

        String circularShift_2 = leftBitsCircularShift + rightBitsCircularShift;
        System.out.println("2nd Circular Shift: " + circularShift_2);

        // Apply P8 on circular shift 2 to get second key
        String key_2 = generatePermutation(circularShift_2, KEY_P8);
        System.out.println("Key 2: " + key_2);

        keys.add(key_2);

        return keys;
    }

    private static String generateCircularShift(String substring, int dist) {
        StringBuilder circularShift = new StringBuilder();
        int length = substring.length();
        for (int i = 0; i < length; i++) {
            circularShift.append(substring.charAt((i + dist) % substring.length()));
        }

        return circularShift.toString();
    }

    private static String generatePermutation(String text, int[] permutationMatrix) {
        StringBuilder permutation = new StringBuilder();

        for (int index : permutationMatrix) {
            int idx = index - 1;
            permutation.append(text.charAt(idx));
        }
        return permutation.toString();
    }

}
