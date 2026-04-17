import java.util.*;

class RailFenceCipher {

    public static String encrypt(String text, int key) {
        char[][] rail = new char[key][text.length()];

        for (int i = 0; i < key; i++)
            Arrays.fill(rail[i], '\n');

        boolean down = false;
        int row = 0;

        for (int i = 0; i < text.length(); i++) {

            if (row == 0 || row == key - 1)
                down = !down;

            rail[row][i] = text.charAt(i);

            if (down)
                row++;
            else
                row--;
        }

        String result = "";

        for (int i = 0; i < key; i++)
            for (int j = 0; j < text.length(); j++)
                if (rail[i][j] != '\n')
                    result += rail[i][j];

        return result;
    }

    public static String decrypt(String cipher, int key) {

        char[][] rail = new char[key][cipher.length()];

        for (int i = 0; i < key; i++)
            Arrays.fill(rail[i], '\n');

        boolean down = false;
        int row = 0;

        for (int i = 0; i < cipher.length(); i++) {

            if (row == 0)
                down = true;
            if (row == key - 1)
                down = false;

            rail[row][i] = '*';

            if (down)
                row++;
            else
                row--;
        }

        int index = 0;

        for (int i = 0; i < key; i++)
            for (int j = 0; j < cipher.length(); j++)
                if (rail[i][j] == '*' && index < cipher.length())
                    rail[i][j] = cipher.charAt(index++);

        String result = "";
        row = 0;
        down = false;

        for (int i = 0; i < cipher.length(); i++) {

            if (row == 0)
                down = true;
            if (row == key - 1)
                down = false;

            result += rail[row][i];

            if (down)
                row++;
            else
                row--;
        }

        return result;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Plain Text: ");
        String text = sc.nextLine();

        System.out.print("Enter Key (number of rails): ");
        int key = sc.nextInt();

        String cipher = encrypt(text, key);
        System.out.println("\nEncrypted Text: " + cipher);

        String decrypted = decrypt(cipher, key);
        System.out.println("Decrypted Text: " + decrypted);

        sc.close();
    }
}