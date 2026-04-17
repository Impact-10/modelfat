import java.util.Scanner;

public class Vernam {

    public static int charToNum(char c) {
        return c - 'A';
    }

    public static char numToChar(int n) {
        return (char) (n + 'A');
    }

    public static String vernamOperation(String text, String key) {
        if (text.length() != key.length()) {
            System.out.println("Key and Word length not same");
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            int pt = charToNum(text.charAt(i));  
            int ky = charToNum(key.charAt(i));   

            int xor = pt ^ ky; 

            if (xor >= 26) {
                xor -= 26;
            }

            char cipherChar = numToChar(xor);
            result.append(cipherChar);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String plaintext = sc.nextLine().toUpperCase();
        String key = sc.nextLine().toUpperCase();

        System.out.println("Original:  " + plaintext);
        System.out.println("Key:       " + key);

        String ciphertext = vernamOperation(plaintext, key);
        System.out.println("Encrypted: " + ciphertext);

        // Decryption is same operation
        String decrypted = vernamOperation(ciphertext, key);
        System.out.println("Decrypted: " + decrypted);
    }
}

