import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class EncryptKDTree {
	
	public static EncryptNode Encrypt(Node tree, Paillier.PublicKey pubkey, SecretKey AESKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		EncryptNode encryptNode = new EncryptNode();
		if (tree == null) {
			encryptNode = null;
			return encryptNode;
		}
			
		encryptNode.cd = tree.cd;
		BigInteger[] data = new BigInteger[tree.data.length + 1];
		
		// encrypt the plaintext data
		for(int i = 0; i < tree.data.length; i++) {
			BigInteger m = new BigInteger(Integer.toString(tree.data[i]));
			try {
				data[i] = Paillier.encrypt(m, pubkey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// encrypt the whole int array
		byte[] ciphertext = AES.Encrytor(Arrays.toString(tree.data), AESKey);
		data[data.length -1] = new BigInteger(ciphertext);
		
		encryptNode.data = data;
		encryptNode.right = Encrypt(tree.right, pubkey, AESKey);
		encryptNode.left = Encrypt(tree.left, pubkey, AESKey);
		
		return encryptNode;
	}
	
	public static BigInteger[] EncryptPoint(int[] point, Paillier.PublicKey pubkey, SecretKey AESKey) throws Exception {
		
		BigInteger[] encryptPoint = new BigInteger[point.length + 1];
		for(int i = 0; i < point.length; i++) {
			encryptPoint[i] = Paillier.encrypt(new BigInteger(Integer.toString(point[i])), pubkey);
		}
		
		byte[] ciphertext = AES.Encrytor(Arrays.toString(point), AESKey);
		encryptPoint[point.length] = new BigInteger(ciphertext);
		
		return encryptPoint;
	}
	
	public static int[] DecryptPoint(BigInteger[] encryPoint, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		
		int[] point = new int[encryPoint.length - 1];
		for(int i = 0; i < point.length; i++) 
			point[i] = Paillier.decrypt(encryPoint[i], pubkey, prikey).intValue();
		
		return point;
	}

	public static void printTree(EncryptNode node, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		if (node != null) {
			System.out.print(node.cd + ",");
			for(int i = 0; i < node.data.length - 1; i++) {
				
				int x = Paillier.decrypt(node.data[i], pubkey, prikey).intValue();
				System.out.print(x + ",");
			}
			System.out.println();
			printTree(node.left, pubkey, prikey);
			printTree(node.right, pubkey, prikey);
		}
	}
	
	
	public static EncryptNode insertTree(BigInteger[] dataPoint, EncryptNode node, int cd, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		
		int leftCd = 0, rightCd = 0;
		int k = dataPoint.length - 1;
		if (node != null) {
			if (node.right == null) {
				rightCd = (cd + 1) % k;
			}else {
				rightCd = node.right.cd;
			}
			
			if (node.left == null) {
				leftCd = (cd + 1) % k;
			}else {
				leftCd = node.left.cd;
			}
		}
		
		if (node == null){
			node = new EncryptNode(cd, dataPoint, null, null);
		}else if (dataPoint[k] == node.data[k]) {
			System.out.println("error!");
		}else if (EncryptStatistic.Compare(dataPoint[cd], node.data[cd], pubkey, prikey) <= 0){
				node.left = insertTree(dataPoint, node.left, leftCd, pubkey, prikey);
		}else {
				node.right = insertTree(dataPoint, node.right, rightCd, pubkey, prikey);
		}
		
		return node;
	}

	
	public static BigInteger[] findMin(EncryptNode node, int i, int cd, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		
		if (node == null) {
			return null;
		}
		
		int leftCd = 0, rightCd = 0;
		int k = node.data.length - 1;
		if (node != null) {
			if (node.right == null) {
				rightCd = (cd + 1) % k;
			}else {
				rightCd = node.right.cd;
			}
			
			if (node.left == null) {
				leftCd = (cd + 1) % k;
			}else {
				leftCd = node.left.cd;
			}
		}
		
		if (cd == i) {
			if (node.right == null) {
				return node.data;
			}else if (node.left == null) {
				return node.data;
			} else{
				return findMin(node.left, i, leftCd, pubkey, prikey);
			}
		}else {
			
			BigInteger[][] a = {findMin(node.left, i, leftCd, pubkey, prikey), findMin(node.right, i, rightCd, pubkey, prikey), node.data};
			
			BigInteger[] min = new BigInteger[k + 1];
			for(int j = 0; j < a.length; j++) {
				 if  (a[j] != null) {
					 min = a[j];
					 break;
				 }
			}
			for(int j = 0; j < a.length; j++) {
				if (a[j] != null) {
					if (EncryptStatistic.Compare(a[j][i], min[i], pubkey, prikey) == -1) 
						min = a[j];
				}
			}
			return min;
		}
	}
	
	public static EncryptNode delete(BigInteger[] dataPoint, EncryptNode node, int cd, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		
		if(node == null) {
			return null;
		}
		int k = dataPoint.length - 1;
		
		if (dataPoint[k].compareTo(node.data[k]) == 0) {
//			System.out.println("find the point");
			if (node.right != null) {
				
				node.data = findMin(node.right, cd, node.right.cd, pubkey, prikey);
				
				node.right = delete(node.data, node.right, node.right.cd, pubkey, prikey);
				
			} else if (node.left != null) {
				node.data = findMin(node.left, cd, node.left.cd, pubkey, prikey);
				node.right = delete(node.data, node.left, node.left.cd, pubkey, prikey);
			}else
				node = null;
		}else if (EncryptStatistic.Compare(dataPoint[cd], node.data[cd], pubkey, prikey) == -1) {
			
			if(node.left != null) 
				node.left = delete(dataPoint, node.left, node.left.cd, pubkey, prikey);
		}else {
			if(node.right != null)
				node.right = delete(dataPoint, node.right, node.right.cd, pubkey, prikey);
		}
		return node;
	}

	public static int kNN(int n, BigInteger[] dataPoint, EncryptNode node, int cd, List<BigInteger[]> BQPoint, List<Integer> BQDist, Paillier.PublicKey pubkey, Paillier.PrivateKey prikey) throws Exception {
		
		if (node == null) {
			return 0;
		}
		
		int k = dataPoint.length - 1;
		int leftCd = 0, rightCd = 0;
		if (node != null) {
			if (node.right == null) {
				rightCd = (cd + 1) % k;
			}else {
				rightCd = node.right.cd;
			}
			
			if (node.left == null) {
				leftCd = (cd + 1) % k;
			}else {
				leftCd = node.left.cd;
			}
		}
		
		int d = EncryptStatistic.dist(dataPoint, node.data, pubkey, prikey);
		Integer[] a = new Integer[BQDist.size()];
		BQDist.toArray(a);
		double bestdist = EncryptStatistic.getMax(a);
		

		if (BQPoint.size() < n || d < bestdist) {
			EncryptStatistic.insert(n, node.data, d, BQPoint, BQDist);
			Integer[] b = new Integer[BQDist.size()];
			BQDist.toArray(b);
			bestdist = EncryptStatistic.getMax(b);
		}
		
		if (EncryptStatistic.Compare(dataPoint[cd], node.data[cd], pubkey, prikey) == -1) {
			kNN(n, dataPoint, node.left, leftCd, BQPoint, BQDist, pubkey, prikey);
			
		}else {
			kNN(n, dataPoint, node.right, rightCd, BQPoint, BQDist, pubkey, prikey);
		}
		
		
		if (EncryptStatistic.absDifference(dataPoint[cd], node.data[cd], pubkey, prikey) < bestdist) {
			if (EncryptStatistic.Compare(dataPoint[cd], node.data[cd], pubkey, prikey) == -1)
				kNN(n, dataPoint, node.right, rightCd, BQPoint, BQDist, pubkey, prikey);
			else
				kNN(n, dataPoint, node.left, leftCd, BQPoint, BQDist, pubkey, prikey);
		}
		
		return 1;
	}
	
	
	
	
	
	
	

}
