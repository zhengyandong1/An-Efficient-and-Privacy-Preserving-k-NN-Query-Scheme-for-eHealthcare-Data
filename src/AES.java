import java.security.InvalidKeyException;    
import java.security.NoSuchAlgorithmException;    
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;    
import javax.crypto.Cipher;    
import javax.crypto.IllegalBlockSizeException;    
import javax.crypto.KeyGenerator;    
import javax.crypto.NoSuchPaddingException;    
import javax.crypto.SecretKey;    
    
public class AES {     
        
    public AES(){}
    
    // Generate the AESKey
    public static SecretKey keyGeneration(int bitsize) throws NoSuchAlgorithmException{
    	KeyGenerator keygen;
    	SecretKey AESKey; 
    	keygen = KeyGenerator.getInstance("AES"); 
    	keygen.init(bitsize);
    	AESKey = keygen.generateKey();
    	
    	return AESKey;
    }
 
    // Encrypt
    public static byte[] Encrytor(String str, SecretKey AESKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {    
    	
    	Cipher c;  
    	c = Cipher.getInstance("AES"); 
        c.init(Cipher.ENCRYPT_MODE, AESKey);    
        byte[] src = str.getBytes(); 
        byte[] cipherByte; 
        cipherByte = c.doFinal(src);  
        
        cipherByte = Base64.getEncoder().encode(cipherByte);
        
        
        return cipherByte;    
    }    
    
    // Decrypt
    public static byte[] Decryptor(byte[] cipherByte, SecretKey AESKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {    
          
    	Cipher c;  
    	c = Cipher.getInstance("AES"); 
        c.init(Cipher.DECRYPT_MODE, AESKey); 
        byte[] plainByte; 
        plainByte = c.doFinal(Base64.getDecoder().decode(cipherByte)); 
        return plainByte;    
    }        
    
}    
