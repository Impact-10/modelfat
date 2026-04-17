import java.util.Scanner; 
 
public class MIM { 
 
    // Modular exponentiation 
    static int modPow(int base, int exp, int mod) { 
        int result = 1; 
        base = base % mod; 
        while (exp > 0) { 
            if (exp % 2 == 1) 
                result = (result * base) % mod; 
            exp = exp / 2; 
            base = (base * base) % mod; 
        } 
        return result; 
    } 
 
    public static void main(String[] args) { 
 
        Scanner sc = new Scanner(System.in); 
 
        // Public parameters 
 
        System.out.print("Enter prime number p: "); 
        int p = sc.nextInt(); 
 
        System.out.print("Enter primitive root g: "); 
        int g = sc.nextInt(); 
 
        System.out.println("--------------------------------"); 
 
        // Client side 
        System.out.print("Enter Client private key (a): "); 
        int a = sc.nextInt(); 
        int A = modPow(g, a, p); 
        System.out.println("Client public key A = " + A); 
 
        // Server side 
        System.out.print("Enter Server private key (b): "); 
        int b = sc.nextInt(); 
        int B = modPow(g, b, p); 
        System.out.println("Server public key B = " + B); 
 
        System.out.println("--------------------------------"); 
 
        // MITM attacker 
        System.out.print("Enter MITM private key with Client (m1): "); 
        int m1 = sc.nextInt(); 
 
        System.out.print("Enter MITM private key with Server (m2): "); 
        int m2 = sc.nextInt(); 
 
        int M1 = modPow(g, m1, p); 
        int M2 = modPow(g, m2, p); 
 
        System.out.println("MITM sends fake public key to Client: " + M1); 
        System.out.println("MITM sends fake public key to Server: " + M2); 
 
        System.out.println("--------------------------------"); 
 
        // Shared key calculations 
        int clientSharedKey = modPow(M1, a, p); 
        int serverSharedKey = modPow(M2, b, p); 
 
        int mitmKeyWithClient = modPow(A, m1, p); 
        int mitmKeyWithServer = modPow(B, m2, p); 
 
        System.out.println("Client computed shared key = " + clientSharedKey); 
        System.out.println("MITM computed key with Client = " + 
mitmKeyWithClient); 
 
 
        System.out.println("Server computed shared key = " + serverSharedKey); 
        System.out.println("MITM computed key with Server = " + 
mitmKeyWithServer); 
 
        System.out.println("--------------------------------"); 
 
        if (clientSharedKey == mitmKeyWithClient && 
            serverSharedKey == mitmKeyWithServer) { 
            System.out.println("MITM Attack Successful!"); 
            System.out.println("Attacker can decrypt and modify messages."); 
        } else { 
            System.out.println("MITM Attack Failed."); 
        } 
 
        sc.close(); 
    } 
} 
