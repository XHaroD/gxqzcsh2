 package com.carry.util;

 import javax.crypto.*;
 import javax.crypto.spec.SecretKeySpec;
 import java.io.*;
 import java.security.Key;
 import java.security.SecureRandom;

 /** 
  * <p> 
  * AES���ܽ��ܹ��߰� 
  * </p> 
  *  
  * @author IceWee 
  * @date 2012-5-18 
  * @version 1.0 
  */ 
 public class AESUtils { 
   
     private static final String ALGORITHM = "AES"; 
     private static final int KEY_SIZE = 128; 
     private static final int CACHE_SIZE = 1024; 
     public static final String KEY="lRiD2bx/qeSHARR0WEHFpA==";
       
     /** 
      * <p> 
      * ��������Կ 
      * </p> 
      *  
      * @return 
      * @throws Exception 
      */ 
     public static String getSecretKey() throws Exception { 
         return getSecretKey(null); 
     } 
       
     /** 
      * <p> 
      * �����Կ 
      * </p> 
      *  
      * @param seed ��Կ���� 
      * @return 
      * @throws Exception 
      */ 
     public static String getSecretKey(String seed) throws Exception { 
         KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM); 
         SecureRandom secureRandom; 
         if (seed != null && !"".equals(seed)) { 
             secureRandom = new SecureRandom(seed.getBytes()); 
         } else { 
             secureRandom = new SecureRandom(); 
         } 
         keyGenerator.init(KEY_SIZE, secureRandom);  
         SecretKey secretKey = keyGenerator.generateKey();  
         return Base64Utils.encode(secretKey.getEncoded()); 
     } 
       
     /** 
      * <p> 
      * ���� 
      * </p> 
      *  
      * @param data 
      * @param key 
      * @return 
      * @throws Exception 
      */ 
     public static byte[] encrypt(byte[] data, String key) throws Exception { 
         Key k = toKey(Base64Utils.decode(key)); 
         byte[] raw = k.getEncoded();  
         SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);  
         Cipher cipher = Cipher.getInstance(ALGORITHM);  
         cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); 
         return cipher.doFinal(data); 
     } 
       
     /** 
      * <p> 
      * �ļ����� 
      * </p> 
      *  
      * @param key 
      * @param sourceFilePath 
      * @param destFilePath 
      * @throws Exception 
      */ 
     public static void encryptFile(String key, String sourceFilePath, String destFilePath) throws Exception { 
         File sourceFile = new File(sourceFilePath); 
         File destFile = new File(destFilePath);  
         if (sourceFile.exists() && sourceFile.isFile()) { 
             if (!destFile.getParentFile().exists()) { 
                 destFile.getParentFile().mkdirs(); 
             } 
             destFile.createNewFile(); 
             InputStream in = new FileInputStream(sourceFile); 
             OutputStream out = new FileOutputStream(destFile); 
             Key k = toKey(Base64Utils.decode(key)); 
             byte[] raw = k.getEncoded();  
             SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);  
             Cipher cipher = Cipher.getInstance(ALGORITHM);  
             cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); 
             CipherInputStream cin = new CipherInputStream(in, cipher); 
             byte[] cache = new byte[CACHE_SIZE]; 
             int nRead = 0; 
             while ((nRead = cin.read(cache)) != -1) { 
                 out.write(cache, 0, nRead); 
                 out.flush(); 
             } 
             out.close(); 
             cin.close(); 
             in.close(); 
         } 
     } 
       
     /** 
      * <p> 
      * ���� 
      * </p> 
      *  
      * @param data 
      * @param key 
      * @return 
      * @throws Exception 
      */ 
     public static byte[] decrypt(byte[] data, String key) throws Exception { 
         Key k = toKey(Base64Utils.decode(key)); 
         byte[] raw = k.getEncoded();  
         SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);  
         Cipher cipher = Cipher.getInstance(ALGORITHM);  
         cipher.init(Cipher.DECRYPT_MODE, secretKeySpec); 
         return cipher.doFinal(data); 
     } 
       
     /** 
      * <p> 
      * �ļ����� 
      * </p> 
      *  
      * @param key 
      * @param sourceFilePath 
      * @param destFilePath 
      * @throws Exception 
      */ 
     public static void decryptFile(String key, String sourceFilePath, String destFilePath) throws Exception { 
         File sourceFile = new File(sourceFilePath); 
         File destFile = new File(destFilePath);  
         if (sourceFile.exists() && sourceFile.isFile()) { 
             if (!destFile.getParentFile().exists()) { 
                 destFile.getParentFile().mkdirs(); 
             } 
             destFile.createNewFile(); 
             FileInputStream in = new FileInputStream(sourceFile); 
             FileOutputStream out = new FileOutputStream(destFile); 
             Key k = toKey(Base64Utils.decode(key)); 
             byte[] raw = k.getEncoded();  
             SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);  
             Cipher cipher = Cipher.getInstance(ALGORITHM);  
             cipher.init(Cipher.DECRYPT_MODE, secretKeySpec); 
             CipherOutputStream cout = new CipherOutputStream(out, cipher); 
             byte[] cache = new byte[CACHE_SIZE]; 
             int nRead = 0; 
             while ((nRead = in.read(cache)) != -1) { 
                 cout.write(cache, 0, nRead); 
                 cout.flush(); 
             } 
             cout.close(); 
             out.close(); 
             in.close(); 
         } 
     } 
       
     /** 
      * <p> 
      * ת����Կ 
      * </p> 
      *  
      * @param key 
      * @return 
      * @throws Exception 
      */ 
     private static Key toKey(byte[] key) throws Exception { 
         SecretKey secretKey = new SecretKeySpec(key, ALGORITHM); 
         return secretKey; 
     } 
       
} 
