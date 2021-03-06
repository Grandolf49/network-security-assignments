package com.chinmay;

import java.util.Scanner;

/**
 * A program to perform Diffie Hellman Key Exchange
 *
 * @author grandolf49
 */
public class DiffieHellmanKeyExchange {

    public static final int PUBLIC_P = 23;
    public static final int PUBLIC_G = 9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Private key for Alice: ");
        int private_alice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Private key for Bob: ");
        int private_bob = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Using public keys: " + PUBLIC_P + " and " + PUBLIC_G);
        int public_alice = generatePublicKey(private_alice);
        System.out.println("Public key of Alice: " + public_alice);

        int public_bob = generatePublicKey(private_bob);
        System.out.println("Public key of Bob: " + public_bob);

        System.out.println("Exchanging public keys...");
        int key_alice = generateKey(public_bob, private_alice);
        System.out.println("Key generated by Alice: " + key_alice);

        int key_bob = generateKey(public_alice, private_bob);
        System.out.println("Key generated by Bob: " + key_bob);
    }

    /**
     * A function to generate the secret key using public and private key
     */
    private static int generateKey(int public_key, int private_key) {
        int key = 1;
        for (int i = 1; i <= private_key; i++) {
            key *= public_key;
            key = key % PUBLIC_P;
        }
        return key;
    }

    /**
     * A function to generate the public using private key
     */
    private static int generatePublicKey(int private_key) {
        int public_key = 1;
        for (int i = 1; i <= private_key; i++) {
            public_key *= PUBLIC_G;
            public_key = public_key % PUBLIC_P;
        }
        return public_key;
    }

}
