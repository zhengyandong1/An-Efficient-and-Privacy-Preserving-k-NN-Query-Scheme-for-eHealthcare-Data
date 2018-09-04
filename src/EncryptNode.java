import java.math.BigInteger;

public class EncryptNode {
	
		public int cd; // the cutting dimension
		public BigInteger[] data; //the kdimensional data
		public EncryptNode left; // the left subtree
		public EncryptNode right; // the right subtree

		public EncryptNode(){}
		public EncryptNode(int cd, BigInteger[] data, EncryptNode left, EncryptNode right) {
	
		this.cd = cd;
		this.data = data;
		this.left = left;
		this.right = right;
	}	
}
