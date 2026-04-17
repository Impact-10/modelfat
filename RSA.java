
import java.util.Scanner; 
 
public class RSA{ 
    private long n, d, e; 
 
    public RSA(long p, long q) { 
        n = p * q; 
        long phi = (p - 1) * (q - 1); 
        e = 3;  
        while (gcd(e, phi) != 1) { e += 2; } 
        d = modInverse(e, phi); 
    } 
 
    // Standard GCD function 
    private long gcd(long a, long b) { 
        while (b != 0) { 
            a %= b; 
            long temp = a; a = b; b = temp; 
        } 
        return a; 
    } 
 
    private long modInverse(long e, long phi) { 
        long m0 = phi, t, q; 
        long x0 = 0, x1 = 1; 
        if (phi == 1) return 0; 
        while (e > 1) { 
            q = e / phi; 
            t = phi; 
            phi = e % phi; e = t; 
            t = x0; 
            x0 = x1 - q * x0; 
            x1 = t; 
        } 
        if (x1 < 0) x1 += m0; 
        return x1; 
    } 
 
    private long power(long base, long exp, long mod) { 
        long res = 1; 
        base = base % mod; 
        while (exp > 0) { 
            if (exp % 2 == 1) res = (res * base) % mod; 
            base = (base * base) % mod; 
            exp = exp / 2; 
        } 
        return res; 
    } 
 
    public long[] encrypt(String msg) { 
        long[] cipher = new long[msg.length()]; 
        for (int i = 0; i < msg.length(); i++) { 
            cipher[i] = power(msg.charAt(i), e, n); 
        } 
        return cipher; 
    } 
 
    public String decrypt(long[] cipher) { 
        StringBuilder sb = new StringBuilder(); 
        for (long val : cipher) { 
            sb.append((char) power(val, d, n)); 
        } 
        return sb.toString(); 
    } 
 
    public static void main(String[] args) { 
        RSA rsa = new RSA(61, 53);  
        Scanner sc = new Scanner(System.in); 
 
        System.out.print("Enter text to encrypt: "); 
        String input = sc.nextLine(); 
 
        long[] encrypted = rsa.encrypt(input); 
        System.out.print("Encrypted code: "); 
        for (long l : encrypted) System.out.print(l + " "); 
 
        System.out.println("\nDecrypted text: " + rsa.decrypt(encrypted)); 
        sc.close(); 
    } 
}