package com.chinmay;

/**
 * A program to encrypt and decrypt a message using DES Encryption
 *
 * @author grandolf49
 */
public class RSAAlgorithm {

    public static final int P = 17;
    public static final int Q = 11;
    public static final int M = 88;
    public static final int E = 7;

    public static void main(String[] args) {
        System.out.println("P = " + P + " Q = " + Q + " M = " + M);

        int encrypted = rsaEncryption();
        System.out.println("==============================");
        System.out.println("Encrypted: " + encrypted);
        System.out.println("==============================");

        int decrypted = rsaDecryption(encrypted);
        System.out.println("==============================");
        System.out.println("Decrypted: " + decrypted);
        System.out.println("Original: " + M);
        System.out.println("==============================");

    }

    /**
     * A function to decrypt a number using RSA Algorithm
     */
    private static int rsaDecryption(int C) {
        int n = P * Q;
        int phi_n = (P - 1) * (Q - 1);
        System.out.println("Phi(n) = " + phi_n);

        int d = modularMultiplicativeInverse(phi_n);
        System.out.println("D = " + d);
        int decrypted = 1;
        for (int i = 1; i <= d; i++) {
            decrypted *= C;
            decrypted = decrypted % n;
        }
        return decrypted;
    }

    /**
     * A function to encrypt a number using RSA Algorithm
     */
    private static int rsaEncryption() {
        int n = P * Q;
        System.out.println("N = " + n);

        int cipher = 1;
        for (int i = 1; i <= E; i++) {
            cipher *= M;
            cipher = cipher % n;
        }

        return cipher;
    }

    /**
     * A function to calculate multiplicative inverse of a number wrt another
     */
    private static int modularMultiplicativeInverse(int phi) {
        int a = E % phi;
        int result = 1;
        for (int i = 1; i < phi; i++) {
            if ((a * i) % phi == 1) {
                result = i;
                break;
            }
        }
        return result;
    }

}
