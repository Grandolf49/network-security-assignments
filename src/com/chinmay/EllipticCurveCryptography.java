package com.chinmay;

import java.util.Scanner;

/**
 * A program to calculate A = B + C and 2A using Elliptic Curve Cryptography
 *
 * @author grandolf49
 */
public class EllipticCurveCryptography {

    public static final int M = 23;
    public static final int A = 1;
    public static final int B = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter P(x, y): ");
        int px, py;
        px = scanner.nextInt();
        py = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Q(x, y): ");
        int qx, qy;
        qx = scanner.nextInt();
        qy = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Calculating R = P + Q");
        int[] R = calculatePoint(px, py, qx, qy);
        System.out.println("R = " + R[0] + "," + R[1]);

        System.out.println("Calculating 2R");
        int[] R_2 = calculatePoint(px, py, px, py);
        System.out.println("2R = " + R_2[0] + "," + R_2[1]);
    }

    /**
     * A function to calculate the sum of 2 points in elliptic curve cryptography
     */
    private static int[] calculatePoint(int px, int py, int qx, int qy) {
        int delta = calculateDelta(px, py, qx, qy);
        System.out.println("Delta: " + delta);
        int xr = (((delta * delta) - px - qx) % M + M) % M;
        int yr = ((-1 * py + delta * (px - xr)) % M + M) % M;
        return new int[]{xr, yr};
    }

    /**
     * A function to calculate the value of delta in elliptic curve cryptography
     */
    private static int calculateDelta(int px, int py, int qx, int qy) {
        int delta;
        if (px != qx && py != qy) {
            System.out.println("Different Points");
            // Calculating Delta
            int diff_x = qx - px;

            // To avoid negative sign while calculating mod inverse
            if (diff_x < 0) {
                // Swap P and Q
                int temp = px;
                px = qx;
                qx = temp;
                temp = py;
                py = qy;
                qy = temp;
            }
            diff_x = qx - px;
            int diff_y = qy - py;

            // Calculate mod inverse of diff_x wrt M
            int mod_inv = getMultiplicativeInverse(diff_x);
            System.out.println("Modular Multiplicative Inverse of " + diff_x + ": " + mod_inv);
            delta = ((mod_inv * diff_y) % M + M) % M;
        } else {
            System.out.println("Same Points");
            int y_2 = 2 * py;
            int mov_inv = getMultiplicativeInverse(y_2);
            System.out.println("Modular Multiplicative Inverse of " + y_2 + ": " + mov_inv);
            delta = ((mov_inv * (3 * px * px + A)) % M + M) % M;
        }

        return delta;
    }

    /**
     * A function to calculate multiplicative inverse of a number wrt another
     */
    private static int getMultiplicativeInverse(int determinant) {
        int a = determinant % M;
        int result = 1;
        for (int i = 1; i < M; i++) {
            if ((a * i) % M == 1) {
                result = i;
                break;
            }
        }
        return result;
    }

}
