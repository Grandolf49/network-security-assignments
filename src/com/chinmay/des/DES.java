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

        // Circular Shift to generate keys
        String circular_shift = permutation.toString();
        for (int i = 1; i <= MAX_KEYS; i++) {
            String leftBitsCircularShift = generateCircularShift(circular_shift.substring(0, circular_shift.length() / 2), i);
            String rightBitsCircularShift = generateCircularShift(circular_shift.substring(circular_shift.length() / 2), i);

            circular_shift = leftBitsCircularShift + rightBitsCircularShift;
            System.out.println("1st Circular Shift: " + circular_shift);

            // Apply P8 on circular shift 1 to get first key
            String key_1 = generatePermutation(circular_shift, KEY_P8);
            System.out.println("Key " + i + ": " + key_1);

            keys.add(key_1);

        }
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
