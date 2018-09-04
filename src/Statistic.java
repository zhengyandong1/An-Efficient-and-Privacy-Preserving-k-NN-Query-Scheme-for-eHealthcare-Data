import java.util.List;

public class Statistic {

	
	 public Statistic() {
		
	}
	 
	 public static void quickSort(int array[], int low, int high) {
	        int pivot, p_pos, i, t;
	        if (low < high) {
	            p_pos = low;
	            pivot = array[p_pos];
	            for (i = low + 1; i <= high; i++)
	                if (array[i] > pivot) {
	                    p_pos++;
	                    t = array[p_pos];
	                    array[p_pos] = array[i];
	                    array[i] = t;
	                }
	            t = array[low];
	            array[low] = array[p_pos];
	            array[p_pos] = t;
	            
	            quickSort(array, low, p_pos - 1); // sort the left part
	            quickSort(array, p_pos + 1, high); // sort the right part
	        }
	 }
	        

		// Calculate the distance of two points
	 public static int dist(int[] inputData1, int[] inputData2) {
	  if (inputData1 == null || inputData1.length == 0)
		  return -1;
	  if (inputData2 == null || inputData2.length == 0)
		  return -1; 
	  int distance = 0; 
	  for(int i = 0; i < inputData1.length; i++) {
		  distance = distance + (inputData1[i] - inputData2[i])*(inputData1[i] - inputData2[i]);
	  }
	  return distance;
	 }
	 
	 
	 
	// Calculate the Minimum Location
	 public static int getMaxLoc(double[] inputData) {
	  if (inputData == null || inputData.length == 0)
	   return -1;
	  int len = inputData.length;
	  double max = inputData[0];
	  int loc = 0;
	  for (int i = 0; i < len; i++) {
	   if (max < inputData[i]) {
		   max = inputData[i];
		   loc = i;
	   }
		   
	  }
	  return loc;
	 }

		// Calculate the Minimum
	 public static int getMin(Integer[] inputData) {
	  if (inputData == null || inputData.length == 0)
	   return -1;
	  int len = inputData.length;
	  int min = inputData[0];
	  for (int i = 0; i < len; i++) {
	   if (min > inputData[i]) {
		   min = inputData[i];
	   }
	  }
	  return min;
	 }

	 
	// Calculate the Maximum
	 public static int getMax(Integer[] inputData) {
	  if (inputData == null || inputData.length == 0)
	   return -1;
	  int len = inputData.length;
	  int max = inputData[0];
	  for (int i = 0; i < len; i++) {
	   if (max < inputData[i]) 
		   max = inputData[i];
	  };
	  return max;
	 }

	 // Calculate the Sum
	 public static int getSum(int[] inputData) {
	  if (inputData == null || inputData.length == 0)
	   return -1;
	  int len = inputData.length;
	  int sum = 0;
	  for (int i = 0; i < len; i++) {
	   sum = sum + inputData[i];
	  }

	  return sum;

	 }

	 // Calculate the length
	 public static int getCount(int[] inputData) {
	  if (inputData == null)
	   return -1;

	  return inputData.length;
	 }

	 // Calculate the Average
	 public static double getAverage(int[] inputData) {
	  if (inputData == null || inputData.length == 0)
	   return -1;
	  int len = inputData.length;
	  double result;
	  result = getSum(inputData) / len;
	  
	  return result;
	 }

	 // Calculate the Sum of Square
	 public static int getSquareSum(int[] inputData) {
	  if(inputData==null||inputData.length==0)
	      return -1;
	     int len=inputData.length;
	  int sqrsum = 0;
	  for (int i = 0; i <len; i++) {
	   sqrsum = sqrsum + inputData[i] * inputData[i];
	  }
	  
	  return sqrsum;
	 }

	 // Calculate the Variance
	 public static double getVariance(int[] inputData) {
	  int count = getCount(inputData);
	  int sqrsum = getSquareSum(inputData);
	  double average = getAverage(inputData);
	  double result;
	  result = (sqrsum - count * average * average) / count;

	  return result; 
	 }
	 
	 public static boolean arrayCompare(int[] a, int[] b) {
		 
		 boolean flag = true;
		 for(int i = 0; i < a.length; i++) {
			 if (a[i] != b[i]) {
				 flag = false;
			 }
		 }
		 
		 return flag;
		 
	 }
	 
	 public static void insert(int[] Point, int dist, List<int[]> BQPoint, List<Integer> BQDist) {
		 
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
		 
	 }

	}