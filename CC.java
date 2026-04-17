import java.util.*;
import java.io.*;
class SimpleCaesarCipher {
    public static void encrypt(String s,int k){
        String s1="";
        for(int i=0;i<s.length();i++){
            char x=s.charAt(i);
            if(x>='a' && x<='z'){
                int y=x-'a';
                int z=(y+k)%26; 
                char p=(char)(z+'a');
                s1=s1+p;
            }
            else if(x>='A' && x<='Z'){
                int y=x-'A';
                int z=(y+k)%26; 
                char p=(char)(z+'A');
                s1=s1+p;
            }
        }
        System.out.println(s1);
    }
    public static void decrypt(String s,int k){
        String s1="";
        for(int i=0;i<s.length();i++){
            char x=s.charAt(i);
            if(x>='a' && x<='z'){
                int y=x-'a';
                int z=(y-k)%26; 
                char p=(char)(z+'a');
                s1=s1+p;
            }
            else if(x>='A' && x<='Z'){
                int y=x-'A';
                int z=(y-k)%26; 
                char p=(char)(z+'A');
                s1=s1+p;
            }
        }
        System.out.println(s1);
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("For Encryption Service Enter 1 or if Decryption Enter 2: ");
        int service=sc.nextInt();
        sc.nextLine();
        String s=sc.nextLine();
        int k=sc.nextInt();
        String s1="";
        if(service==1){
            encrypt(s,k);
        }
        else if(service==2){
            decrypt(s,k);
        }
    }
}
