import java.util.*;

public class SDES{
    static int[] P10 = {3,5,2,7,4,10,1,9,8,6};
    static int[] P8  = {6,3,7,4,8,5,10,9};
    static int[] IP  = {2,6,3,1,4,8,5,7};
    static int[] IP_INV = {4,1,3,5,7,2,8,6};
    static int[] EP  = {4,1,2,3,2,3,4,1};
    static int[] P4  = {2,4,3,1};

    static int[][] S0 = {
        {1,0,3,2},
        {3,2,1,0},
        {0,2,1,3},
        {3,1,3,2}
    };

    static int[][] S1 = {
        {0,1,2,3},
        {2,0,1,3},
        {3,0,1,0},
        {2,1,0,3}
    };

    static String K1, K2;

    static String permute(String input, int[] table) {
        StringBuilder output = new StringBuilder();
        for (int i : table)
            output.append(input.charAt(i - 1));
        return output.toString();
    }

    static String leftShift(String input, int shifts) {
        return input.substring(shifts) + input.substring(0, shifts);
    }

    static String xor(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++)
            sb.append(a.charAt(i) ^ b.charAt(i));
        return sb.toString();
    }

    static void generateKeys(String key) {
        String p10 = permute(key, P10);
        String left = p10.substring(0,5);
        String right = p10.substring(5);

        left = leftShift(left, 1);
        right = leftShift(right, 1);
        K1 = permute(left + right, P8);

        left = leftShift(left, 2);
        right = leftShift(right, 2);
        K2 = permute(left + right, P8);
    }

    static String sBox(String input, int[][] box) {
        int row = Integer.parseInt("" + input.charAt(0) + input.charAt(3), 2);
        int col = Integer.parseInt("" + input.charAt(1) + input.charAt(2), 2);
        return String.format("%2s",
                Integer.toBinaryString(box[row][col])).replace(' ', '0');
    }

    static String fk(String input, String key) {
        String left = input.substring(0,4);
        String right = input.substring(4);

        String ep = permute(right, EP);
        String xorResult = xor(ep, key);

        String s0 = sBox(xorResult.substring(0,4), S0);
        String s1 = sBox(xorResult.substring(4), S1);

        String p4 = permute(s0 + s1, P4);
        return xor(left, p4) + right;
    }

    static String switchSW(String input) {
        return input.substring(4) + input.substring(0,4);
    }

    static String encrypt(String plaintext) {
        String ip = permute(plaintext, IP);
        String r1 = fk(ip, K1);
        String sw = switchSW(r1);
        String r2 = fk(sw, K2);
        return permute(r2, IP_INV);
    }

    static String decrypt(String ciphertext) {
        String ip = permute(ciphertext, IP);
        String r1 = fk(ip, K2);
        String sw = switchSW(r1);
        String r2 = fk(sw, K1);
        return permute(r2, IP_INV);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 10-bit key: ");
        String key = sc.next();

        System.out.print("Enter 8-bit plaintext: ");
        String plaintext = sc.next();

        generateKeys(key);

        String cipher = encrypt(plaintext);
        String decrypted = decrypt(cipher);

        System.out.println("K1 = " + K1);
        System.out.println("K2 = " + K2);
        System.out.println("Encrypted: " + cipher);
        System.out.println("Decrypted: " + decrypted);
    }
}
