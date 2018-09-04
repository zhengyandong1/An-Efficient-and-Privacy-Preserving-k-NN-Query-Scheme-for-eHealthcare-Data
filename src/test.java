import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class test {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br =null;
	    br = new BufferedReader(new FileReader("src/std_synthetic_1000.txt"));
	    List<String> list =new ArrayList<String>();
	    String temp =null;
	    while((temp = br.readLine()) !=null){
	    list.add(temp);
	    }

	    String a[][] = new String[list.size()][];
	    for(int i = 0; i < list.size(); i++) {
	    	String aa = list.get(i);
	    	a[i] = aa.split("\t");
	    	System.out.println(a[i][0]);
	    	break;
	   }
	    

	}

}
