import java.math.BigInteger; 
import java.util.Scanner; 
 
public class MPKE { 
 
    static class Party { 
        String name; 
        BigInteger privateKey; 
        BigInteger publicKey; 
 
        Party(String name, BigInteger privateKey, BigInteger g, BigInteger p) 
{ 
            this.name = name; 
            this.privateKey = privateKey; 
            this.publicKey = g.modPow(privateKey, p); 
        } 
 
        BigInteger compute(BigInteger received, BigInteger p) { 
            return received.modPow(privateKey, p); 
        } 
    } 
 
    public static void main(String[] args) { 
 
        Scanner sc = new Scanner(System.in); 
 
        System.out.print("Enter prime number (p): "); 
        BigInteger p = sc.nextBigInteger(); 
 
        System.out.print("Enter generator (g): "); 
        BigInteger g = sc.nextBigInteger(); 
 
        System.out.print("Enter Alice private key: "); 
        BigInteger a = sc.nextBigInteger(); 
 
        System.out.print("Enter Bob private key: "); 
        BigInteger b = sc.nextBigInteger(); 
 
        System.out.print("Enter Charlie private key: "); 
        BigInteger c = sc.nextBigInteger(); 
 
        Party alice = new Party("Alice", a, g, p); 
        Party bob = new Party("Bob", b, g, p); 
 
        Party charlie = new Party("Charlie", c, g, p); 
 
        System.out.println("\n--- Public Keys ---"); 
        System.out.println("Alice public: " + alice.publicKey); 
        System.out.println("Bob public: " + bob.publicKey); 
        System.out.println("Charlie public: " + charlie.publicKey); 
 
        System.out.println("\n--- Round 1 ---"); 
 
        BigInteger AB = alice.compute(bob.publicKey, p); 
        BigInteger BC = bob.compute(charlie.publicKey, p); 
        BigInteger CA = charlie.compute(alice.publicKey, p); 
 
        System.out.println("g^(ab): " + AB); 
        System.out.println("g^(bc): " + BC); 
        System.out.println("g^(ca): " + CA); 
 
        System.out.println("\n--- Final Shared Key ---"); 
 
        BigInteger keyAlice = AB.modPow(c, p); 
        BigInteger keyBob = BC.modPow(a, p); 
        BigInteger keyCharlie = CA.modPow(b, p); 
 
        System.out.println("Alice final key: " + keyAlice); 
        System.out.println("Bob final key: " + keyBob); 
        System.out.println("Charlie final key: " + keyCharlie); 
 
        System.out.println("\nAll keys equal? " + 
                (keyAlice.equals(keyBob) && keyBob.equals(keyCharlie))); 
 
        sc.close(); 
    }
}