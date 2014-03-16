package lab3;

import java.util.Arrays;
import java.util.Scanner;

import lab1.Gate;

public class Ram8
{
	private Register[] memory = new Register[8];
	private int[] output = new int[16];
	private Gate g = new Gate();
	private Scanner s = new Scanner(System.in);
	
	public Ram8()
	{
		for(int i = 0; i < 8; i++) {
			memory[i] = new Register();
		}
	}
	
	public static void main(String[] args) 
	{
		Ram8 ram8 = new Ram8();
		ram8.run();
	}
	
	public void run()
	{
		while(true) {
			System.out.println("Type in the load, and press enter");
			int load = s.nextInt();
			
			System.out.println("Type in the 3 bit memory address (for example, '010', without the quotes), and press enter");
			String unparsedAddress = s.next();
			int[] addressArray = makeArray(unparsedAddress);
			
			System.out.println("Type in the 16-bit input (for example, '00101010....', without the quotes), and press enter");
			int[] input = makeArray(s.next());
			
			//Processes info
			process(addressArray, input, load); 
			
			//Prints out the memory at the address specified	
			System.out.println("Output");
			System.out.println(Arrays.toString(output));
			System.out.println();
		}
	}
	
	public void process(int[] address, int[] input, int load) 
	{
		//Sets the output
		int[][] temp = new int[8][16];
		for(int i = 0; i < 8; i++) {
			temp[i] = memory[i].getRegister();
		}
		output = g.Mux8Way16(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], address);
		
		//Potentially changes data
		int[] loadStuff = g.DMux8Way(load, address);
		for(int i = 0; i < 8; i++) {
			memory[i].setRegister(input, loadStuff[i]);
		}
	}
	
	private int[] makeArray(String x) {
		int[] output = new int[x.length()];
		
		for(int i = 0; i < x.length(); i++) {
			output[i] = Integer.parseInt(x.substring(i, i + 1));
		}
		
		return output;
	}
	
	public int[] getOutput() 
	{
		return output;
	}
}
