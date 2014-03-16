package lab1;

import java.util.Arrays;
import java.util.Scanner;

public class Gate
{
	public static void main(String[] args) 
	{
		Gate gate = new Gate();
		System.out.println(gate.Mux(-1,0,0));
		//gate.test();
	}

	public int And(int bit1, int bit2) 
	{
		return bit1 & bit2;
	}

	public int Or(int bit1, int bit2)
	{
		return bit1 | bit2;
	}

	public int Not(int bit1) 
	{
		return 1 - bit1;
	}

	//Just to help with some later gates
	public int And3Way(int bit1, int bit2, int bit3)
	{
		return And(bit1,And(bit2,bit3));
	}
	
	public int Or3Way(int bit1, int bit2, int bit3)
	{
		return Or(bit1, Or(bit2, bit3));
	}
	
	public int Nor(int bit1, int bit2)
	{
		return Not(Or(bit1, bit2));
	}

	public int Nand(int bit1, int bit2) 
	{
		return Not(And(bit1, bit2));
	}

	public int Xor(int bit1, int bit2)
	{
		return Or(And(bit1, Not(bit2)), And(bit2, Not(bit1)));
	}

	public int Mux(int bit1, int bit2, int sel)
	{
		return Or(And(Not(sel),bit1), And(sel, bit2));
	}

	public int[] DMux(int bit1, int sel) 
	{
		int[] array = new int[2];
		array[0] = And(Not(sel),bit1);
		array[1] = And(sel, bit1);
		return array;
	}

	public int[] Not16(int[] input) 
	{
		int[] array = new int[16];
		for (int i = 0; i < 16; i++) {
			array[i] = Not(input[i]);
		}
		return array;
	}

	public int[] And16(int[] input1, int[] input2) 
	{
		int[] array = new int[16];
		for (int i = 0; i < 16; i++) {
			array[i] = And(input1[i], input2[i]);
		}
		return array;
	}

	public int[] Or16(int[] input1, int[] input2) 
	{
		int[] array = new int[16];
		for (int i = 0; i < 16; i++) {
			array[i] = Or(input1[i], input2[i]);
		}
		return array;
	}

	public int[] Mux16(int[] input1, int[] input2, int sel) 
	{
		int[] array = new int[16];
		for (int i = 0; i < 16; i++) {
			array[i] = Mux(input1[i], input2[i], sel);
		}
		return array;
	}

	public int Or8Way(int[] input) 
	{
		int finalBit = 0;
		for (int i = 0; i < 8; i++) {
			finalBit = Or(finalBit, input[i]);
		}
		return finalBit;
	}
	
	public int Or16Way(int[] input) 
	{
		int finalBit = 0;
		for (int i = 0; i < 16; i++) {
			finalBit = Or(finalBit, input[i]);
		}
		return finalBit;
	}

	public int[] Mux4Way16(int[] input1, int[] input2, int[] input3, int[] input4, int[] sel) 
	{
		int[] a1 = Mux16(input1, input2, sel[1]);
		int[] a2 = Mux16(input3, input4, sel[1]);
		int[] a3 = Mux16(a1, a2, sel[0]);
		return a3;
	}

	public int[] Mux8Way16(int[] input1, int[] input2, int[] input3, int[] input4, 
			int[] input5, int[] input6, int[] input7, int[] input8, int[] sel) 
	{
		int[] selBreakdown = {sel[1], sel[2]};
		int[] a1 = Mux4Way16(input1, input2, input3, input4, selBreakdown);
		int[] a2 = Mux4Way16(input5, input6, input7, input8, selBreakdown);
		int[] a3 = Mux16(a1, a2, sel[0]);
		return a3;
	}

	public int[] DMux4Way(int bit1, int[] sel) 
	{
		int[] array = new int[4];
		int[] a1 = DMux(bit1,sel[0]);
		int[] a2 = DMux(bit1,sel[1]);
		array[0] = And(a1[0],a2[0]);
		array[1] = And(a1[0],a2[1]);
		array[2] = And(a1[1],a2[0]);
		array[3] = And(a1[1], a2[1]);
		return array;
	}

	public int[] DMux8Way(int bit1, int[] sel) 
	{
		int[] array = new int[8];
		int[] a1 = DMux(bit1,sel[0]);
		int[] a2 = DMux(bit1,sel[1]);
		int[] a3 = DMux(bit1,sel[2]);


		array[0] = And3Way(a1[0],a2[0],a3[0]);
		array[1] = And3Way(a1[0],a2[0],a3[1]);
		array[2] = And3Way(a1[0],a2[1],a3[0]);
		array[3] = And3Way(a1[0],a2[1],a3[1]);
		array[4] = And3Way(a1[1],a2[0],a3[0]);
		array[5] = And3Way(a1[1],a2[0],a3[1]);
		array[6] = And3Way(a1[1],a2[1],a3[0]);
		array[7] = And3Way(a1[1],a2[1],a3[1]);

		return array;
	}
	
	public int Mux4Way(int input1, int input2, int input3, int input4, int[] sel) 
	{
		int a1 = Mux(input1, input2, sel[1]);
		int a2 = Mux(input3, input4, sel[1]);
		int a3 = Mux(a1, a2, sel[0]);
		return a3;
	}

	public int Mux8Way(int input1, int input2, int input3, int input4, 
			int input5, int input6, int input7, int input8, int[] sel) 
	{
		int[] selBreakdown = {sel[1], sel[2]};
		int a1 = Mux4Way(input1, input2, input3, input4, selBreakdown);
		int a2 = Mux4Way(input5, input6, input7, input8, selBreakdown);
		int a3 = Mux(a1, a2, sel[0]);
		return a3;
	}

	/**
	 * I'm positive there's a way to refactor this, because there's way too much duplicate code, but this was
	 * easy and straightforward with copy/paste.
	 */
	public void test() 
	{
		Scanner s = new Scanner(System.in);	
		int a, b, sel;
		int[] a1, a2, a3, a4, a5, a6, a7, a8, aSel;
		System.out.println("Type in one of the following gates, in all capitals:");
		System.out.println("AND, OR, NOT, NAND, XOR, MUX, DMUX, NOT16, AND16, OR16, "
				+ " MUX16, OR8WAY, MUX4WAY16, MUX8WAY16, DMUX4WAY, DMUX8WAY");

		String gateString = s.nextLine();
		GatesEnum gate = GatesEnum.valueOf(gateString); //Thanks StackOverflow!

		switch(gate) {
		case AND:
			System.out.println("Type in one element at a time (either 0 or 1), and press enter after each element");
			a = s.nextInt();
			b = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + b);
			System.out.println("Output- " + And(a,b));
			break;
		case OR:
			System.out.println("Type in one element at a time (either 0 or 1), and press enter after each element");
			a = s.nextInt();
			b = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + b);
			System.out.println("Output- " + Or(a,b));
			break;
		case NOT:
			System.out.println("Type in one element, and press enter");
			a = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input- " + a);
			System.out.println("Output- " + Not(a));
			break;
		case NAND:
			System.out.println("Type in one element at a time (either 0 or 1), and press enter after each element");
			a = s.nextInt();
			b = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + b);
			System.out.println("Output- " + Nand(a,b));
			break;
		case XOR:
			System.out.println("Type in one element at a time (either 0 or 1), and press enter after each element");
			a = s.nextInt();
			b = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + b);
			System.out.println("Output- " + Xor(a,b));
			break;
		case MUX:
			System.out.println("Type in one element at a time (either 0 or 1), and press enter after each element.");
			System.out.println("The selector should be the last element typed in");
			a = s.nextInt();
			b = s.nextInt();
			sel = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + b + " " + "Sel- " + sel);
			System.out.println("Output- " + Mux(a,b,sel));
			break;
		case DMUX:
			System.out.println("Type in one element at a time (either 0 or 1), and press enter after each element.");
			System.out.println("The selector should be the last element typed in");
			a = s.nextInt();
			sel = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + "Sel- " + sel);
			System.out.println("Output- " + Arrays.toString(DMux(a,sel)));
			break;
		case NOT16:
			System.out.println("Type in the 16 elements, not separated by commas");
			a1 = toArray(s.nextLine());
			System.out.println(gateString);
			System.out.println("Input- " + Arrays.toString(a1));
			System.out.println("Output- " + Arrays.toString(Not16(a1)));
			break;
		case AND16:
			System.out.println("Type in the first of the 16 element arrays, not separating elements by commas");
			a1 = toArray(s.nextLine());
			System.out.println("Type in the second of the 16 element arrays, not separating elements by commas");
			a2 = toArray(s.nextLine());
			System.out.println(gateString);
			System.out.println("Input1- " + Arrays.toString(a1));
			System.out.println("Input2- " + Arrays.toString(a2));
			System.out.println("Output- " + Arrays.toString(And16(a1,a2)));
			break;
		case OR16:
			System.out.println("Type in the first of the 16 element arrays, not separating elements by commas");
			a1 = toArray(s.nextLine());
			System.out.println("Type in the second of the 16 element arrays, not separating elements by commas");
			a2 = toArray(s.nextLine());
			System.out.println(gateString);
			System.out.println("Input1- " + Arrays.toString(a1));
			System.out.println("Input2- " + Arrays.toString(a2));
			System.out.println("Output- " + Arrays.toString(Or16(a1,a2)));
			break;
		case MUX16:
			System.out.println("Type in the first of the 16 element arrays, not separating elements by commas");
			a1 = toArray(s.nextLine());
			System.out.println("Type in the second of the 16 element arrays, not separating elements by commas");
			a2 = toArray(s.nextLine());
			System.out.println("Type in the selector");
			sel = s.nextInt();
			System.out.println(gateString);
			System.out.println("Input1- " + Arrays.toString(a1));
			System.out.println("Input2- " + Arrays.toString(a2));
			System.out.println("Selector- " + sel);
			System.out.println("Output- " + Arrays.toString(Mux16(a1,a2,sel)));
			break;
		case OR8WAY:
			System.out.println("Type in the 16 element arrays, not separating elements by commas");
			a1 = toArray(s.nextLine());
			System.out.println(gateString);
			System.out.println("Input- " + Arrays.toString(a1));
			System.out.println("Output- " + (Or8Way(a1)));
			break;
		case MUX4WAY16:
			System.out.println("Type in each of the 16 element arrays, not separating elements by commas");
			System.out.println("Hit enter after each of the 16 bit inputs");
			a1 = toArray(s.nextLine());
			a2 = toArray(s.nextLine());
			a3 = toArray(s.nextLine());
			a4 = toArray(s.nextLine());
			System.out.println("Type in the whole selector, with the bits not separated by commas");
			aSel = toArray(s.nextLine());
			System.out.println(gateString);
			System.out.println("Input1- " + Arrays.toString(a1));
			System.out.println("Input2- " + Arrays.toString(a2));
			System.out.println("Input3- " + Arrays.toString(a3));
			System.out.println("Input4- " + Arrays.toString(a4));
			System.out.println("Selector- " + Arrays.toString(aSel));
			System.out.println("Output- " + Arrays.toString(Mux4Way16(a1,a2,a3,a4,aSel)));
			break;
		case MUX8WAY16:
			System.out.println("Type in each of the 16 element arrays, not separating elements by commas");
			System.out.println("Hit enter after each of the 16 bit inputs");
			a1 = toArray(s.nextLine());
			a2 = toArray(s.nextLine());
			a3 = toArray(s.nextLine());
			a4 = toArray(s.nextLine());
			a5 = toArray(s.nextLine());
			a6 = toArray(s.nextLine());
			a7 = toArray(s.nextLine());
			a8 = toArray(s.nextLine());
			System.out.println("Type in the whole selector, with the bits not separated by commas");
			aSel = toArray(s.nextLine());
			System.out.println(gateString);
			System.out.println("Input1- " + Arrays.toString(a1));
			System.out.println("Input2- " + Arrays.toString(a2));
			System.out.println("Input3- " + Arrays.toString(a3));
			System.out.println("Input4- " + Arrays.toString(a4));
			System.out.println("Input5- " + Arrays.toString(a5));
			System.out.println("Input6- " + Arrays.toString(a6));
			System.out.println("Input7- " + Arrays.toString(a7));
			System.out.println("Input8- " + Arrays.toString(a8));
			System.out.println("Selector- " + Arrays.toString(aSel));
			System.out.println("Output- " + Arrays.toString(Mux8Way16(a1,a2,a3,a4,a5,a6,a7,a8,aSel)));
			break;
		case DMUX4WAY:
			System.out.println("Type in the input bit, and press enter");
			a = s.nextInt();
			System.out.println("Type in the whole selector, with the bits not separated by commas, and press enter");			
			aSel = toArray(s.nextLine());;
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + "Sel- " + Arrays.toString(aSel));
			System.out.println("Output- " + Arrays.toString(DMux4Way(a,aSel)));
			break;
		case DMUX8WAY:
			System.out.println("Type in the input bit, and press enter");
			a = s.nextInt();
			System.out.println("Type in the whole selector, with the bits not separated by commas, and press enter");			
			aSel = toArray(s.nextLine());;
			System.out.println(gateString);
			System.out.println("Input- " + a + " " + "Sel- " + Arrays.toString(aSel));
			System.out.println("Output- " + Arrays.toString(DMux8Way(a,aSel)));
			break;
		}		
		s.close();
	}

	/**
	 * A method for convering an input string to an array of ints. 
	 * @param input The user's input string
	 * @return An array of ints made from that String
	 */
	public int[] toArray(String input)
	{
		int[] array = new int[input.length()];
		for (int i = 0; i < input.length(); i++) {
			array[i] = Character.getNumericValue(input.charAt(i));
		}
		return array;
	}
}
