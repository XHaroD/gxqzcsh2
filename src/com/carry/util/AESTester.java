//AESTester.java


 package com.carry.util; 
   
 public class AESTester { 
       
     static String key; 
       
     static { 
         try { 
  key = AESUtils.getSecretKey(); 
         } catch (Exception e) { 
             e.printStackTrace(); 
         } 
     } 
   
     public static void main(String[] args) throws Exception { 
         long begin = System.currentTimeMillis(); 
//         encryptFile(); 
//         decryptFile(); 
         test(); 
         long end = System.currentTimeMillis(); 
         System.err.println("��ʱ��" + (end-begin)/1000 + "��"); 
     } 
       
     static void encryptFile() throws Exception { 
         String sourceFilePath = "D:/demo.mp4"; 
         String destFilePath = "D:/demo_encrypted.mp4"; 
         AESUtils.encryptFile(key, sourceFilePath, destFilePath); 
     } 
       
     static void decryptFile() throws Exception { 
         String sourceFilePath = "D:/demo_encrypted.mp4"; 
         String destFilePath = "D:/demo_decrypted.mp4"; 
         AESUtils.decryptFile(key, sourceFilePath, destFilePath); 
     } 
       
     static void test() throws Exception { 
         String source = "xagx_admin_sc"; 
         System.err.println("原值:\t" + source); 
         byte[] inputData = source.getBytes(); 
         inputData = AESUtils.encrypt(inputData, "lRiD2bx/qeSHARR0WEHFpA=="); 
         System.err.println("加密后:\t" + Base64Utils.encode(inputData));
         byte[] outputData = AESUtils.decrypt(inputData, "G18m8zUDAGs8HP42qHf4hw=="); 
         
         String outputStr = new String(outputData); 
         System.err.println("解密后:\t" + outputStr); 
         
         System.out.println(key);
     } 
   
 } 
