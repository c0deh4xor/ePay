package eripay.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

/**
Aes encryption
*/
@Service
public class EncryptionService {

  private static SecretKeySpec secretKey;
  private static byte[] key;

  private static String decryptedString;
  private static String encryptedString;

  public static void setKey(String myKey) {

    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      System.out.println(key.length);
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16); // use only first 128 bit
      System.out.println(key.length);
      System.out.println(new String(key, "UTF-8"));
      secretKey = new SecretKeySpec(key, "AES");

    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public static String getDecryptedString() {
    return decryptedString;
  }

  public static void setDecryptedString(String decryptedString) {
    EncryptionService.decryptedString = decryptedString;
  }

  public static String getEncryptedString() {
    return encryptedString;
  }

  public static void setEncryptedString(String encryptedString) {
    EncryptionService.encryptedString = encryptedString;
  }

  public static String encrypt(String strToEncrypt) {
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

      cipher.init(Cipher.ENCRYPT_MODE, secretKey);

      setEncryptedString(Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));

    } catch (Exception e) {

      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  public static String decrypt(String strToDecrypt) {
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      setDecryptedString(new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt))));

    } catch (Exception e) {

      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }

  public String getEncryptedWithKey(String textToEncrypt, String secretKey) {
    setKey(secretKey);
    encrypt(textToEncrypt.trim());
    return getEncryptedString();
  }

  public String getDecryptedWithKey(String textToDecrypt, String secretKey) {
    setKey(secretKey);
    decrypt(textToDecrypt.trim());
    return getDecryptedString();
  }

}