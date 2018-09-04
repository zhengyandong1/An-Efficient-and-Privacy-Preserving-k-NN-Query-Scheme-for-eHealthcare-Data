import java.util.*;

import javax.crypto.SecretKey;

public class KDTree {
	
	// build the kdTree according to the dataSet
	public Node buildTree(List<int[]> dataSet) {
		
		int N = dataSet.size(); // the size of the dataSet
		if (N == 0) {
			return null;
		}
		int k = dataSet.get(0).length;
		
		Node node = new Node();
		double[] var = new double[k];
		for(int i = 0; i < k; i++) {
			int[] temp = new int[N];
			for(int j = 0; j < N; j++)
				temp[j] = dataSet.get(j)[i];
			var[i] = Statistic.getVariance(temp);
		}
		int cd = Statistic.getMaxLoc(var);
		int[] data_cd = new int[N];
		for(int i = 0; i < N; i++) {
			data_cd[i] = dataSet.get(i)[cd];
		}
		
		Statistic.quickSort(data_cd, 0, N - 1);
		
		int mid = data_cd[(N-1)/2];
		int[] data = new int[k];
		List<int[]> leftTree_data = new ArrayList<int[]>();
		List<int[]> rightTree_data = new ArrayList<int[]>();
		
		
		boolean flag = false;
		for(int i = 0; i < N; i++) {
			if (dataSet.get(i)[cd] == mid && flag == false) {
				data = dataSet.get(i);
				flag = true;
			}else if (dataSet.get(i)[cd] < mid) {
				leftTree_data.add(dataSet.get(i));
			}else {
				rightTree_data.add(dataSet.get(i));
			}
		}
		node.cd = cd;
		node.data = data;
		node.left = buildTree(leftTree_data);
		node.right = buildTree(rightTree_data);
		
		
		return node;
	}
	
	public static void printTree(Node node) {
		if (node != null) {
			for(int i = 0; i < node.data.length; i++) {
				System.out.print(node.data[i] + ",");
			}
			System.out.println();
			printTree(node.left);
			printTree(node.right);
		}
	}
	
	public static void main(String[] args) {
		
		KDTree kd = new KDTree();
		
		int[][] data = {{2,3}, {5,4}, {9,6}, {4,7}, {8,1}, {7,2}};
		List<int[]> dataSet = new ArrayList<int[]>();
		
		for(int i = 0; i < data.length; i++)
			dataSet.add(data[i]);
		  
		Node tree = kd.buildTree(dataSet);
		KDTree.printTree(tree);
	}

} 
