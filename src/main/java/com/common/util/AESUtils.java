package com.common.util;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public final class AESUtils {
	
    //static String PASS = "abcdef0123456789";
    
    public final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    

    private static final String KEY_ALGORITHM = "AES";//Rijndael
    
    //参数分别代表 算法名称/加密模式/数据填充方式
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NOPadding";
	
	
	@Test
    public  void main() throws Exception {
    	//System.err.println(generatePass());
		/*String privateKey = generatePrivateKey(16);
		System.out.println(encryptAES("java", privateKey)+privateKey);*/
		for(int i =10;i<2000;i++) {
			String a = encryptAES("12");
			System.out.println(a);
			System.out.println(decryptAES(a));
		}
    }
	
	
	
	/**
	 * 
	 * @return
	 */
    public static String generatePass() {
    	String pass = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    	return pass;
    }
    
    /**
     * 生成秘钥
     * @param length
     * @return
     */
    public static synchronized String generatePrivateKey(int length) {
    	StringBuffer buffer = new StringBuffer();
    	int i = 0;
    	Random random = new Random();
    	while(i<length) {
    		buffer.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
    		i++;
    	}
    	String privateKey = buffer.toString();
    	return privateKey;
    }
    
    public static synchronized String encryptAES(String data)  throws Exception{
    	String privateKey = generatePrivateKey(16);
    	String encryptAES = encryptAES(data, privateKey);
    	int index = new Random().nextInt(ALPHABET.length());
    	return ALPHABET.charAt(index)+encryptAES.substring(0, index/24)+privateKey+encryptAES.substring(index/24);
    	
    }
    
    public static synchronized String decryptAES(String data)  throws Exception{
    	int index = ALPHABET.indexOf(data.substring(0, 1));
    	String privateKey = data.substring(index/24+1,index/24+16+1);
    	String decryptAES =data.substring(1,index/24+1)+data.substring(index/24+1+16);
    	return decryptAES(decryptAES, privateKey);
    	
    }
    
    public static String encryptAES(String data, String privateKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);   
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
           /* SecretKeySpec keyspec = new SecretKeySpec(PASS.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(PASS.getBytes());*/
            SecretKeySpec keyspec = new SecretKeySpec(privateKey.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(privateKey.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            return Base64.encodeBase64String(cipher.doFinal(plaintext));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptAES(String data, String privateKey) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            /*SecretKeySpec keyspec = new SecretKeySpec(PASS.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(PASS.getBytes());*/
            SecretKeySpec keyspec = new SecretKeySpec(privateKey.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(privateKey.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] result = cipher.doFinal(Base64.decodeBase64(data.getBytes()));
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
