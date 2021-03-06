package com.chinmay.des;

/**
 * A class to store all the constants used for DES Encryption
 *
 * @author grandolf49
 */
public class DESConstants {

    // Limits
    public static final int MAX_KEYS = 2;

    // Input data
    public static final String PLAIN_TEXT = "10100101";
    public static final String KEY = "0010010111";

    // Permutations for Plain Text
    public static final int[] PLAIN_TEXT_IP = {2, 6, 3, 1, 4, 8, 5, 7};
    public static final int[] PLAIN_TEXT_IP_INV = {4, 1, 3, 5, 7, 2, 8, 6};
    public static final int[] PLAIN_TEXT_EP = {4, 1, 2, 3, 2, 3, 4, 1};
    public static final int[] PLAIN_TEXT_P4 = {2, 4, 3, 1};
    public static final int[][] PLAIN_TEXT_S0 = {
            {1, 0, 3, 2},
            {3, 2, 1, 0},
            {0, 2, 1, 3},
            {3, 1, 3, 2}
    };
    public static final int[][] PLAIN_TEXT_S1 = {
            {0, 1, 2, 3},
            {2, 0, 1, 3},
            {3, 0, 1, 0},
            {2, 1, 0, 3}
    };
    public static final String BIN_0 = "0";
    public static final String BIN_1 = "1";

    // Permutations for Key
    public static final int[] KEY_P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    public static final int[] KEY_P8 = {6, 3, 7, 4, 8, 5, 10, 9};

}
