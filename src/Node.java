public class Node {
	
	public int cd; // the cutting dimension
	public int[] data; //the kdimensional data
	public Node left; // the left subtree
	public Node right; // the right subtree

	public Node(){}
	public Node(int cd, int[] data, Node left, Node right) {
		super();
		this.cd = cd;
		this.data = data;
		this.left = left;
		this.right = right;
	}		
}

