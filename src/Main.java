import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		
		Paillier paillier = new Paillier();
		
		// Paillier Key Generation
		paillier.keyGeneration(512);
		Paillier.PublicKey pubkey = paillier.getPubkey();
		Paillier.PrivateKey prikey = paillier.getPrikey();
		
		// AES Key Generation
		SecretKey AESKey = AES.keyGeneration(128);
		
		// building tree time
		
		String[] dataset = {"src/std_synthetic_1000.txt", "src/std_synthetic_10000.txt", "src/std_synthetic.txt"};
		double time[][] = new double[3][3];
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				time[i][j] = 0;
		
		for(int count = 0; count < 1000; count ++) {
						
			for(int k = 0; k < dataset.length; k++) {
				
				int j = 4;
				while(j <= 8) {
					
					// DataSet
					Data d = new Data();
					int[][] data = d.data_transform(dataset[k], j);
					
					List<int[]> dataSet = new ArrayList<int[]>();
					for(int i = 0; i < data.length; i++)
						dataSet.add(data[i]);
					
					double t1 = System.currentTimeMillis();
					// Build the KDTree
					KDTree kd = new KDTree();
					Node tree = kd.buildTree(dataSet);
					double t2 = System.currentTimeMillis();
					
					time[k][j/2-2] = time[k][j/2-2] + t2 - t1;					
					j = j + 2;
				}

			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(time[i][j]*1.0/1000 + " ");
			}
			
			System.out.println();
		}		
		
		// Encrypting point time
		
//		double[] encry_time = new double[3];
//		int j = 4;
//		while(j <= 8) {
//			
//			// DataSet
//			Data d = new Data();
//			int[][] data = d.data_transform("src/std_synthetic_1000.txt", j);
//			
//			List<int[]> dataSet = new ArrayList<int[]>();
//			for(int i = 0; i < data.length; i++)
//				dataSet.add(data[i]);
//			
//			// Build the KDTree
//			KDTree kd = new KDTree();
//			Node tree = kd.buildTree(dataSet);
//			double t2 = System.currentTimeMillis();
//			// Encrypt the KDTree
//			EncryptNode encryptTree = EncryptKDTree.Encrypt(tree, pubkey, AESKey);
//			double t3 = System.currentTimeMillis();
//			
//			encry_time[j/2-2] = encry_time[j/2-2] + t3 - t2;
//						
//			j = j + 2;
//		}
//
//	for(int i = 0; i < 3; i++) {
//			System.out.print(encry_time[i]*1.0/1000 + " ");
//	}

		
		// insertion time
		
//		String[] dataset = {"src/std_synthetic_1000.txt", "src/std_synthetic_10000.txt", "src/std_synthetic.txt"};		
//		double insertion_time[][] = new double[3][3];
//		double deletion_time[][] = new double[3][3];
//		
//		for(int i = 0; i < 3; i++)
//			for(int j = 0; j < 3; j++) {
//				insertion_time[i][j] = 0;
//				deletion_time[i][j] = 0;
//			}
//				
//		
//		for(int k = 0; k < dataset.length; k++) {
//			
//			int j = 4;
//			while(j <= 8) {
//				
//				// DataSet
//				Data d = new Data();
//				int[][] data = d.data_transform(dataset[k], j);
//				
//				List<int[]> dataSet = new ArrayList<int[]>();
//				for(int i = 0; i < data.length; i++)
//					dataSet.add(data[i]);
//				
//				// Build the KDTree
//				KDTree kd = new KDTree();
//				Node tree = kd.buildTree(dataSet);
//				
//				System.out.println("finish build tree:" + dataset[k] + "the dimension: " + j);
//				
//				// Encrypt the KDTree
//				EncryptNode encryptTree = EncryptKDTree.Encrypt(tree, pubkey, AESKey);
//				
//				for(int i = 0; i < 10; i++) {
//					
//					Random random = new Random();
//					BigInteger[] encryptPoint = EncryptKDTree.EncryptPoint(dataSet.get(random.nextInt(dataSet.size()-1)), pubkey, AESKey);
//					double t1 = System.currentTimeMillis();
//					encryptTree = EncryptKDTree.delete(encryptPoint, encryptTree, encryptTree.cd, pubkey, prikey);
//					double t2 = System.currentTimeMillis();
//					encryptTree = EncryptKDTree.insertTree(encryptPoint, encryptTree, encryptTree.cd, pubkey, prikey);
//					double t3 = System.currentTimeMillis();
//					
//					insertion_time[k][j/2-2] = insertion_time[k][j/2-2] + t3 - t2;
//					deletion_time[k][j/2-2] = deletion_time[k][j/2-2] + t2 - t1;
//				}
//				j = j + 2;	
//			}
//		}
//
//	
//		// print insertion time
//		for(int i = 0; i < 3; i++) {
//			for(int j = 0; j < 3; j++) {
//				System.out.print(insertion_time[i][j]/10 + " ");
//			}			
//			System.out.println();
//		}
//		
//		// print deletion time
//		for(int i = 0; i < 3; i++) {
//			for(int j = 0; j < 3; j++) {
//				System.out.print(deletion_time[i][j]/10 + " ");
//			}			
//			System.out.println();
//		}

		
		// KNN query
//		String[] dataset = {"src/std_synthetic_1000.txt", "src/std_synthetic_10000.txt", "src/std_synthetic.txt"};
//		int[] neighbor_num = {10, 20, 30};
//		
//		for(int n = 0; n < 3; n++) {
//			
//			double kNN_time[][] = new double[3][3];
//			
//			for(int i = 0; i < 3; i++)
//				for(int j = 0; j < 3; j++) 
//					kNN_time[i][j] = 0;
//			
//			for(int k = 0; k < dataset.length; k++) {
//				
//				int j = 4;
//				while(j <= 8) {
//					
//					// DataSet
//					Data d = new Data();
//					int[][] data = d.data_transform(dataset[k], j);
//					
//					List<int[]> dataSet = new ArrayList<int[]>();
//					for(int i = 0; i < data.length; i++)
//						dataSet.add(data[i]);
//					
//					// Build the KDTree
//					KDTree kd = new KDTree();
//					Node tree = kd.buildTree(dataSet);
//					
//					System.out.println("finish build tree:" + dataset[k] + "the dimension: " + j);
//					
//					// Encrypt the KDTree
//					EncryptNode encryptTree = EncryptKDTree.Encrypt(tree, pubkey, AESKey);
//					
//					for(int i = 0; i < 10; i++) {
//						int id = (int) (Math.random()*dataSet.size());
//						BigInteger[] encryptPoint = EncryptKDTree.EncryptPoint(dataSet.get(id), pubkey, AESKey);	
//						List<BigInteger[]> BQPoint = new ArrayList<BigInteger[]>();
//						List<Integer> BQDist = new ArrayList<Integer>();
//						double t1 = System.currentTimeMillis();
//						EncryptKDTree.kNN(neighbor_num[n] + 1, encryptPoint, encryptTree, encryptTree.cd, BQPoint, BQDist, pubkey, prikey);
//						for(int u = 0; u < BQDist.size(); u++) {
//							System.out.println(BQDist.get(u));
//						}
//						
//						double t2 = System.currentTimeMillis();
//						kNN_time[k][j/2-2] = kNN_time[k][j/2-2] + t2 - t1;
//						break;
//						}
//					System.out.println(kNN_time[k][j/2-2]/10);
//					j = j + 2;
//					}						
//				}
//			// print insertion time
//			System.out.println("the number of nearst neighbor: " + neighbor_num[n]);
//			for(int i = 0; i < 3; i++) {
//				for(int j = 0; j < 3; j++) {
//					System.out.print(kNN_time[i][j]/10 + " ");
//				}			
//				System.out.println();
//			}
//			
//			
//		}
				
	
	}
 
}

