package lab3;

import java.util.Arrays;
import java.util.Scanner;

import lab1.Gate;
import lab2.Calculator;

public class PC
{
	private int[] input = new int[16];
	private int inc = 0;
	private int load = 0;
	private int reset = 0;
	private int[] output = new int[16];
	private Gate g = new Gate();
	private Calculator calc = new Calculator();
	private Scanner s = new Scanner(System.in);

	public static void main(String[] args)
	{
		PC pc = new PC();
		pc.run();
	}

	public void run()
	{
		while(true) {
			System.out.println("Type in the 16 bit input, without spaces, and press enter");
			int[] currentInput = makeArray(s.next());
			System.out.println("Type in the inc, and press enter");
			int currentInc = s.nextInt();
			System.out.println("Type in the load, and press enter");
			int currentLoad = s.nextInt();
			System.out.println("Type in the reset, and press enter");
			int currentReset = s.nextInt();
			
			//Inc
			output = g.Mux16(output, calc.inc16(output), inc);

			//Load
			for(int i = 0; i < 16; i++) 
			{
				output[i] = g.Mux(output[i], input[i], load);
			}

			//Reset
			for(int i = 0; i < 16; i++)
			{
				output[i] = g.And(output[i], g.Not(reset));
			}

			input = currentInput;
			inc = currentInc;
			load = currentLoad;
			reset = currentReset;

			System.out.println();
			System.out.println("Ouput");
			System.out.println(Arrays.toString(output));
			System.out.println();
		}
	}
	
	private int[] makeArray(String x) {
		int[] output = new int[x.length()];

		for(int i = 0; i < x.length(); i++) {
			output[i] = Integer.parseInt(x.substring(i, i + 1));
		}
		return output;
	}
}
