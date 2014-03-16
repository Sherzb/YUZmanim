package lab2;

import java.util.Arrays;
import java.util.Scanner;

import lab1.Gate;

public class Calculator
{
	private Gate g = new Gate();


	public static void main(String[] args) {
		Calculator c = new Calculator();
		c.test();
	}

	public int[] halfAdder(int a, int b) 
	{
		int[] array = new int[2];
		array[0] = g.And(a, b); //Carry
		array[1] = g.Xor(a, b); //Sum
		return array;
	}

	public int[] fullAdder(int a, int b, int c)
	{
		int[] array = new int[2];
		array[0] = g.Or3Way(g.And(a, b), g.And(b,c), g.And(a,c)); //Carry
		array[1] = g.Xor(a, g.Xor(b,c)); //Sum
		return array;
	}

	public int[] add16(int[] a, int[] b)
	{
		int[] array = new int[16];
		int carry = 0;
		for(int i = 15; i >= 0; i--) {
			int[] temp = fullAdder(a[i], b[i], carry);
			array[i] = temp[1];
			carry = temp[0];
		}
		return array;
	}

	public int[] inc16(int[] a)
	{
		int[] one = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
		return add16(a, one);
	}

	public int[][] ALU(int[] x, int[] y, int zx, int nx, int zy, int ny, int f, int no)
	{
		int[][] array = new int[2][]; 
		array[0] = new int[16]; //16 bit outputs
		array[1] = new int[2]; //zr and ng
		for(int i = 0; i < 16; i++) {
			x[i] = g.And(x[i], g.Not(zx)); //zx
			y[i] = g.And(y[i], g.Not(zy)); //zy
			x[i] = g.Xor(x[i], nx); //nx
			y[i] = g.Xor(y[i], ny); //ny
		}
		int[] f0 = g.And16(x, y);
		int[] f1 = add16(x, y);
		array[0] = g.Mux16(f0, f1, f); 
		for(int i = 0; i < 16; i++) {
			array[0][i] = g.Xor(array[0][i], no);
		}
		array[1][0] = g.Not(g.Or16Way(array[0])); //zr
		array[1][1] = array[0][0]; //ng
		return array;
	}
	
	/**
	 * Done with an if-else, mostly because I was too lazy to do Enums, even though I should have.
	 */
	public void test() {
		Scanner s = new Scanner(System.in);
		int a, b, c, d, e, g;
		int[] arr1;
		int[] arr2 = {};
		while (true) {
			System.out.println("Type" + "\n1 for the halfAdder" + "\n2 for the fullAdder" +
					"\n3 for the add16" + "\n4 for the inc16" + "" + "\n5 for the ALU" + "\n6 to quit" );
			int input = s.nextInt();
			if (input == 1) {
				System.out.println("Input one number at a time, pressing enter after each one");
				a = s.nextInt();
				b = s.nextInt();
				arr1 = halfAdder(a,b);
				System.out.println("Sum: " + arr1[1] + "\nCarry: " + arr1[0]);
			}
			else if (input == 2) {
				System.out.println("Input one number at a time, pressing enter after each one");
				a = s.nextInt();
				b = s.nextInt();
				c = s.nextInt();
				arr1 = fullAdder(a,b,c);
				System.out.println("Sum: " + arr1[1] + "\nCarry: " + arr1[0]);
			}
			else if (input == 3) {
				System.out.println("Input one array at a time (no commas or spaces), pressing enter after each one");
				arr1 = toArray(s.next());
				arr2 = toArray(s.next());
				System.out.println("In1: " + Arrays.toString(arr1));
				System.out.println("In2: " + Arrays.toString(arr2));
				System.out.println("Sum: " + Arrays.toString(add16(arr1, arr2)));
			}
			else if (input == 4) {
				System.out.println("Input the array (no commas or spaces)");
				arr1 = toArray(s.next());
				System.out.println("In1: " + Arrays.toString(arr1));
				System.out.println("Inc: " + Arrays.toString(inc16(arr1)));
			}
			else if (input == 5) {
				System.out.println("Input one array at a time (no commas or spaces), pressing enter after each one");
				arr1 = toArray(s.next());
				arr2 = toArray(s.next());
				System.out.println("Input one number at a time, pressing enter after each one\n" + 
				"The order is zx, nx, zy, ny, f, no");
				a = s.nextInt();
				b = s.nextInt();
				c = s.nextInt();
				d = s.nextInt();
				e = s.nextInt();
				g = s.nextInt();
				int[][] result = ALU(arr1, arr2, a, b, c, d, e, g);
				System.out.println("In1: " + Arrays.toString(arr1));
				System.out.println("In2: " + Arrays.toString(arr2));
				System.out.println("Output number: " + Arrays.toString(result[0]));
				System.out.println("zr: " + result[1][0] + " ng: " + result[1][1]);
			}
			else if (input == 6) {
				return;
			}
			else {
				System.out.println("Invalid Input");
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
	
	public int[] toArray(String input)
	{
		int[] array = new int[input.length()];
		for (int i = 0; i < input.length(); i++) {
			array[i] = Character.getNumericValue(input.charAt(i));
		}
		return array;
	}
}
