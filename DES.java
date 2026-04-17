import java.util.Scanner;

public class DES{
    private static final int[][] S1 = {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 8-character plaintext: ");
        String input = sc.nextLine();
        if (input.length() != 8) {
            System.out.println("Input must be exactly 8 characters (64 bits).");
            return;
        }

        byte[] bits = stringToBits(input);
        byte[] permuted = initialPermutation(bits);
        byte[] roundResult = dummyRound(permuted);

        System.out.println("Encrypted (Binary): " + bitsToBinaryString(roundResult));
        sc.close();
    }

    private static byte[] stringToBits(String str) {
        byte[] bits = new byte[64];
        byte[] bytes = str.getBytes();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = (byte) ((bytes[i] >> (7 - j)) & 1);
            }
        }
        return bits;
    }

    private static byte[] initialPermutation(byte[] bits) {
        return bits;
    }

    private static byte[] dummyRound(byte[] bits) {
        byte[] result = new byte[64];
        for (int i = 0; i < 32; i++) {
            result[i] = (byte) (bits[i] ^ bits[i + 32]);
            result[i + 32] = bits[i];
        }
        return result;
    }

    private static String bitsToBinaryString(byte[] bits) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bits)
            sb.append(b);
        return sb.toString();
    }
}
