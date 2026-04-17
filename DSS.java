import java.security.KeyPair; 
import java.security.KeyPairGenerator; 
import java.security.PrivateKey; 
import java.security.PublicKey; 
import java.security.Signature; 
import java.security.spec.ECGenParameterSpec; 
import java.util.Base64; 
import java.util.Scanner; 
 
public class DSS { 
    public static void main(String[] args) throws Exception { 
        Scanner scanner = new Scanner(System.in); 
         
        System.out.print("Enter the legal document or message to sign: "); 
        String document = scanner.nextLine(); 
        scanner.close(); 
 
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC"); 
        keyGen.initialize(new ECGenParameterSpec("secp256r1")); 
        KeyPair pair = keyGen.generateKeyPair(); 
        PrivateKey privKey = pair.getPrivate(); 
        PublicKey pubKey = pair.getPublic(); 
 
        Signature sign = Signature.getInstance("SHA256withECDSA"); 
         
        sign.initSign(privKey); 
        byte[] docBytes = document.getBytes(); 
        sign.update(docBytes); 
        byte[] signature = sign.sign(); 
 
        System.out.println("Legal Signature (Base64): " +  Base64.getEncoder().encodeToString(signature)); 
 
        sign.initVerify(pubKey); 
        sign.update(docBytes); 
        boolean isVerified = sign.verify(signature); 
 
        System.out.println("Parties verified successfully: " + isVerified); 
    }
}
    