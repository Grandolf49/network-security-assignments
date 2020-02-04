package com.chinmay.des;

public class DESConstants {

    // Input limits
    public static final int PLAIN_TEXT_LENGTH = 8;
    public static final int KEY_LENGTH = 10;

    // Input plain text and key
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

    // Permutations for Key
    public static final int[] KEY_P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    public static final int[] KEY_P8 = {6, 3, 7, 4, 8, 5, 10, 9};

}
