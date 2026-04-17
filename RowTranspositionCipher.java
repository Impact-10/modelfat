import java.util.*;

class RowTranspositionCipher {

    static int[] getOrder(String key) {

        int[] order = new int[key.length()];
        Character[] k = new Character[key.length()];

        for (int i = 0; i < key.length(); i++)
            k[i] = key.charAt(i);

        Character[] sorted = k.clone();
        Arrays.sort(sorted);

        for (int i = 0; i < key.length(); i++)
            for (int j = 0; j < key.length(); j++)
                if (k[i] == sorted[j])
                    order[i] = j;

        return order;
    }

    static String encrypt(String text, String key) {

        int col = key.length();
        int row = (int) Math.ceil((double) text.length() / col);

        char[][] matrix = new char[row][col];

        int k = 0;

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                if (k < text.length())
                    matrix[i][j] = text.charAt(k++);
                else
                    matrix[i][j] = 'X';

        int[] order = getOrder(key);

        String cipher = "";

        for (int num = 0; num < col; num++) {
            for (int j = 0; j < col; j++) {
                if (order[j] == num) {
                    for (int i = 0; i < row; i++)
                        cipher += matrix[i][j];
                }
            }
        }

        return cipher;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Plain Text: ");
        String text = sc.nextLine();

        System.out.print("Enter Key (numbers only, no spaces): ");
        String key = sc.nextLine();

        String cipher = encrypt(text, key);

        System.out.println("\nCiphertext: " + cipher);

        sc.close();
    }
}