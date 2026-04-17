import java.util.*;
import java.io.*;

class AdvancedCaesarCipher{

    public static void encrypt(String s, int k) {
        String s1 = "";
        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(i);

            if (Character.isAlphabetic(x)) {
                if (x >= 'a' && x <= 'z') {
                    int y = x - 'a';
                    int z = (y + k) % 26;
                    char p = (char) (z + 'a');
                    s1 = s1 + p;
                } else if (x >= 'A' && x <= 'Z') {
                    int y = x - 'A';
                    int z = (y + k) % 26;
                    char p = (char) (z + 'A');
                    s1 = s1 + p;
                }
            } else {
                int v = (int) x;

                if (v >= 33 && v <= 47) {
                    int y = v - 33;
                    int z = (y + k) % 15;
                    char p = (char) (z + 33);
                    s1 = s1 + p;
                } else if (v >= 48 && v <= 57) {
                    int y = v - 48;
                    int z = (y + k) % 10;
                    char p = (char) (z + 48);
                    s1 = s1 + p;
                } else if (v >= 58 && v <= 64) {
                    int y = v - 58;
                    int z = (y + k) % 7;
                    char p = (char) (z + 58);
                    s1 = s1 + p;
                } else {
                    s1 = s1 + x;
                }
            }
        }
        System.out.println(s1);
    }

    public static void decrypt(String s, int k) {
        String s1 = "";

        for (int i = 0; i < s.length(); i++) {
            char x = s.charAt(i);

            if (Character.isAlphabetic(x)) {
                if (x >= 'a' && x <= 'z') {
                    int y = x - 'a';
                    int z = (y - k) % 26;
                    if (z < 0) z += 26;
                    char p = (char) (z + 'a');
                    s1 = s1 + p;
                } else if (x >= 'A' && x <= 'Z') {
                    int y = x - 'A';
                    int z = (y - k) % 26;
                    if (z < 0) z += 26;
                    char p = (char) (z + 'A');
                    s1 = s1 + p;
                }
            } else {
                int v = (int) x;

                if (v >= 33 && v <= 47) {
                    int y = v - 33;
                    int z = (y - k) % 15;
                    if (z < 0) z += 15;
                    char p = (char) (z + 33);
                    s1 = s1 + p;
                } else if (v >= 48 && v <= 57) {
                    int y = v - 48;
                    int z = (y - k) % 10;
                    if (z < 0) z += 10;
                    char p = (char) (z + 48);
                    s1 = s1 + p;
                } else if (v >= 58 && v <= 64) {
                    int y = v - 58;
                    int z = (y - k) % 7;
                    if (z < 0) z += 7;
                    char p = (char) (z + 58);
                    s1 = s1 + p;
                } else {
                    s1 = s1 + x;
                }
            }
        }

        System.out.println(s1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("For Encryption Service Enter 1 or if Decryption Enter 2: ");
        int service = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the string: ");
        String s = sc.nextLine();
        System.out.print("Enter the key: ");
        int k = sc.nextInt();

        if (service == 1) {
            encrypt(s, k);
        } else if (service == 2) {
            decrypt(s, k);
        } else {
            System.out.println("Invalid service choice!");
        }
    }
}
