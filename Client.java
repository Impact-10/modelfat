import java.io.*;
import java.net.*;
import java.security.*;
import javax.crypto.*;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    public static byte[] signMessage(String message, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes());
        return signature.sign();
    }

    public static byte[] encrypt(String message, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message.getBytes());
    }

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner sc = new Scanner(System.in);

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        KeyGenerator aesGen = KeyGenerator.getInstance("AES");
        aesGen.init(128);
        SecretKey sessionKey = aesGen.generateKey();

        System.out.print("Enter message: ");
        String message = sc.nextLine();

        byte[] encryptedMsg = encrypt(message, sessionKey);
        byte[] signatureBytes = signMessage(message, privateKey);

        String encMsgBase64 = Base64.getEncoder().encodeToString(encryptedMsg);
        String signatureBase64 = Base64.getEncoder().encodeToString(signatureBytes);
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String sessionKeyBase64 = Base64.getEncoder().encodeToString(sessionKey.getEncoded());

        out.write(encMsgBase64 + "\n");
        out.write(signatureBase64 + "\n");
        out.write(publicKeyBase64 + "\n");
        out.write(sessionKeyBase64 + "\n");
        out.flush();

        socket.close();
        sc.close();
    }
}