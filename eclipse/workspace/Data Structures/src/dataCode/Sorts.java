package dataCode;

public class Sorts
{
	int[] array = {2,4,6,8,10,7,9,5,3,1};
	static double[] doubleArray = {10,8,6,4,2,9,3,7,5,1};


	public static void main(String[] args) {
		//Sorts sort = new Sorts(); 
		//sort.insertionSort();
		//sort.mergeSort(doubleArray, 0, 9);
		//sort.fibStack(7);
	}

	public void insertionSort() {
		for(int i = 1; i < array.length; i++) {
			int current = array[i];
			int j = i - 1;
			while(j >= 0 && array[j] > current) {
				array[j + 1] = array[j];
				j--;
			}
			array[j + 1] = current;
		}
		for(int number: array) {
			System.out.print(number + ",");
		}
		System.out.println();
	}

	public void mergeSort(double[] A, int left, int right) {
		if(left < right) {
			int middle = (left + right)/2;
			mergeSort(A, left, middle);
			mergeSort(A, middle + 1, right);
			merge(A, left, middle, right);
		}
	}

	public void merge(double[] A, int left, int middle, int right) {
		double[] array1 = new double[middle - left + 2];
		double[] array2 = new double[right - middle + 1];
		//Fills in the two new arrays
		for(int i = 0; i < array1.length - 1; i++) {
			array1[i] = A[left + i];
		}
		for(int i = 0; i < array2.length - 1; i++) {
			array2[i] = A[middle + i + 1];
		}
		//Adds infinite to the end of both.
		array1[array1.length - 1] = Double.POSITIVE_INFINITY;
		array2[array2.length - 1] = Double.POSITIVE_INFINITY;
		//Sorts
		int i = 0;
		int j = 0;
		for(int k = left; k < right + 1; k++) {
			if(array1[i] <= array1[j]) {
				A[k] = array1[i];
				i++;
			}
			else {
				A[k] = array2[j];
				j++;
			}
		}
	}
}
