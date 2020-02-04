package com.chinmay.des;

import java.util.ArrayList;

import static com.chinmay.des.DESConstants.*;

/**
 * A program to encrypt message using DES Encryption
 *
 * @author grandolf49
 */
public class DES {

    public static void main(String[] args) {

        // Inputs defined in DESConstants.java class
        System.out.println("=================== Data Encryption Standard ===================");
        System.out.println("Plain Text: " + PLAIN_TEXT);
        System.out.println("Key: " + KEY);
        System.out.println("================================================================");

        // Encrypt message using DES Encryption
        String encryptedMessage = desEncryption(PLAIN_TEXT, KEY);
        System.out.println("================================================================");
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("================================================================");

    }

    /**
     * A function to encrypt plain text with key using DES Encryption
     */
    private static String desEncryption(String plainText, String key) {
        String encryptedMessage;
        String resultComplexFunction = "";

        // Apply initial permutation
        String initialPermutation = generatePermutation(plainText, PLAIN_TEXT_IP);
        System.out.println("Initial Permutation: " + initialPermutation);

        // Generate keys for complex functions
        ArrayList<String> keys = generateKeys(key);

        // Apply complex function
        String input = initialPermutation;

        for (int i = 0; i < MAX_KEYS; i++) {
            // Split the result into 2 equal parts
            String leftOperand = input.substring(0, input.length() / 2);
            String rightOperand = input.substring(input.length() / 2);
            System.out.println("Left: " + leftOperand + "\t" + "Right: " + rightOperand);

            // Apply complex function
            resultComplexFunction = complexFunction(leftOperand, rightOperand, keys.get(i));
            System.out.println("Result " + (i + 1) + ": " + resultComplexFunction);

            // Swap left and right operands
            if (i == 0)
                resultComplexFunction = resultComplexFunction.substring(resultComplexFunction.length() / 2) +
                        resultComplexFunction.substring(0, resultComplexFunction.length() / 2);
            else continue;

            input = resultComplexFunction;
        }
        System.out.println("Result after applying complex function: " + resultComplexFunction);

        // Apply IP Inverse on result of complex function to get final encrypted message
        encryptedMessage = generatePermutation(resultComplexFunction, PLAIN_TEXT_IP_INV);

        return encryptedMessage;
    }

    /**
     * The complex function used for DES Encryption
     */
    private static String complexFunction(String leftOperand, String rightOperand, String key) {
        // Applying extended permutation on right operand
        String extendedPermutation = generatePermutation(rightOperand, PLAIN_TEXT_EP);
        System.out.println("Extended Permutation: " + extendedPermutation);

        // XOR extended permutation with key 1
        String xorResult = xor(extendedPermutation, key);
        System.out.println("XOR Result: " + xorResult);

        // Apply S0 and S1 on xorResult
        String s0_s1_result = applyS0S1(xorResult);
        System.out.println("S0 S1 Result: " + s0_s1_result);

        // Apply P4 on S0 S1 result
        String permutation_p4 = generatePermutation(s0_s1_result, PLAIN_TEXT_P4);
        System.out.println("P4 Permutation: " + permutation_p4);

        // XOR permutation p4 with left operand
        leftOperand = xor(permutation_p4, leftOperand);
        System.out.println("XOR Result: " + leftOperand);

        return leftOperand + rightOperand;
    }

    /**
     * A utility function to apply S0 and S1 matrix element selector
     */
    private static String applyS0S1(String xorResult) {
        String s0 = xorResult.substring(0, xorResult.length() / 2);
        String s1 = xorResult.substring(xorResult.length() / 2);

        int s0_row = Integer.parseInt(String.valueOf(s0.charAt(0)) + s0.charAt(s0.length() - 1), 2);
        int s0_col = Integer.parseInt(s0.substring(1, 3), 2);

        int s1_row = Integer.parseInt(String.valueOf(s1.charAt(0)) + s1.charAt(s1.length() - 1), 2);
        int s1_col = Integer.parseInt(s1.substring(1, 3), 2);

        String left = Integer.toBinaryString(PLAIN_TEXT_S0[s0_row][s0_col]);
        if (left.length() == 1) {
            StringBuilder t = new StringBuilder(left);
            t.insert(0, BIN_0);
            left = t.toString();
        }
        String right = Integer.toBinaryString(PLAIN_TEXT_S1[s1_row][s1_col]);
        if (right.length() == 1) {
            StringBuilder t = new StringBuilder(right);
            t.insert(0, BIN_0);
            right = t.toString();
        }

        return left + right;
    }

    /**
     * A utility function to calculate XOR of 2 binary strings
     */
    private static String xor(String extendedPermutation, String s) {
        StringBuilder xorResult = new StringBuilder();
        for (int i = 0; i < extendedPermutation.length(); i++) {
            if (extendedPermutation.charAt(i) == s.charAt(i)) {
                xorResult.append("0");
            } else {
                xorResult.append("1");
            }
        }

        return xorResult.toString();
    }

    /**
     * A utility function to generate the keys according to DES Encryption Standard
     */
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

    /**
     * A utility function to perform circular shift operation on a string with given repetitions
     */
    private static String generateCircularShift(String substring, int dist) {
        StringBuilder circularShift = new StringBuilder();
        int length = substring.length();
        for (int i = 0; i < length; i++) {
            circularShift.append(substring.charAt((i + dist) % substring.length()));
        }

        return circularShift.toString();
    }

    /**
     * A utility function to generate the permutation of a string using the permutation array
     */
    private static String generatePermutation(String text, int[] permutationArray) {
        StringBuilder permutation = new StringBuilder();

        for (int index : permutationArray) {
            int idx = index - 1;
            permutation.append(text.charAt(idx));
        }
        return permutation.toString();
    }

}
