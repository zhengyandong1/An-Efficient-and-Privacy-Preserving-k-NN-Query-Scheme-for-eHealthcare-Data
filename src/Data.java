import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
	
	public int[][] data_transform(String file, int dim) throws IOException{

		BufferedReader br =null;
	    br =new BufferedReader(new FileReader(file));
	    List<String> list =new ArrayList<String>();
	    String temp =null;
	    while((temp = br.readLine()) !=null){
	    list.add(temp);
	    }
	    if(list.size() <=0){
	    	return null;
	    	}
	    String a[][] = new String[list.size()][];
	    for(int i = 0; i < list.size(); i++) {
	    	String aa = list.get(i);
	    	a[i] = aa.split("\t");
	   }
	    
	    int b[][] = new int[a.length][dim];
	    for(int i = 0; i < a.length; i++ )
	    	for(int j = 0; j < dim; j++)
	    		b[i][j] = Integer.parseInt(a[i][j]);

	    return b;
	    
	}	
	
//	public static void main(String[] args) throws IOException {
//		
//		Data d = new Data();
//		int b[][] = d.data_transform("C:/Users/user/Desktop/data_diabete.txt", 2);
//		
//	    for(int i = 0; i < 10; i++ ) {
//	    	for(int j = 0; j < 8; j++) {
//	    		System.out.print(b[i][j] + " ");
//	    	}
//	    	System.out.println();
//	    }
//		
//		
//	}
	
	
	
}
