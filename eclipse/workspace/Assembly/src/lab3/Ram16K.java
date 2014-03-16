package lab3;

import java.util.Arrays;
import java.util.Scanner;

import lab1.Gate;

public class Ram16K
{
	private Ram4K[] memory = new Ram4K[4];
	private int[] output = new int[16];
	private Gate g = new Gate();
	private Scanner s = new Scanner(System.in);
	
	public Ram16K()
	{
		for(int i = 0; i < 4; i++) {
			memory[i] = new Ram4K();
		}
	}
	
	public static void main(String[] args) 
	{
		Ram16K ram16K = new Ram16K();
		ram16K.run();
	}
	
	public void run()
	{
		while(true) {
			System.out.println("Type in the load, and press enter");
			int load = s.nextInt();
			
			System.out.println("Type in the 14 bit memory address (for example, '01010101010101', without the quotes), and press enter");
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
		int[] getBreakdown1 = {address[0], address[1]}; //Which Ram512 we want
		int[] getBreakdown2 = {address[2], address[3], address[4], address[5],address[6], address[7], address[8], address[9], address[10], address[11], address[12], address[13]}; //Which Register we want
		
		//Potentially changes data
		int[] loadStuff = g.DMux4Way(load, getBreakdown1);
		for(int i = 0; i < 4; i++) {
			memory[i].process(getBreakdown2, input, loadStuff[i]);
		}
		
		//Sets the output
		int[][] temp = new int[4][16];
		for(int i = 0; i < 4; i++) {
			temp[i] = memory[i].getOutput();
		}
		
		output = g.Mux4Way16(temp[0], temp[1], temp[2], temp[3], getBreakdown1);
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
