import java.util.*;
import java.io.*;

class MD5Program {
    static byte[] messagePreparation(String message) {
        byte[] input = message.getBytes();
        int originalLength = input.length;
        long bitLength = (long) originalLength * 8;

        int paddingLength = (56 - (originalLength + 1) % 64 + 64) % 64;
        byte[] padded = new byte[originalLength + 1 + paddingLength + 8];

        for (int i = 0; i < originalLength; i++)
            padded[i] = input[i];

        padded[originalLength] = (byte) 0x80;

        for (int i = 0; i < 8; i++)
            padded[padded.length - 8 + i] = (byte) (bitLength >>> (8 * i));

        return padded;
    }

    static int leftRotate(int x, int c) {
        return (x << c) | (x >>> (32 - c));
    }

    static int F(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    static void singleRound(byte[] paddedMessage) {
        int a = 0x67452301;
        int b = 0xefcdab89;
        int c = 0x98badcfe;
        int d = 0x10325476;

        int[] M = new int[16];
        for (int i = 0; i < 16; i++) {
            M[i] = ((paddedMessage[i * 4] & 0xff)) |
                   ((paddedMessage[i * 4 + 1] & 0xff) << 8) |
                   ((paddedMessage[i * 4 + 2] & 0xff) << 16) |
                   ((paddedMessage[i * 4 + 3] & 0xff) << 24);
        }

        int k = 0;
        int s = 7;
        int T = 0xd76aa478;

        int temp = a + F(b, c, d) + M[k] + T;
        a = b + leftRotate(temp, s);

        System.out.println("\nAfter Single Round:");
        System.out.println("A = " + Integer.toHexString(a));
        System.out.println("B = " + Integer.toHexString(b));
        System.out.println("C = " + Integer.toHexString(c));
        System.out.println("D = " + Integer.toHexString(d));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message: ");
        String message = sc.nextLine();

        byte[] padded = messagePreparation(message);

        System.out.println("\nAfter Message Preparation:");
        System.out.println("Prepared Length: " + padded.length + " bytes");

        singleRound(padded);
    }
}
