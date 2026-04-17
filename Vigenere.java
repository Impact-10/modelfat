import java.util.*;
import java.io.*;

public class Vigenere {
    static char vigenereMatrix[][]=new char[26][26];
    public static void generateMatrix(){
        for(int i=0;i<26;i++){
            for(int j=0;j<26;j++){
                int u=(i+j)%26;
                vigenereMatrix[i][j]=(char)(u+65);
            }
        }
    }
    public static String vigenereCipherEncrypt(String p, String k){
        String s="";
        for(int i=0;i<p.length();i++){
            int r=(int)(k.charAt(i % k.length()) - 65);
            int c=(int)(p.charAt(i)-65);
            char h=vigenereMatrix[r][c];
            s+=h;
        }
        return s;
    }

    public static String vigenereCipherDecrypt(String e, String k)

{
        String s="";
        for(int i=0;i<e.length();i++){
            int r=(int)(k.charAt(i % k.length()) - 65);
            for(int j=0;j<26;j++){
                if(vigenereMatrix[r][j]==e.charAt(i)){
                    char h=vigenereMatrix[0][j];
                    s+=h;
                }
            }
        }
        return s;
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        String p=sc.nextLine();
        String k=sc.nextLine();
        generateMatrix();
        String encText=vigenereCipherEncrypt(p,k);
        System.out.println(encText);
        String decText=vigenereCipherDecrypt(encText,k);
        System.out.println(decText);
    }
}
