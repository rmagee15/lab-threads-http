import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 * A class that races sorting algorithms.
 * 
 * @author Joel Ross
 */
public class SortRacer {

	public static class SortMerge implements Runnable {
		public void run() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSSS"); //for output

			Integer[] numbers;
			numbers = shuffled((int)Math.pow(10,7), 448);

			System.out.println("Starting merge sort at "+dateFormat.format(new Date()));

			Sorting.mergeSort(numbers);

			System.out.println("Mergesort finished at "+dateFormat.format(new Date())+" !");

		}
	}

	public static class SortQuick implements Runnable {
		public void run() {
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSSS"); //for output

			Integer[] numbers;
			numbers = shuffled((int)Math.pow(10,7), 448);

			System.out.println("Starting quick sort at "+dateFormat.format(new Date()));

			Sorting.quickSort(numbers);

			System.out.println("Quicksort finished at "+dateFormat.format(new Date())+" !");

		}
	}

	public static void main(String[] args) 
	{
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSSS"); //for output
		Integer[] nums;

		
		/** Merge Sort 
		nums = shuffled((int)Math.pow(10,7), 448); //a list of shuffled 10 million numbers

		System.out.println("Starting merge sort at "+dateFormat.format(new Date()));
		Sorting.mergeSort(nums);
		System.out.println("Merge sort finished at "+dateFormat.format(new Date())+" !");

		
		/** Quick Sort *
		nums = shuffled((int)Math.pow(10,7), 448); //a list of shuffled 10 million numbers
		System.out.println("Starting quicksort at "+dateFormat.format(new Date()));
		Sorting.quickSort(nums);
		System.out.println("Quicksort finished at "+dateFormat.format(new Date())+" !");

		Thread mergeing = new Thread(new SortMerge);
		sorting.start();*/
		Thread mergeing = new Thread(new SortMerge());
		Thread quicking = new Thread(new SortQuick());
		mergeing.start();
		quicking.start();
	}
	
	
	/**
	 * A utility method that returns a shuffled (randomly sorted) array of integers from 1 to the given number.
	 * @param n The number of numbers to shuffle
	 * @param seed A random seed, if less than 0 then unseeded
	 * @return An array of shuffled integers
	 */
	public static Integer[] shuffled(int n, int seed)
	{
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for(int i=0; i<n; i++) {
			nums.add(i+1);
		}
		if(seed >= 0)
			Collections.shuffle(nums, new Random(seed));
		else
			Collections.shuffle(nums);
		return nums.toArray(new Integer[0]);		
	}
	
}