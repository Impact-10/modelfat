import java.util.*;

public class Playfair {

    static char matrix[][] = new char[5][5];

    // Generate Playfair matrix
    static void generateMatrix(String key) {

        boolean used[] = new boolean[26];
        key = key.toLowerCase().replace("j","i");

        int k = 0;

        for(int i=0;i<key.length();i++){
            char c = key.charAt(i);

            if(!used[c-'a']){
                matrix[k/5][k%5] = c;
                used[c-'a'] = true;
                k++;
            }
        }

        for(char c='a'; c<='z'; c++){

            if(c=='j') continue;

            if(!used[c-'a']){
                matrix[k/5][k%5] = c;
                used[c-'a'] = true;
                k++;
            }
        }
    }

    // Find position of character
    static int[] findPos(char c){

        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                if(matrix[i][j]==c)
                    return new int[]{i,j};

        return null;
    }

    // Format plaintext
    static String formatText(String text){

        text = text.toLowerCase().replace("j","i").replaceAll("\\s+","");

        StringBuilder temp = new StringBuilder(text);

        for(int i=0;i<temp.length()-1;i+=2){
            if(temp.charAt(i)==temp.charAt(i+1)){
                temp.insert(i+1,'x');
            }
        }

        if(temp.length()%2!=0)
            temp.append('x');

        return temp.toString();
    }

    // Encryption
    static String encrypt(String text){

        text = formatText(text);

        String result="";

        for(int i=0;i<text.length();i+=2){

            char a=text.charAt(i);
            char b=text.charAt(i+1);

            int p1[]=findPos(a);
            int p2[]=findPos(b);

            if(p1[0]==p2[0]){

                result+=matrix[p1[0]][(p1[1]+1)%5];
                result+=matrix[p2[0]][(p2[1]+1)%5];

            }

            else if(p1[1]==p2[1]){

                result+=matrix[(p1[0]+1)%5][p1[1]];
                result+=matrix[(p2[0]+1)%5][p2[1]];

            }

            else{

                result+=matrix[p1[0]][p2[1]];
                result+=matrix[p2[0]][p1[1]];
            }
        }

        return result;
    }

    // Decryption
    static String decrypt(String text){

        text = text.toLowerCase();

        String result="";

        for(int i=0;i<text.length();i+=2){

            char a=text.charAt(i);
            char b=text.charAt(i+1);

            int p1[]=findPos(a);
            int p2[]=findPos(b);

            if(p1[0]==p2[0]){

                result+=matrix[p1[0]][(p1[1]+4)%5];
                result+=matrix[p2[0]][(p2[1]+4)%5];

            }

            else if(p1[1]==p2[1]){

                result+=matrix[(p1[0]+4)%5][p1[1]];
                result+=matrix[(p2[0]+4)%5][p2[1]];

            }

            else{

                result+=matrix[p1[0]][p2[1]];
                result+=matrix[p2[0]][p1[1]];
            }
        }

        return result;
    }

    public static void main(String args[]){

        Scanner sc=new Scanner(System.in);

        System.out.println("Enter key:");
        String key=sc.nextLine();

        generateMatrix(key);

        System.out.println("Enter text:");
        String text=sc.nextLine();

        System.out.println("1 Encrypt");
        System.out.println("2 Decrypt");

        int choice=sc.nextInt();

        if(choice==1)
            System.out.println("Ciphertext: "+encrypt(text));

        else
            System.out.println("Plaintext: "+decrypt(text));

        sc.close();
    }
}