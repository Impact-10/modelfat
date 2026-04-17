import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Server {
    public static boolean verifySignature(String message, byte[] signatureBytes, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withDSA");
        signature.initVerify(publicKey);
        signature.update(message.getBytes());
        return signature.verify(signatureBytes);
    }

    public static String decrypt(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(encryptedData));
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String encMsgBase64 = in.readLine();
        String signatureBase64 = in.readLine();
        String publicKeyBase64 = in.readLine();
        String sessionKeyBase64 = in.readLine();

        byte[] keyBytes = Base64.getDecoder().decode(sessionKeyBase64);
        SecretKey sessionKey = new SecretKeySpec(keyBytes, "AES");

        byte[] encryptedMsg = Base64.getDecoder().decode(encMsgBase64);
        String message = decrypt(encryptedMsg, sessionKey);

        byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);

        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        boolean isValid = verifySignature(message, signatureBytes, publicKey);

        System.out.println("\nDecrypted Message: " + message);
        System.out.println("Signature Valid: " + isValid);

        socket.close();
        serverSocket.close();
    }
}