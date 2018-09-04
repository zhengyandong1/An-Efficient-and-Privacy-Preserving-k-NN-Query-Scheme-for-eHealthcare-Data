import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.crypto.SecretKey;

public class EncryptStatistic {
	
	public static int Compare(BigInteger c1, BigInteger c2, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		
		BigInteger r = new BigInteger(256, new Random());
		
		BigInteger nsquare = pubkey.getN().pow(2);
		BigInteger c = c1.multiply(c2.modInverse(nsquare)).modPow(r, nsquare);
		
		BigInteger m = Paillier.decrypt(c, pubkey, prikey);
		
		if(m.compareTo(BigInteger.ZERO) == 0 || m.bitLength() == pubkey.getN().bitLength())
			return -1;
		else
			return 1;

	}
	
	
	public static int dist(BigInteger[] c1, BigInteger[] c2, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		
		int k = c1.length - 1;
		BigInteger[] c = new BigInteger[k];
		BigInteger nsquare = pubkey.getN().pow(2);
		for(int i = 0; i < k; i++) 
			c[i] = c1[i].multiply(c2[i].modInverse(nsquare)).mod(nsquare);
		BigInteger m;
		BigInteger distance = BigInteger.ZERO;
		for(int i = 0; i < k; i++) {
			m = Paillier.decrypt(c[i], pubkey, prikey);
			m = m.pow(2).mod(pubkey.getN());
			distance = distance.add(m);
		}
		
			
		return (int) Math.sqrt(distance.doubleValue());
		
	}

//	public static BigInteger dist(BigInteger[] c1, BigInteger[] c2, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey, SecretKey AESKey) throws Exception {
//		
//		int k = c1.length - 1;
//		BigInteger[] c = new BigInteger[k];
//		BigInteger nsquare = pubkey.getN().pow(2);
//		for(int i = 0; i < k; i++) 
//			c[i] = c1[i].multiply(c2[i].modInverse(nsquare)).mod(nsquare);
//		BigInteger m;
//		BigInteger distance = BigInteger.ZERO;
//		for(int i = 0; i < k; i++) {
//			m = Paillier.decrypt(c[i], pubkey, prikey);
//			m = m.pow(2).mod(pubkey.getN());
//			distance = distance.add(m);
//		}
//		
////		System.out.println("distance" + distance);
//			
////		return Paillier.encrypt(distance, pubkey);
////		System.out.println(distance);
//		BigInteger x = new BigInteger(AES.Encrytor(new String(distance.toByteArray()), AESKey));
////		BigInteger y = new BigInteger(AES.Decryptor(x.toByteArray(), AESKey));
////		System.out.println("before encryption " + y);
////		System.out.println("after encryption " + x);
//		return x;
//	}

	
	public static double absDifference(BigInteger c1, BigInteger c2, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
	
		BigInteger nsquare = pubkey.getN().pow(2);
		BigInteger c = c1.multiply(c2.modInverse(nsquare)).mod(nsquare);
		BigInteger m = Paillier.decrypt(c, pubkey, prikey);
		m = m.pow(2);
		
//		BigInteger m2 = new BigInteger(AES.Decryptor(c3.toByteArray(), AESKey));
		
		return Math.sqrt(m.intValue());
		
	}
	
	// Calculate the Maximum
	 public static double getMax(Integer[] inputData) throws Exception {
	  if (inputData == null || inputData.length == 0)
		  return Math.pow(10,13);
	  int len = inputData.length;
	  int max = inputData[0];
	  for (int i = 0; i < len; i++) {
		  if (max < inputData[i]) 
			  max = inputData[i];
	  };
	  
	  return max;
	 }
	 
		// Calculate the Maximum for encrypted data
	 public static BigInteger getEncryptMax(BigInteger[] inputData, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey, SecretKey AESKey) throws Exception {
	  if (inputData == null || inputData.length == 0)
		  return BigInteger.valueOf((int) Math.pow(10,13));
	  int len = inputData.length;
	  
//	  BigInteger max = Paillier.decrypt(inputData[0], pubkey, prikey);
	  BigInteger max = new BigInteger(AES.Decryptor(inputData[0].toByteArray(), AESKey));
	  BigInteger encryptMax = inputData[0];
	  
	  for (int i = 0; i < len; i++) {
//		  BigInteger x = Paillier.decrypt(inputData[i], pubkey, prikey);
		  BigInteger x = new BigInteger(AES.Decryptor(inputData[i].toByteArray(), AESKey));
		  if (max.compareTo(x) == -1){
			  max = x;
			  encryptMax = inputData[i];
		  }  
	  }
	  
	  return encryptMax;
	 }
	 

	// Compare Euclidean distance comparison protocol
	 
	 public static int getDisCom(BigInteger dist1, BigInteger dist2, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey, SecretKey AESKey) throws Exception {

		 
		 BigInteger d1 = new BigInteger(AES.Decryptor(dist1.toByteArray(), AESKey));
		 BigInteger d2 = new BigInteger(AES.Decryptor(dist2.toByteArray(), AESKey));
		 
//		 int d1 = Paillier.decrypt(dist1, pubkey, prikey).intValue();
//		 int d2 = Paillier.decrypt(dist2, pubkey, prikey).intValue();
		 
		 if(d1.compareTo(d2) == 1)
			 return 1;
		 else if(d1.compareTo(d2) == -1)
			 return -1;
		 else
			 return 0;
	 }

	 
	 public static void insert(int n, BigInteger[] Point, int dist, List<BigInteger[]> BQPoint, List<Integer> BQDist) {
		 
		 int i;
		 
		 for(i = 0; i < BQPoint.size(); i++) {
			 if (BQDist.get(i) > dist) {
				 BQDist.add(i, dist);
				 BQPoint.add(i, Point);				 
				 break;
			 }
		 }
		 
		 if (i == BQPoint.size()) {
			 BQDist.add(i, dist);
			 BQPoint.add(i, Point);
		 }
		 
		 if (BQDist.size() > n) {
			 BQDist.remove(BQDist.size() - 1);
			 BQPoint.remove(BQPoint.size() - 1);
		 }
		 
	 }

//	 public static void insert(int n, BigInteger[] Point, BigInteger dist, List<BigInteger[]> BQPoint, List<BigInteger> BQDist, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey, SecretKey AESKey) throws Exception {
//		 
//		 int i;
//		 
//		 for(i = 0; i < BQPoint.size(); i++) {
//			 if (getDisCom(BQDist.get(i), dist, pubkey, prikey, AESKey) == 1) {
//				 BQDist.add(i, dist);
//				 BQPoint.add(i, Point);				 
//				 break;
//			 }
//		 }
//		 
//		 if (i == BQPoint.size()) {
//			 BQDist.add(i, dist);
//			 BQPoint.add(i, Point);
//		 }
//		 
//		 if (BQDist.size() > n) {
//			 BQDist.remove(BQDist.size() - 1);
//			 BQPoint.remove(BQPoint.size() - 1);
//		 }
//		 
//	 }


	}

