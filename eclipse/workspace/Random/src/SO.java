import java.util.Arrays;


public class SO
{
	static int[][] array = new int[10][10];
	public static void main(String[] args) {
		int[][] newArray = Arrays.copyOf(array, 10);
		newArray[0][0] = 1;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.println(array[i][j]);
			}
		}
	}
}
