import java.util.*;
import java.io.*;

class SHA512Program {

    static long ROTR(long x, int n) {
        return (x >>> n) | (x << (64 - n));
    }

    static long smallSigma0(long x) {
        return ROTR(x, 1) ^ ROTR(x, 8) ^ (x >>> 7);
    }

    static long smallSigma1(long x) {
        return ROTR(x, 19) ^ ROTR(x, 61) ^ (x >>> 6);
    }

    static byte[] messagePreparation(String message) {

        byte[] input = message.getBytes();
        int length = input.length;

        long bitLength = (long) length * 8;

        int paddingLength = (112 - (length + 1) % 128 + 128) % 128;

        byte[] padded = new byte[length + 1 + paddingLength + 16];

        for (int i = 0; i < length; i++)
            padded[i] = input[i];

        padded[length] = (byte) 0x80;

        for (int i = 0; i < 8; i++)
            padded[padded.length - 1 - i] = (byte) (bitLength >>> (8 * i));

        return padded;
    }

    static void computeW(byte[] paddedMessage) {

        long[] W = new long[80];

        for (int i = 0; i < 16; i++) {
            W[i] =
                ((long)(paddedMessage[i*8] & 0xff) << 56) |
                ((long)(paddedMessage[i*8+1] & 0xff) << 48) |
                ((long)(paddedMessage[i*8+2] & 0xff) << 40) |
                ((long)(paddedMessage[i*8+3] & 0xff) << 32) |
                ((long)(paddedMessage[i*8+4] & 0xff) << 24) |
                ((long)(paddedMessage[i*8+5] & 0xff) << 16) |
                ((long)(paddedMessage[i*8+6] & 0xff) << 8) |
                ((long)(paddedMessage[i*8+7] & 0xff));
        }

        System.out.println("\nW0 to W15:");
        for (int i = 0; i < 16; i++) {
            System.out.println("W[" + i + "] = " + Long.toHexString(W[i]));
        }

        // W16 calculation
        W[16] = smallSigma1(W[14]) + W[9] + smallSigma0(W[1]) + W[0];

        System.out.println("\nDerived W16:");
        System.out.println("W[16] = " + Long.toHexString(W[16]));
    }

    static void printHex(byte[] data) {
        System.out.println("\nPrepared Message (Hex):");
        for (int i = 0; i < data.length; i++) {
            System.out.printf("%02x ", data[i]);
            if ((i + 1) % 16 == 0)
                System.out.println();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message: ");
        String message = sc.nextLine();

        byte[] padded = messagePreparation(message);

        System.out.println("\nAfter Message Preparation:");
        System.out.println("Prepared Length: " + padded.length + " bytes");

        printHex(padded);

        computeW(padded);
    }
}
