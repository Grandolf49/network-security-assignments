package com.chinmay.aes;

/**
 * A class to store all the constants used for AES Encryption
 *
 * @author grandolf49
 */
public class AESConstants {

    // Input Data
    public static final String PLAIN_TEXT = "1101011100101000";
    public static final String KEY = "0100101011110101";

    // Key Constants
    public static final String[] KEY_XOR = {"10000000", "00110000"};

    // Encryption and Decryption variables
    public static final String[][] S_BOX_ENCRYPTION = {
            {"1001", "0100", "1010", "1011"},
            {"1101", "0001", "1000", "0101"},
            {"0110", "0010", "0000", "0011"},
            {"1100", "1110", "1111", "0111"}
    };
    public static final String[][] S_BOX_DECRYPTION = {
            {"1010", "0101", "1001", "1011"},
            {"0001", "0111", "1000", "1111"},
            {"0110", "0000", "0010", "0011"},
            {"1100", "0100", "1101", "1110"}
    };
    public static final int[][] MIXED_COL_ENCRYPTION = {
            {1, 4},
            {4, 1}
    };
    public static final int[][] MIXED_COL_DECRYPTION = {
            {9, 2},
            {2, 9}
    };
    public static final int POLY_REDUCER_CONST = 19;
}
