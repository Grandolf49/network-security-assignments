package com.chinmay.aes;

import java.util.ArrayList;

import static com.chinmay.aes.AESConstants.*;

/**
 * A program to encrypt message using AES Encryption
 *
 * @author grandolf49
 */
public class AES {

    public static void main(String[] args) {

        System.out.println("=============================================");
        System.out.println("Plain Text: " + PLAIN_TEXT);
        System.out.println("Key: " + KEY);
        System.out.println("=============================================");

        System.out.println("Encrypting message...");
        String encryptedMessage = aesEncryption();
        System.out.println("=============================================");
        System.out.println("Encrypted Message: " + encryptedMessage);
        System.out.println("=============================================");

    }

    /**
     * A function to encrypt message using AES Encryption
     */
    private static String aesEncryption() {

        String encryptedMessage = "";
        ArrayList<String> keys = generateKeys();
        System.out.println("All keys generated!\n");

        System.out.println("Encryption Begins!");

        // Add Round Key 1
        String addRoundKey = xor(PLAIN_TEXT, keys.get(0));
        System.out.println("Add Round Key 1: " + addRoundKey);

        // Complex Function
        for (int i = 1; i < 3; i++) {
            String sub_nib = substituteNibble(addRoundKey);
            System.out.println("Substitute Nibble: " + sub_nib);

            String output = shiftRow(sub_nib);
            System.out.println("Shift Row: " + output);

            if (i == 1) {
                System.out.println("Applying Mixed Column on: " + output);
                output = applyMixedColumn(output);
                System.out.println("After Mixed Column: " + output);
            }

            addRoundKey = xor(output, keys.get(i));
        }

        encryptedMessage = addRoundKey;
        return encryptedMessage;
    }

    /**
     * A utility function to apply Mixed Column Operation
     */
    private static String applyMixedColumn(String output) {
        StringBuilder result = new StringBuilder();

        int[][] s_matrix = new int[2][2];
        for (int i = 0; i < output.length(); i += 4) {
            int num = Integer.parseInt(output.substring(i, i + 4), 2);
            s_matrix[(i / 4) % 2][i / 8] = num;
        }

        int[][] mul_matrix = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int ans = 0;
                for (int k = 0; k < 2; k++) {
                    int a = MIXED_COL_ENCRYPTION[i][k] * s_matrix[k][j];
                    a = applyPolynomialReducer(a);
                    ans ^= a;
                }
                mul_matrix[i][j] = applyPolynomialReducer(ans);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                String bin = Integer.toBinaryString(mul_matrix[j][i]);
                switch (bin.length()) {
                    case 1:
                        bin = "000" + bin;
                        break;
                    case 2:
                        bin = "00" + bin;
                        break;
                    case 3:
                        bin = "0" + bin;
                        break;
                }
                result.append(bin);
            }
        }

        return result.toString();
    }

    /**
     * A utility function to perform the polynomial reducer operation
     */
    private static int applyPolynomialReducer(int number) {
        while (number > 15) {
            int factor = number / POLY_REDUCER_CONST;
            int max_power_of_2 = (int) (Math.log(factor) / Math.log(2));
            number = number ^ (POLY_REDUCER_CONST * (int) Math.pow(2, max_power_of_2));
        }
        return number;
    }

    /**
     * A function to shift row
     */
    private static String shiftRow(String sub_nib) {

        return sub_nib.substring(0, 4) +
                sub_nib.substring(12, 16) +
                sub_nib.substring(8, 12) +
                sub_nib.substring(4, 8);
    }

    /**
     * A function to generate keys
     */
    private static ArrayList<String> generateKeys() {

        ArrayList<String> keys = new ArrayList<>();

        // 1st key
        keys.add(AESConstants.KEY);

        String w0 = AESConstants.KEY.substring(0, AESConstants.KEY.length() / 2);
        String w1 = AESConstants.KEY.substring(AESConstants.KEY.length() / 2);

        for (int i = 0; i < NO_OF_KEYS - 1; i++) {

            String rot_nib = rotateNibble(w1);
            System.out.println("Rotate Nibble of w1: " + rot_nib);

            String sub_nib = substituteNibble(rot_nib);
            System.out.println("Substitute Nibble of w1: " + sub_nib);

            String xor_1 = xor(KEY_XOR[i], sub_nib);
            String w2 = xor(xor_1, w0);
            System.out.println("W2: " + w2);

            String w3 = xor(w1, w2);
            System.out.println("W3: " + w3);

            String key_2 = w2 + w3;
            System.out.println("Key: " + (i + 2) + " " + key_2);
            keys.add(key_2);

            w0 = w2;
            w1 = w3;
        }

        return keys;
    }

    /**
     * A utility function to perform XOR operation on 2 binary strings
     */
    private static String xor(String keyXor1, String sub_nib) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < keyXor1.length(); i++) {
            char c = '0';
            if (keyXor1.charAt(i) != sub_nib.charAt(i)) {
                c = '1';
            }
            output.append(c);
        }
        return output.toString();
    }

    /**
     * A function to substitute nibble from S-Box Encryption
     */
    private static String substituteNibble(String rot_nib) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < rot_nib.length(); i += 4) {
            int r = Integer.parseInt(rot_nib.substring(i, i + 2), 2);
            int c = Integer.parseInt(rot_nib.substring(i + 2, i + 4), 2);
            result.append(S_BOX_ENCRYPTION[r][c]);
        }

        return result.toString();
    }

    /**
     * A function to rotate nibble from S-Box Encryption
     */
    private static String rotateNibble(String w1) {
        return w1.substring(w1.length() / 2) + w1.substring(0, w1.length() / 2);
    }
}
